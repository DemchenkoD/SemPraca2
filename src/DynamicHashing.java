import java.io.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Scanner;
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

    private ArrayList<Long> freeAdresses;
    private T dataInitial;

    public DynamicHashing(String paFileName, String paTreeFileName, String paFreeBlocksFileName, int paBlockFactor, T paDataInitial) {
        super();
        this.fileName = paFileName;
        blockFactor = paBlockFactor;
        this.treeFileName = paTreeFileName; //TODO add read strom from file
        this.freeBlocksFileName = paFreeBlocksFileName;
        freeAdresses = new ArrayList<>();
        dataInitial = paDataInitial;
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

    public void vypis() {
        Block<T> b = new Block<>(blockFactor, dataInitial.getClass());
        byte[] blockBytes = new byte[b.getSize()];
        long counter = 0;
        System.out.println();
        while (true) {
            try {
                long address = counter * b.getSize();

                if (address >= file.length())
                    break;
                System.out.println("_______________________SEEK_____" + address + "___________");
                file.seek(address);
                file.read(blockBytes);
                b.FromByteArray(blockBytes);
                b.vypis();
                counter++;

            } catch (EOFException e) {
                System.out.println("EOF");
                break;
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
        System.out.println();
    }

    public boolean Insert2(T data) {
        BitSet hash = data.getHash();
        Block<T> b = new Block<>(blockFactor, data.getClass());
        ExternyVrchol extVrchol = getExternyVrchol(hash);

        byte[] blockBytes = new byte[b.getSize()];
        try {
            long adresaBloku = extVrchol.getAdresaBloku();
            if (adresaBloku != -1) { // block has pointer
                file.seek(adresaBloku);
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
                ArrayList<T> allData = b.getValidRecords();
                freeAdresses.add(extVrchol.getAdresaBloku());
                extVrchol.setAdresaBloku(-1);
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

            file.seek(adresaBloku);
            file.read(blockBytes);
            b.FromByteArray(blockBytes);
            for (T zaznam : b.getValidRecords()) {
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

    public boolean Delete2(T data) {
        BitSet hash = data.getHash();
        Block<T> b = new Block<>(blockFactor, data.getClass());
        ExternyVrchol extVrchol = getExternyVrchol(hash);
        long adresaBloku = extVrchol.getAdresaBloku();
        byte[] blockBytes = new byte[b.getSize()];
        try {

            file.seek(adresaBloku);
            file.read(blockBytes);
            b.FromByteArray(blockBytes);
            boolean result = b.deleteRecord(data);
            if (result) {
                file.seek(adresaBloku);
                file.write(b.ToByteArray());
                while (canBeUnited(extVrchol)) {
                    ArrayList<T> allData = b.getValidRecords();
                    ExternyVrchol brother = (ExternyVrchol) extVrchol.getParent().getBrother(extVrchol);
                    if (brother.getAdresaBloku() != -1) {
                        Block<T> block2 = getBlock(brother);

                        allData.addAll(block2.getValidRecords());
                    }

                    extVrchol = uniteSons(extVrchol.getParent());
                    b = getBlock(extVrchol);
                    adresaBloku = extVrchol.getAdresaBloku();


                    for (T t_data : allData)
                        b.insertRecord(t_data);

                    file.seek(adresaBloku);
                    file.write(b.ToByteArray());
                }
                if (b.getValidCount() == 0) { //empty block, has to be deleted
                    freeAdresses.add(extVrchol.getAdresaBloku());
                    extVrchol.setAdresaBloku(-1);
                    return result;
                }
                cleanFreeBlocks();
                System.out.println("DELETED" + data.toString());
                vypis();
            }
            return result;
        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean Delete3(T data) {
        BitSet hash = data.getHash();
        Block<T> b = new Block<>(blockFactor, data.getClass());
        ExternyVrchol extVrchol = getExternyVrchol(hash);
        long adresaBloku = extVrchol.getAdresaBloku();
        byte[] blockBytes = new byte[b.getSize()];
        try {

            file.seek(adresaBloku);
            file.read(blockBytes);
            b.FromByteArray(blockBytes);
            boolean result = b.deleteRecord(data);
            if (result) {
                file.seek(adresaBloku);
                file.write(b.ToByteArray());
                if (canBeUnited(extVrchol)) { //check if extVrchol can be united with another vrchol
                    ArrayList<T> allData = b.getValidRecords();
                    ExternyVrchol brother = (ExternyVrchol) extVrchol.getParent().getBrother(extVrchol);
                    if (brother.getAdresaBloku() != -1) {
                        Block<T> block2 = getBlock(brother);

                        allData.addAll(block2.getValidRecords());
                    }
                    /*
                    ExternyVrchol newVrchol = uniteSons(extVrchol.getParent());
                    b = getBlock(newVrchol);
                    adresaBloku = newVrchol.getAdresaBloku();
                     */
                    extVrchol = uniteSons(extVrchol.getParent());
                    b = getBlock(extVrchol);
                    adresaBloku = extVrchol.getAdresaBloku();


                    for (T t_data : allData)
                        b.insertRecord(t_data);

                    file.seek(adresaBloku);
                    file.write(b.ToByteArray());
                } else if (b.getValidCount() == 0) { //empty block, has to be deleted
                    freeAdresses.add(extVrchol.getAdresaBloku());
                    extVrchol.setAdresaBloku(-1);
                    return result;
                }
                //uniteHigher(extVrchol);
                cleanFreeBlocks();
                System.out.println("DELETED" + data.toString());
                vypis();
            }
            return result;
        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void clearBlock(long paAdresaBloku) {

        Block<T> block = new Block<>(blockFactor, dataInitial.getClass());
        //long adresa = paAdresaBloku;
        try {
            //if (paAdresaBloku < file.length()) {
            file.seek(paAdresaBloku);
            file.write(block.ToByteArray());
            freeAdresses.add(paAdresaBloku);
            //TODO pridat - ak adresa + blocksize = size suboru -> vymazat block a pozret predchadzajuci
                /*
                while (adresa + block.getSize() == file.length()) { //it's last block
                    if (block.getValidCount() == 0) {
                        freeAdresses.remove(adresa);
                        file.setLength(adresa);
                        if (adresa != 0) {
                            adresa = adresa - block.getSize();
                            block = getBlock(adresa);
                        } else
                            break;
                    } else
                        break;
                }
                */

            // }
        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cleanFreeBlocks() {
        Block<T> block = new Block<>(blockFactor, dataInitial.getClass());
        try {
            long adresa = file.length() - block.getSize();
            block = getBlock(adresa);
            while (adresa + block.getSize() == file.length()) { //it's last block
                if (block.getValidCount() == 0) {
                    freeAdresses.remove(adresa);
                    file.setLength(adresa);
                    if (adresa != 0) {
                        adresa = adresa - block.getSize();
                        block = getBlock(adresa);
                    } else
                        break;
                } else
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public Block<T> getBlock(long paAdresa) {
        Block<T> b = new Block<>(blockFactor, dataInitial.getClass());
        byte[] blockBytes = new byte[b.getSize()];
        try {

            file.seek(paAdresa);
            file.read(blockBytes);
            b.FromByteArray(blockBytes);
            return b;
        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private ExternyVrchol uniteSons(InternyVrchol paIntVrchol) {
        if (((ExternyVrchol) paIntVrchol.getLavy()).getAdresaBloku() != -1)
            clearBlock(((ExternyVrchol) paIntVrchol.getLavy()).getAdresaBloku());
        if (((ExternyVrchol) paIntVrchol.getPravy()).getAdresaBloku() != -1)
            clearBlock(((ExternyVrchol) paIntVrchol.getPravy()).getAdresaBloku());
        ExternyVrchol replacement;
        if (paIntVrchol == Root) {
            replacement = new ExternyVrchol(null, getFreeAdresa());
            Root = replacement;
            return replacement;
        } else {
            replacement = new ExternyVrchol(paIntVrchol.getParent(), getFreeAdresa());
            paIntVrchol.getParent().setSon(paIntVrchol, replacement);
            return replacement;
        }
    }

    public boolean canBeUnited(ExternyVrchol paExtVrchol) {
        if (paExtVrchol != Root) {
            if (!paExtVrchol.getParent().getBrother(paExtVrchol).isInternal()) { //brother is also external vrchol
                ExternyVrchol brother = (ExternyVrchol) paExtVrchol.getParent().getBrother(paExtVrchol);
                if (brother.getAdresaBloku() == -1) { // it'll fit cause it;s empty
                    return true;
                }
                //read blocks
                Block<T> block1 = getBlock(paExtVrchol);

                Block<T> block2 = getBlock(brother);
                if (block1.getValidCount() + block2.getValidCount() <= blockFactor) {
                    return true;
                }


            }
        }
        return false;
    }

    private Block<T> getBlock(ExternyVrchol extVrchol) {
        Block<T> b = new Block<>(blockFactor, dataInitial.getClass());
        long adresaBloku = extVrchol.getAdresaBloku();
        byte[] blockBytes = new byte[b.getSize()];
        try {

            file.seek(adresaBloku);
            file.read(blockBytes);
            b.FromByteArray(blockBytes);
            return b;
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

    private boolean transformAndInsert(ExternyVrchol extVrchol, ArrayList<T> allData) {
        InternyVrchol r;
        if (extVrchol.getParent() == null) { //it's root
            r = new InternyVrchol(null, 0);
            Root = r;
        } else {
            r = new InternyVrchol(extVrchol.getParent(), extVrchol.getParent().getIndexSplitter() + 1);
            extVrchol.getParent().setSon(extVrchol, r);
        }
        r.setLavy(new ExternyVrchol(r, -1));
        r.setPravy(new ExternyVrchol(r, -1));
        for (T data : allData)
            Insert2(data);
        return true;
    }

    public ArrayList<ExternyVrchol> getExterneVrcholi() {
        ArrayList<ExternyVrchol> result = new ArrayList<>();
        if (Root == null || Root instanceof ExternyVrchol)
            return result;
        InternyVrchol node = (InternyVrchol) Root;
        boolean value_visited = !node.visited;
        while (true) {

            if (node.visited != value_visited) {

                while (node.getLavy() instanceof InternyVrchol) {
                    if (((InternyVrchol) node.getLavy()).visited == value_visited)
                        break;
                    node = (InternyVrchol) node.getLavy();
                }
                if (node.getLavy() instanceof ExternyVrchol)
                    result.add((ExternyVrchol) node.getLavy());
                if (node.getPravy() instanceof ExternyVrchol)
                    result.add((ExternyVrchol) node.getPravy());
                node.visited = value_visited;

            }
            boolean goHigher = false;
            if (node.getPravy() instanceof InternyVrchol) {
                if (((InternyVrchol) node.getPravy()).visited != value_visited)
                    node = (InternyVrchol) node.getPravy();
                else
                    goHigher = true;
            } else {
                goHigher = true;
            }
            if (goHigher) {
                try {
                    while (node.getParent().visited == value_visited)
                        node = node.getParent();
                    node = node.getParent();
                } catch (NullPointerException e) {
                    return result;
                }
            }
        }

    }

    public void writeTreeToFile(String nazovSuboru) {// TODO check if tree has only one extVrchol
        try {
            FileWriter writer = new FileWriter(nazovSuboru);
            ArrayList<ExternyVrchol> extVrcholy = getExterneVrcholi();
            for(ExternyVrchol extVrchol: extVrcholy)
                writer.write(extVrchol.toString());

            writer.close();
        } catch (IOException e) {

        }
    }
    public void readTreeFromFile(String nazovSuboru) {
        Root = null;
        try {
            File file = new File(nazovSuboru);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String parts[] = data.split(";");
                IVrchol tmp = Root;

                for(int i = 0; i < parts[0].length(); i++){ //create IntVrchol for every bit in adress
                    if (i == 0 && !(Root instanceof InternyVrchol)) { //Root has to be created
                        Root = new InternyVrchol(null, 0);
                        tmp = Root;
                        ((InternyVrchol)tmp).setLavy(new ExternyVrchol((InternyVrchol)tmp, -1));
                        ((InternyVrchol)tmp).setPravy(new ExternyVrchol((InternyVrchol)tmp, -1));
                    }
                    if (tmp instanceof ExternyVrchol) {
                        transformAndInsert((ExternyVrchol) tmp, new ArrayList<>()); //TODO change
                        tmp = tmp.getParent();
                        i = i-2;
                        continue;
                    }
                    //tmp = new InternyVrchol(tmp.getParent(), tmp.getParent().getIndexSplitter() + 1);
                    if (parts[0].charAt(i) == '0')
                        tmp = ((InternyVrchol) tmp).getLavy();
                    else if (parts[0].charAt(i) == '1')
                        tmp = ((InternyVrchol) tmp).getPravy();

                }
                ExternyVrchol extVrchol = new ExternyVrchol(tmp.getParent(), Long.parseLong(parts[1]));
                tmp.getParent().setSon(tmp, extVrchol);

            }
            reader.close();
        } catch (IOException e) {

        }
    }
    private ExternyVrchol createExtVrcholOnPosition(String adresa) {
        if (adresa.contains("")) {
            Root = new ExternyVrchol(null, -1);
            return (ExternyVrchol) Root;
        }
        IVrchol tmp = Root;
        for (int i = 0; i < adresa.length(); i++) {

            if(!(tmp instanceof InternyVrchol)) {
                tmp = new InternyVrchol(tmp.getParent(), tmp.getParent().getIndexSplitter()+1);
            }

            if (adresa.charAt(i) == '0') {

            } else if (adresa.charAt(i) == '1') {

            }
        }
        return null;
    }

    private long getFreeAdresa() {
        if (freeAdresses.size() != 0) {
            long address = freeAdresses.get(0);
            freeAdresses.remove(0);
            return address;
        }
        try {
            return file.length();
        } catch (IOException e) {
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
