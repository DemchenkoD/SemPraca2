import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Hashing<T extends IData> {
    private int blockFactor;
    private int numbOfBlocks;
    private RandomAccessFile file;
    private T dataInitial;

    public Hashing(String paFileName, int paBlockFactor, int countData, T pDataInitial) {
        this.blockFactor = paBlockFactor;
        numbOfBlocks = (int) Math.ceil((double) countData / (double) paBlockFactor);
        dataInitial = pDataInitial;

        Block<T> b = new Block<>(blockFactor, dataInitial.getClass());
        byte[] blockBytes = new byte[b.getSize()];

        try {
            this.file = new RandomAccessFile(paFileName, "rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            for (int i = 0; i < numbOfBlocks; i++) {
                file.write(b.ToByteArray());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public Hashing(String paFileName, int paBlockFactor, T pDataInitial) {
        this.blockFactor = paBlockFactor;
        this.dataInitial = pDataInitial;
        try {
            this.file = new RandomAccessFile(paFileName, "rw");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int getBlockFactor() {
        return blockFactor;
    }

    public int getNumbOfBlocks() {
        return numbOfBlocks;
    }

    public T Find(T data) {
        Block<T> b;
        BitSet hash = data.getHash();

        b = new Block<>(blockFactor, data.getClass());
        long adresaBloku = getAdresa(hash);
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

        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);

        }
        return null;
    }

    protected long getAdresa(BitSet hash) {
        Block<T> b = new Block<>(blockFactor, dataInitial.getClass());
        long adresaBloku = hash.toLongArray()[0] % numbOfBlocks * b.getSize();
        return adresaBloku;
    }

    public boolean Insert(T data) {
        Block<T> b;
        BitSet hash = data.getHash();

        b = new Block<>(blockFactor, data.getClass());
        long adresaBloku = hash.toLongArray()[0] % numbOfBlocks * b.getSize();
        byte[] blockBytes = new byte[b.getSize()];
        try {

            file.seek(adresaBloku);
            file.read(blockBytes);
            b.FromByteArray(blockBytes);
            if (b.insertRecord(data)) {
                file.seek(adresaBloku);
                file.write(b.ToByteArray());
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }


    public boolean Delete(T data) {

        Block<T> b;
        BitSet hash = data.getHash();
        boolean result = false;
        b = new Block<>(blockFactor, data.getClass());
        long adresaBloku = getAdresa(hash);
        byte[] blockBytes = new byte[b.getSize()];
        try {

            file.seek(adresaBloku);
            file.read(blockBytes);
            b.FromByteArray(blockBytes);
            result = b.deleteRecord(data);
            if (result) {
                successfulDeletion(b, hash);
            }
        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    public void successfulDeletion(Block<T> blok, BitSet hash) {
        long adresaBloku = getAdresa(hash);
        try {
            file.seek(adresaBloku);
            file.write(blok.ToByteArray());
        } catch (IOException ex) {
            Logger.getLogger(Hashing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public long getAddress(BitSet key) {
        long value = 0L;
        for (int i = 0; i < key.length(); ++i) {
            value += key.get(i) ? (1L << i) : 0L;
        }
        value = value % 100;
        return value;
    }


}