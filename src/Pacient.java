import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;
import java.util.Random;

public class Pacient implements IData<Pacient> {
    private final int maxLengthMeno = 15;
    private int menoLength = 0;
    private int priezviskoLength = 0;
    //private int hospitalizacieLength = 0;
    private final int maxLengthPriezvisko = 20;
    private final int maxLengthDatumNarodenia = 10;
    private final int maxLengtRodCislo = 10;
    private final int maxLengtHospitalizacie = 10;
    private String meno;
    private String priezvisko;
    private String rod_cislo;
    private LocalDate d_narodenia;

    private int poistovna;
    private ArrayList<Hospitalizacia> hospitalizacie;
    private ArrayList<Integer> idHospitalizacie;
    public Pacient() {

    }
    public Pacient(String pMeno, String pPriezvisko, String pRod_cislo, LocalDate pD_narodenia, int pZdr_poistovna) {
        meno = pMeno;
        priezvisko = pPriezvisko;
        rod_cislo = pRod_cislo;
        d_narodenia = pD_narodenia;
        poistovna = pZdr_poistovna;
        hospitalizacie = new ArrayList<Hospitalizacia>(maxLengtHospitalizacie);
        for (int i = 0; i < maxLengtHospitalizacie; i++)
            hospitalizacie.add(new Hospitalizacia());
        idHospitalizacie = new ArrayList<>(maxLengtHospitalizacie);

        menoLength = meno.length();
        priezviskoLength = priezvisko.length();

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

    public boolean addHospitalizacia(LocalDateTime pD_zaciatku, LocalDateTime pD_konca, String pDiagnoza ) {
        int id;
        Random r = new Random();
        do {
            id = r.nextInt(1000);
        } while (idHospitalizacie.contains(id));
        if (idHospitalizacie.size() < 10) {
            hospitalizacie.add(idHospitalizacie.size(), new Hospitalizacia(id, pD_zaciatku, pD_konca, pDiagnoza));
            hospitalizacie.remove(hospitalizacie.size() - 1);
            idHospitalizacie.add(id);
            return true;
        } else {
            return false;
        }
    }
    public boolean createHospitalizacia(LocalDateTime pD_zaciatku, String pDiagnoza ) {
        int id;
        Random r = new Random();
        do {
            id = r.nextInt(1000);
        } while (idHospitalizacie.contains(id));
        if (idHospitalizacie.size() < 10) {
            hospitalizacie.add(idHospitalizacie.size(), new Hospitalizacia(id, pD_zaciatku, null, pDiagnoza));
            hospitalizacie.remove(hospitalizacie.size() - 1);
            idHospitalizacie.add(id);
            return true;
        } else {
            return false;
        }
    }
    public boolean removeHospitalizacia(int id) {
        Hospitalizacia h = getHospitalizacia(id);
        if (h != null) {
            hospitalizacie.remove(h);
            hospitalizacie.add(new Hospitalizacia());
            int index = -1;
            for (int i = 0; i < idHospitalizacie.size(); i++) {
                if (idHospitalizacie.get(i) == id)
                    index = i;
            }
            if (index != -1)
                idHospitalizacie.remove(index);
            return true;
        }
        else
            return false;
    }
    public boolean closeHospitalizacia(int id, LocalDateTime pD_koniec) {
        Hospitalizacia h = getHospitalizacia(id);
        if (h != null) {
            h.setD_konca(pD_koniec);
            return true;
        }
        else
            return false;
    }
    public Hospitalizacia getHospitalizacia(int id){
        for (int i = 0; i < idHospitalizacie.size(); i++) {
            if (hospitalizacie.get(i).getId() == id)
                return hospitalizacie.get(i);
        }
        return null;
    }

    public ArrayList<Hospitalizacia> getHospitalizacie() {
        return hospitalizacie;
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
        return (rod_cislo + '\t' + meno + '\t' + priezvisko + '\t' + d_narodenia.toString() + '\t' + poistovna);
    }
    public String toStringFull() {
        String result =  (rod_cislo + '\t' + meno + '\t' + priezvisko + '\t' + d_narodenia.toString() + '\t' + poistovna + '\n');
        for (int i = 0; i < idHospitalizacie.size(); i ++) {
            result += hospitalizacie.get(i).toString(); //TODO remove
        }
        if (idHospitalizacie.size() == 0)
            result += "Patient has no hospitalizations";
        return result;
    }

    public String toStringHospitalizacie() {
        String result = "";
        for (Hospitalizacia h: hospitalizacie) {
            result += h.toString() + '\n';
        }
        return result;
    }

    @Override
    public int getSize() {
        return Character.BYTES * (maxLengthMeno + maxLengthPriezvisko + maxLengtRodCislo + 10) +
                4*Integer.BYTES + maxLengtHospitalizacie*((new Hospitalizacia(0, null, null, null)).getSize()); //TODO addHospitalizacie size

    }
    @Override
    public BitSet getHash() {

        BitSet hash;
        hash = BitSet.valueOf (new long[] {Long.parseLong(this.rod_cislo)});
        return hash;
    }

    @Override
    public boolean myEquals(Pacient data) {
        if (!this.rod_cislo.equals("") && !data.rod_cislo.equals(""))
            return Long.parseLong(this.rod_cislo) == Long.parseLong(data.rod_cislo);
        else
            return this.rod_cislo.equals(data.rod_cislo);
    }

    @Override
    public Pacient createClass() {
        return new Pacient("","","",null,-1);
    }

    @Override
    public void print() {
        System.out.println("P\t" +  meno + '\t' + priezvisko + '\t' + rod_cislo + '\t'+ d_narodenia.toString() + '\t' + poistovna + '\n');
        for (int i = 0; i < idHospitalizacie.size(); i ++) {
            //hospitalizacie.get(i).print(); //TODO remove
        }
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
            hlpOutStream.writeInt(idHospitalizacie.size()); //number of real hospitalisations

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
            if (rod_cislo.equals("----------"))
                rod_cislo = "";
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
            int realHosp = hlpInStream.readInt();
            //added hospitalization
            hospitalizacie.clear();
            idHospitalizacie.clear();
            for (int i = 0; i < maxLengtHospitalizacie; i ++) {
                byte [] hosp = hlpByteArrayInputStream.readNBytes((new Hospitalizacia(0, null, null, null)).getSize());
                Hospitalizacia h = new Hospitalizacia(0, null, null, null);
                h.FromByteArray(hosp);
                hospitalizacie.add(h);
                if (h.getId() != -99) {
                    idHospitalizacie.add(h.getId());
                }
            }
            if (realHosp != idHospitalizacie.size()) {
                System.out.println("Error: Hospitalizacie neboli nacitane spravne");
            }

        } catch (IOException e){
            throw new IllegalStateException("Error during conversion from byte array.");
        }

    }

    public boolean fullyCompare(Pacient p) {
        boolean ok = true;
        if(p == null)
            System.out.println("NULL");


        try {
            if (!this.meno.equals(p.meno))
                ok = false;
            else if (!this.priezvisko.equals(p.priezvisko))
                ok = false;
            else if (!this.rod_cislo.equals(p.rod_cislo))
                ok = false;
            else if (this.poistovna != p.poistovna)
                ok = false;
            else if (this.d_narodenia.compareTo(p.d_narodenia) != 0)
                ok = false;
            else {
                ArrayList<Hospitalizacia> hospitalizacie2 = p.getHospitalizacie();
                for (int i = 0; i < hospitalizacie.size(); i++) {
                    if (!hospitalizacie.get(i).fullyCompare(hospitalizacie2.get(i))) {
                        ok = false;
                        System.out.println("Hospitalizations not equals");
                        break;
                    }

                }
            }
            return ok;
        } catch (NullPointerException n ) {
            System.out.println("Error");
            return ok;
        }

    }


}

