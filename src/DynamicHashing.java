import java.io.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicHashing<T extends IData> extends Hashing<T> {
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
        super(paFileName, paBlockFactor, paDataInitial);
        this.fileName = paFileName;
        blockFactor = paBlockFactor;
        this.treeFileName = paTreeFileName; //TODO add read strom from file
        this.freeBlocksFileName = paFreeBlocksFileName;
        freeAdresses = new ArrayList<>();
        dataInitial = paDataInitial;
        Root = new ExternyVrchol(null, -1);

        try {
            this.file = new RandomAccessFile(paFileName, "rw");
            // file.write(b.ToByteArray());
        } catch (IOException e) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, e);
        }

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

    public boolean Insert(T data) {
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

    @Override
    protected long getAdresa(BitSet hash) {
        long adresaBloku = getExternyVrchol(hash).getAdresaBloku();
        return adresaBloku;
    }
    @Override
    public void successfulDeletion(Block blok, BitSet hash) {
        long adresaBloku = getAdresa(hash);
        ExternyVrchol extVrchol = getExternyVrchol(hash);
        try {
            file.seek(adresaBloku);
            file.write(blok.ToByteArray());
            while (canBeUnited(extVrchol)) {
                ArrayList<T> allData =blok.getValidRecords();
                ExternyVrchol brother = (ExternyVrchol) extVrchol.getParent().getBrother(extVrchol);
                if (brother.getAdresaBloku() != -1) {
                    Block<T> block2 = getBlock(brother);

                    allData.addAll(block2.getValidRecords());
                }

                extVrchol = uniteSons(extVrchol.getParent());
                blok = getBlock(extVrchol);
                adresaBloku = extVrchol.getAdresaBloku();


                for (T t_data : allData) //TODO change
                    blok.insertRecord(t_data);

                file.seek(adresaBloku);
                file.write(blok.ToByteArray());
            }
            if (blok.getValidCount() == 0) { //empty block, has to be deleted
                freeAdresses.add(extVrchol.getAdresaBloku());
                extVrchol.setAdresaBloku(-1);
                return;
            }
            cleanFreeBlocks();
            //vypis();
        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearBlock(long paAdresaBloku) {

        Block<T> block = new Block<>(blockFactor, dataInitial.getClass());
        try {
            file.seek(paAdresaBloku);
            file.write(block.ToByteArray());
            freeAdresses.add(paAdresaBloku);
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
        for (T data : allData) //TODO change
            Insert(data);
        return true;
    }

    public ArrayList<ExternyVrchol> getExterneVrcholi() {
        ArrayList<ExternyVrchol> result = new ArrayList<>();
        if (Root == null)
            return result;
        if (Root instanceof ExternyVrchol) {
            result.add((ExternyVrchol) Root);
            return result;
        }
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

    public void writeTreeToFile(String nazovSuboru) {
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
                if(parts[0].equals("null")) {// that's Root and it's ExtVrchol
                    Root = new ExternyVrchol(null, Long.parseLong(parts[1]));
                    return;
                }
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
