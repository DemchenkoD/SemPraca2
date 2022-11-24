import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicHashing<T extends IData> extends Hashing {
    private String fileName;
    private String treeFileName;
    //private Strom strom;
    private String freeBlocksFileName;
    int blockSize;
    int blockFactor;
    private IVrchol Root;
    private RandomAccessFile file;
    private T dataInitial;
    public DynamicHashing(String paFileName, String paTreeFileName, String paFreeBlocksFileName, int paBlockFactor, T dataInitial) {
        super();
        this.fileName = paFileName;
        blockFactor = paBlockFactor;
        this.treeFileName = paTreeFileName; //TODO add read strom from file
        this.freeBlocksFileName = paFreeBlocksFileName;
        dataInitial = dataInitial;
        //strom = new Strom();
        Root = new ExternyVrchol(null, -1);
        //Block<T> b = new Block<>(blockFactor, dataInitial.getClass());
        //blockSize = b.getSize();
        try {
            this.file = new RandomAccessFile(paFileName, "rw");
           // file.write(b.ToByteArray());
        } catch (IOException e) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, e);
        }

        //InternyVrchol startVrchol = new InternyVrchol();
        //startVrchol.setAdresaBloku(0);

        //strom.insert(startVrchol);
    }

    public boolean Insert2(T data) {
        BitSet hash = data.getHash();
        Block<T> b = new Block<>(blockFactor, data.getClass());
        ExternyVrchol extVrchol = getExternyVrchol(hash);

        byte[] blockBytes = new byte[b.getSize()];
        try {
            long adresaBloku = extVrchol.getAdresaBloku();
            if (adresaBloku != -1) { // block has pointer
                file.seek(adresaBloku) ;
                file.read(blockBytes);
                b.FromByteArray(blockBytes);
            } else {
                adresaBloku = getFreeAdresa();
                extVrchol.setAdresaBloku(adresaBloku);
            }
            if (!b.isFull()) {
                if (b.insertRecord(data)) {
                    file.seek(adresaBloku);
                    file.write(b.ToByteArray());
                    return true;
                } else
                    return false;
            } else { //the block is full
                ArrayList<T> allData = b.getRecords();
                allData.add(data);
                return transformAndInsert(extVrchol, allData);
            }
        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public T Find2(T data) {
        BitSet hash = data.getHash();
        Block<T> b = new Block<>(blockFactor, data.getClass());
        long adresaBloku = getExternyVrchol(hash).getAdresaBloku();
        byte[] blockBytes = new byte[b.getSize()];
        try {

            file.seek(adresaBloku) ;
            file.read(blockBytes);
            b.FromByteArray(blockBytes);
            for (T zaznam : b.getRecords()) {
                if (data.myEquals(zaznam) == true) {
                    return zaznam;
                }
            }
            return null;
        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    private ExternyVrchol getExternyVrchol(BitSet key) {
        //if (!Root.isInternal()){
        //    return Root;
        //}
        IVrchol vrchol = Root;
        while (vrchol instanceof InternyVrchol) {
            vrchol = ((InternyVrchol) vrchol).getSon(key);
        }
        return (ExternyVrchol) vrchol;

    }
    private boolean transformAndInsert(ExternyVrchol extVrchol, ArrayList<T> allData){
        InternyVrchol r;
        if (extVrchol.getParent() == null) { //it's root
            r = new InternyVrchol(null, 0 );
            Root = r;
        } else {
            r = new InternyVrchol(extVrchol.getParent(), extVrchol.getParent().getIndexSplitter()+1);
            extVrchol.getParent().setSon(extVrchol, r);
        }
        r.setLavy(new ExternyVrchol(r, -1));
        r.setPravy(new ExternyVrchol(r, -1));
        for(T data : allData)
            Insert2(data);
        return true;
    }

    private long getFreeAdresa() {
        try {
            return file.length();
        } catch (IOException e){
            System.out.println("Error getFreeAdresa()");
            return -1;
        }
    }
    private boolean createBlock(long paAdresa) {
        Block<T> b = new Block<>(blockFactor, dataInitial.getClass());
        try {
            file.seek(paAdresa);
            file.write(b.ToByteArray());
            return true;
        } catch (IOException e) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }


}
