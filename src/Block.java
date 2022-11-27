import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Block<T extends IData> implements IRecord {
    private final int blockFactor;
    private int ValidCount;
    private final ArrayList<T> records;
    private final Class<T> classType;
    public Block (int paBlockFactor, Class paClassType) {
        this.blockFactor = paBlockFactor;
        this.classType = paClassType;
        this.records = new ArrayList<T>(paBlockFactor);
        for (int i = 0; i < blockFactor; i++) {
            try {
                this.records.add((T) classType.newInstance().createClass());
            } catch (InstantiationException ex) {
                Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        ValidCount = 0;
    }

    public int getValidCount() {
        return ValidCount;
    }

    public boolean insertRecord(T paNew) {
        int indexExists = -99;
        for (int i = 0; i < records.size(); i++){
            if (records.get(i).myEquals(paNew)) {
                indexExists = i;
                break;
            }

        }
        if (indexExists != -99) {
            records.remove(indexExists);
            records.add(indexExists, paNew);
            return true;
        }
        if (ValidCount < blockFactor) {
            records.remove(ValidCount);
            records.add(ValidCount, paNew);
            ValidCount++;
            return true;
        }
        return false;
    }
    public void vypis() {
        System.out.println("________________________NEW BLOCK_______________SIZE =" + ValidCount);
        if(ValidCount == 0) {
            System.out.println("Empty block on address ");
        } else {

            for(int i = 0; i < ValidCount; i++){
                records.get(i).print();
            }
        }
    }


    public boolean isFull(){
        return ValidCount == blockFactor;
    }

    public boolean deleteRecord(T paNew) {
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).myEquals(paNew)) {
                records.remove(i);
                ValidCount--;
                try {
                    this.records.add((T) classType.newInstance().createClass());
                } catch (InstantiationException ex) {
                    Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
                }

                return true;
            }
        }
        return false;
    }



    @Override
    public byte[] ToByteArray() {
        ByteArrayOutputStream hlpByteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream hlpOutStream = new DataOutputStream(hlpByteArrayOutputStream) ;

        try {

            hlpOutStream.writeInt(ValidCount);
            for (int i = 0; i < blockFactor; i++) {
                hlpOutStream.write(records.get(i).ToByteArray());
            }

            return hlpByteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Error during conversion to byte array.");
        }

        // — hlpOutStream.write(dummy.ToByteArray ())

    }

    @Override
    public void FromByteArray(byte[] paArray) {
//+validcount
        ByteArrayInputStream hlpByteArrayInputStream = new ByteArrayInputStream(paArray) ;

        DataInputStream hlpStream = new DataInputStream(hlpByteArrayInputStream );
        try {
            ValidCount = hlpStream.readInt();
        } catch (IOException e){
            throw new IllegalStateException("Error during conversion from byte array.");
        }
        for (int i = 0; i < blockFactor; i++) {
            byte[] n = Arrays.copyOfRange(paArray, i * records.get(i).getSize() + Integer.BYTES, (i + 1) * records.get(i).getSize() + Integer.BYTES);
            records.get(i).FromByteArray(n);
        }
    }

    @Override
    public int getSize() {
        try {
            return classType.newInstance().getSize() * blockFactor + Integer.BYTES;
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public ArrayList<T> getValidRecords() {
        ArrayList<T> result = new ArrayList<>();
        for(int i = 0; i < ValidCount; i++)
            result.add(records.get(i));
        return result;
    }



}


