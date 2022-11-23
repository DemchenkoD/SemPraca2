import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicHashing<T extends IData> extends Hashing {
    private String fileName;
    private String treeFileName;
    private Strom strom;
    private String freeBlocksFileName;
    int blockSize;
    int blockFactor;
    private RandomAccessFile file;
    public DynamicHashing(String paFileName, String paTreeFileName, String paFreeBlocksFileName, int paBlockFactor, T dataInitial) {
        super();
        this.fileName = paFileName;
        blockFactor = paBlockFactor;
        this.treeFileName = paTreeFileName; //TODO add read strom from file
        this.freeBlocksFileName = paFreeBlocksFileName;
        strom = new Strom();
        Block<T> b = new Block<>(blockFactor, dataInitial.getClass());
        blockSize = b.getSize();
        try {
            this.file = new RandomAccessFile(paFileName, "rw");
            file.write(b.ToByteArray());
        } catch (IOException e) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, e);
        }

        //InternyVrchol startVrchol = new InternyVrchol();
        //startVrchol.setAdresaBloku(0);

        //strom.insert(startVrchol);
    }


}
