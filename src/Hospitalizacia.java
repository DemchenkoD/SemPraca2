import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.BitSet;

public class Hospitalizacia implements Comparable<Hospitalizacia>, IData<Hospitalizacia>{
    private final String format = "yyyy-MM-dd HH:mm:ss";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    private final int maxLengthDiagnoza = 20;
    private int diagnozaLength = 0;
    private int id;
    private LocalDateTime d_zaciatku;
    private LocalDateTime d_konca;
    private String diagnoza;

    private boolean bolHospitalizovany = false;

    public Hospitalizacia () {
        id = -99;
        d_zaciatku = null;
        d_konca = null;
        diagnoza = "";
        diagnozaLength = diagnoza.length();

    }
    public Hospitalizacia (int pId, LocalDateTime pD_zaciatku, LocalDateTime pD_konca, String pDiagnoza ) {
        id = pId;
        d_zaciatku = pD_zaciatku;
        d_konca = pD_konca;
        diagnoza = pDiagnoza;
        if (diagnoza != null)
            diagnozaLength = diagnoza.length();

        //FromByteArray(ToByteArray());
    }
    public int getId() {
        return id;
    }

    public void setParBolHospitlizovany(boolean hodnota) {
        bolHospitalizovany = hodnota;
    }

    public LocalDateTime getD_zaciatku() {
        return d_zaciatku;
    }

    public LocalDateTime getD_konca() {
        return d_konca;
    }

    public String getDiagnoza() {
        return diagnoza;
    }


    public void setD_zaciatku(LocalDateTime d_zaciatku) {
        this.d_zaciatku = d_zaciatku;
    }

    public void setD_konca(LocalDateTime d_konca) {
        this.d_konca = d_konca;
    }

    public void setDiagnoza(String diagnoza) {
        this.diagnoza = diagnoza;
    }
    /*
    public String toFile() {
        if (d_konca != null)
            return ("H;" + nazovNemocnice + ';' + rodCisloPacienta + ';' + d_zaciatku.format(formatter) + ';' + d_konca.format(formatter) + ';' + diagnoza + ';' + poistovna + '\n');
        else
            return ("H;" + nazovNemocnice + ';' + rodCisloPacienta + ';' + d_zaciatku.format(formatter) + ';' + "null" + ';' + diagnoza +  ';' + poistovna +'\n');
    }

*/
    @Override
    public int compareTo(Hospitalizacia o) {
        /*
        if (o.bolHospitalizovany && o.rodCisloPacienta.equals("") && o.nazovNemocnice.equals("") && o.d_zaciatku != null) {
            if ( this.d_zaciatku.isAfter(o.d_zaciatku) || this.d_zaciatku.isBefore(o.d_zaciatku)) {
                return 0;
            } else if (this.d_konca == null)
                return 0;
            else if (this.d_konca.isAfter(o.d_zaciatku) || this.d_konca.isBefore(o.d_zaciatku)) {
                return 0;
            } else
                return 99;
        } else if (o.rodCisloPacienta.equals("") && o.nazovNemocnice.equals("") && o.d_zaciatku != null && o.d_konca != null) {
            if (this.d_konca != null && this.d_zaciatku.isAfter(o.d_zaciatku) && this.d_konca.isBefore(o.d_konca))
                return 0;
            else
                return 99;
        } else

         */
            return this.d_zaciatku.compareTo(o.d_zaciatku);
    }

    @Override
    public BitSet getHash() {
        return null; //TODO change
    }

    @Override
    public boolean myEquals(Hospitalizacia data) {
        return false; //TODO change
    }

    @Override
    public Hospitalizacia createClass() {
        return null; //TODO change
    }

    @Override
    public byte[] ToByteArray() {
        ByteArrayOutputStream hlpByteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream hlpOutStream = new DataOutputStream(hlpByteArrayOutputStream);

        try {

            hlpOutStream.writeInt(id);
            if (d_zaciatku != null)
                hlpOutStream.writeChars(d_zaciatku.format(formatter));
            else
                hlpOutStream.writeChars("---------- --:--:--");

            if (d_konca != null)
                hlpOutStream.writeChars(d_konca.format(formatter));
            else
                hlpOutStream.writeChars("---------- --:--:--");
            hlpOutStream.writeInt(diagnozaLength);
            hlpOutStream.writeChars(diagnoza);
            for (int i = 0; i < maxLengthDiagnoza-diagnoza.length(); i++)
                hlpOutStream.writeChar(0);

            return hlpByteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Error during conversion to byte array.");
        }
    }

    @Override
    public void FromByteArray(byte[] paArray) {
        ByteArrayInputStream hlpByteArrayInputStream = new ByteArrayInputStream(paArray) ;
        DataInputStream hlpInStream = new DataInputStream(hlpByteArrayInputStream) ;

        //TODO read only needed characters ( remember length that you have to read)
        try {
            id = hlpInStream.readInt();
            String tmp = "";
            for (int i= 0; i < format.length(); i++) {
                tmp += hlpInStream.readChar();
            }
            if (tmp.equals("---------- --:--:--"))
                d_zaciatku = null;
            else
                d_zaciatku = LocalDateTime.parse(tmp, formatter);
            tmp = "";
            for (int i= 0; i < format.length(); i++) {  // TODO add not finished hospitalization support
                tmp += hlpInStream.readChar();
            }
            if (tmp.equals("---------- --:--:--"))
                d_konca = null;
            else
                d_konca = LocalDateTime.parse(tmp, formatter);
            diagnozaLength = hlpInStream.readInt();
            diagnoza = "";
            for (int j = 0; j < maxLengthDiagnoza; j++) {
                char tmpChar = hlpInStream.readChar();
                if (j < diagnozaLength)
                    diagnoza += tmpChar;
            }

        } catch (IOException e){
            throw new IllegalStateException("Error during conversion from byte array.");
        }
    }

    @Override
    public int getSize() {
        return Character.BYTES * (maxLengthDiagnoza + 2 * format.length()) + Integer.BYTES + Integer.BYTES;

    }
}
