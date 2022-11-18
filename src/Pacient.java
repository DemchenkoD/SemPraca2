import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.Random;

public class Pacient implements Comparable<Pacient>, IData<Pacient> {
    private final int maxLengthMeno = 15;
    private int menoLength = 0;
    private int priezviskoLength = 0;
    private int hospitalizacieLength = 0;
    private final int maxLengthPriezvisko = 20;
    private final int maxLengthDatumNarodenia = 10;
    private final int maxLengtRodCislo = 10;
    private final int maxLengtHospitalizacie = 10;
    private String meno;
    private String priezvisko;
    private String rod_cislo;
    private LocalDate d_narodenia;

    private int poistovna;
    private String zdr_poistovna;
    private ArrayList<Hospitalizacia> hospitalizacie;
    private ArrayList<Integer> idHospitalizacie;
    public Pacient() {

    }
    public Pacient(String pMeno, String pPriezvisko, String pRod_cislo, LocalDate pD_narodenia, String pZdr_poistovna) {
        meno = pMeno;
        priezvisko = pPriezvisko;
        rod_cislo = pRod_cislo;
        d_narodenia = pD_narodenia;
        zdr_poistovna = pZdr_poistovna;
        hospitalizacie = new ArrayList<Hospitalizacia>(maxLengtHospitalizacie);
        for (int i = 0; i < maxLengtHospitalizacie; i++)
            hospitalizacie.add(new Hospitalizacia());
        idHospitalizacie = new ArrayList<>(maxLengtHospitalizacie);

        menoLength = meno.length();
        priezviskoLength = priezvisko.length();

        poistovna = 234;
        //this.FromByteArray(this.ToByteArray());
    }
    public String getMeno() {
        return meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public String getRod_cislo() {
        return rod_cislo;
    }

    public LocalDate getD_narodenia() {
        return d_narodenia;
    }

    public String getZdr_poistovna() {
        return zdr_poistovna;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    public void setRod_cislo(String rod_cislo) {
        this.rod_cislo = rod_cislo;
    }

    public void setD_narodenia(LocalDate d_narodenia) {
        this.d_narodenia = d_narodenia;
    }

    public void setZdr_poistovna(String zdr_poistovna) {
        this.zdr_poistovna = zdr_poistovna;
    }
    public boolean addHospitalizacia(LocalDateTime pD_zaciatku, LocalDateTime pD_konca, String pDiagnoza ) {
        int id;
        Random r = new Random();
        do {
            id = r.nextInt(1000);
        } while (idHospitalizacie.contains(id));

        hospitalizacie.add(new Hospitalizacia(id, pD_zaciatku, pD_konca, pDiagnoza));
        idHospitalizacie.add(id);
        hospitalizacieLength++;
        return true;
    }
    public boolean addHospitalizaciaObj(Hospitalizacia h) {
        hospitalizacie.add(h);
        return true;
    }
/*
    public Hospitalizacia getHospitalizacia(String pNazovNemocnice) {
        for (Hospitalizacia tmp : hospitalizacie){
            if (tmp.getNazovNemocnice().equals(pNazovNemocnice) && tmp.getRodCisloPacienta().equals(this.rod_cislo))
                return tmp;
        }
        return null;
    }
    public LocalDateTime ukoncitHospitalizaciu(String pNazovNemocnice, LocalDateTime pKonca) { // return zaciatok hospitalizacie

        for (Hospitalizacia tmp : hospitalizacie){
            if (tmp.getNazovNemocnice().equals(pNazovNemocnice) && tmp.getRodCisloPacienta().equals(this.rod_cislo) && tmp.getD_konca() == null) {
                tmp.setD_konca(pKonca);
                return tmp.getD_zaciatku();
            }
        }
        return null;
    }

 */
    public String toString() {
        return (rod_cislo + '\t' + meno + '\t' + priezvisko + '\t' + d_narodenia.toString() + '\t' + zdr_poistovna);
    }
    /*
    public String toFile() {
        String result = "P;" +  meno + ';' + priezvisko + ';' + rod_cislo + ';'+ d_narodenia.toString() + ';' + zdr_poistovna + '\n';
        for (Hospitalizacia h: hospitalizacie ) {
            result += h.toFile();
        }
        return result;
    }

     */
    public String toStringHospitalizacie() {
        String result = "";
        for (Hospitalizacia h: hospitalizacie) {
            result += h.toString() + '\n';
        }
        return result;
    }

    @Override
    public int compareTo(Pacient o) {
        if (!o.rod_cislo.equals("")) {
            if (Long.parseLong(this.rod_cislo) < Long.parseLong(o.rod_cislo)) {
                return -1;
            } else if (Long.parseLong(this.rod_cislo) > Long.parseLong(o.rod_cislo))
                return 1;
            else
                return 0;
        } else {
            if (this.meno.equals(o.meno) && this.priezvisko.equals(o.priezvisko))
                return 0;
            else
                return 99;
        }
    }


    @Override
    public int getSize() {
        return Character.BYTES * (maxLengthMeno + maxLengthPriezvisko + maxLengtRodCislo + 10) +
                4*Integer.BYTES + maxLengtHospitalizacie*((new Hospitalizacia(0, null, null, null)).getSize()); //TODO addHospitalizacie size
                //also added number of hospitalizations
    }
    @Override
    public BitSet getHash() {

        BitSet hash;
        hash = BitSet.valueOf (new long[] {Long.parseLong(this.rod_cislo)});
        return hash;
    }

    @Override
    public boolean myEquals(Pacient data) {
        return Long.parseLong(this.rod_cislo) == Long.parseLong(data.rod_cislo);
    }

    @Override
    public Pacient createClass() {
        return new Pacient("","","",null,"");
    }

    @Override
    public byte[] ToByteArray() {

        ByteArrayOutputStream hlpByteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream hlpOutStream = new DataOutputStream(hlpByteArrayOutputStream);

        try {
            if (!rod_cislo.equals(""))
                hlpOutStream.writeChars(rod_cislo);
            else
                hlpOutStream.writeChars("----------");

            hlpOutStream.writeInt(menoLength);
            hlpOutStream.writeChars(meno);

            for (int i = 0; i < maxLengthMeno-meno.length(); i++)
                hlpOutStream.writeChar(0);

            hlpOutStream.writeInt(priezviskoLength);
            hlpOutStream.writeChars(priezvisko);
            for (int i = 0; i < maxLengthPriezvisko-priezvisko.length(); i++)
                hlpOutStream.writeChar(0);

            if (d_narodenia != null)
                hlpOutStream.writeChars(d_narodenia.toString());
            else
                hlpOutStream.writeChars("----------");
            hlpOutStream.writeInt(poistovna);
            // added Hospitalizacie
            hlpOutStream.writeInt(hospitalizacie.size());

            for (int i = 0 ; i < hospitalizacie.size(); i++) {
                hlpByteArrayOutputStream.writeBytes(hospitalizacie.get(i).ToByteArray());
            }
            return hlpByteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Error during conversion to byte array.");
        }
    }


    @Override
    public void FromByteArray(byte[] paArray) {
        ByteArrayInputStream hlpByteArrayInputStream = new ByteArrayInputStream(paArray) ;
        DataInputStream hlpInStream = new DataInputStream(hlpByteArrayInputStream) ;
        try {
            //ID = hiprnstrean.readInt();
            rod_cislo = "";
            for (int i= 0; i < maxLengtRodCislo; i++) {
                rod_cislo += hlpInStream.readChar();
            }
            menoLength = hlpInStream.readInt();
            meno = "";
            for (int j = 0; j < maxLengthMeno; j++) {
                char tmp = hlpInStream.readChar();
                if (j < menoLength)
                    meno += tmp;
            }
            priezviskoLength = hlpInStream.readInt();
            priezvisko = "";
            for (int j = 0; j < maxLengthPriezvisko; j++) {
                char tmp = hlpInStream.readChar();
                if (j < priezviskoLength)
                    priezvisko += tmp;
            }
            String datum_narodenia = "";
            for (int j = 0; j < maxLengthDatumNarodenia; j++) {
                datum_narodenia += hlpInStream.readChar();
            }
            if (!datum_narodenia.equals( "----------"))
                d_narodenia = LocalDate.parse(datum_narodenia);
            else
                d_narodenia = null;
            poistovna = hlpInStream.readInt();
            hospitalizacieLength = hlpInStream.readInt();
            //added hospitalization
            hospitalizacie.clear();
            for (int i = 0; i < hospitalizacieLength; i ++) {
                byte [] hosp = hlpByteArrayInputStream.readNBytes((new Hospitalizacia(0, null, null, null)).getSize());
                Hospitalizacia h = new Hospitalizacia(0, null, null, null);
                h.FromByteArray(hosp);
                hospitalizacie.add(h);
            }

        } catch (IOException e){
            throw new IllegalStateException("Error during conversion from byte array.");
        }

    }


}

