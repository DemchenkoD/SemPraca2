import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    private Hashing<Pacient> file;
    private Pacient dataInitial;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public Program() {
        this.dataInitial = new Pacient("", "", "", LocalDate.now(), -5);
    }

    public void createNewStatFile(String fileName, int blockFactor, int countData) {
        this.file = new Hashing(fileName, blockFactor, countData, this.dataInitial);
    }
    public void createOldStatFile(String fileName, String confFileName) {
        this.file = new Hashing(fileName, confFileName, this.dataInitial);
    }
    public void createNewDynFile(String fileName, int blockFactor) {
        this.file = new DynamicHashing(fileName, blockFactor, dataInitial);
    }
    public void createOldDynFile(String fileName, String treeFileName, String freeBlocksFileName, String confFileName) {
        this.file = new DynamicHashing(fileName, treeFileName, freeBlocksFileName, confFileName, dataInitial);
    }

    public ArrayList<String> fillDatabase(int pacNum, int hospNum) {
        ArrayList<String> logs = new ArrayList<>();
        try {
            Generator g = new Generator();
            ArrayList<Pacient> genData = g.genPatientsForFile(pacNum, hospNum);
            int count = 0;
            for(int i = 0; i < genData.size(); i++) {
                if(file.Insert(genData.get(i)))
                    count++;
            }
            logs.add(count + " dat bolo uspesne vygenerovano");
        } catch (Exception e) {
            logs.add(e.getMessage());
        }
        return logs;
    }
    public ArrayList<String> task1 (String pRodCislo) {
        ArrayList<String> logs = new ArrayList<>();
        Pacient p = file.Find(new Pacient("", "", pRodCislo, null, -99));
        if (p != null)
            logs.add(p.toStringFull());
        else
            logs.add("Pacient neexistuje");
        return logs;
    }

    public ArrayList<String> task2 (String pRodCislo, int pId) {
        ArrayList<String> logs = new ArrayList<>();
        Pacient p = file.Find(new Pacient("", "", pRodCislo, null, -99));
        if (p != null) {
            Hospitalizacia h = p.getHospitalizacia(pId);
            if (h != null)
                logs.add(h.toString());
            else
                logs.add("Hospitaizacia neexistuje");
        }else
            logs.add("Pacient neexistuje");
        return logs;
    }
    public ArrayList<String> task3(String pRodCislo, LocalDateTime pZaciatok, String pDiagnoza) {
        ArrayList<String> logs = new ArrayList<String>();
        Pacient p = file.Find(new Pacient("", "", pRodCislo, null, -99));
        if (p != null) {
            if (p.createHospitalizacia(pZaciatok,pDiagnoza)) {
                file.Update(p);
                logs.add("Hospitalizacia uspesne vytvorena");
            }
            else
                logs.add("Hospitalizacia nebola vytvorena");
        }else
            logs.add("Pacient neexistuje");
        return logs;
    }
    public ArrayList<String> task4(int id, String pRodCislo, LocalDateTime pKoniec) {
        ArrayList<String> logs = new ArrayList<String>();
        Pacient p = file.Find(new Pacient("", "", pRodCislo, null, -99));
        if (p != null) {
            if (p.closeHospitalizacia(id, pKoniec)) {
                file.Update(p);
                logs.add("Hospitalizacia uspesne ukoncena");
            }
            else
                logs.add("Hospitalizacia nebola ukoncena");
        }else
            logs.add("Pacient neexistuje");
        return logs;
    }
    public ArrayList<String> task5(String pMeno, String pPriezvisko, String pRod_cislo, LocalDate pD_narodenia, int pZdr_poistovna) {
        ArrayList<String> logs = new ArrayList<>();
        if (pZdr_poistovna > 0 && pZdr_poistovna <= 255 && file.Insert(new Pacient(pMeno, pPriezvisko, pRod_cislo, pD_narodenia, pZdr_poistovna)))
            logs.add("Pacient bol uspesne pridany");
        else
            logs.add("Chyba");
        return logs;
    }
    public ArrayList<String> task6(String pRodCislo, int pId) {
        ArrayList<String> logs = new ArrayList<String>();
        Pacient p = file.Find(new Pacient("", "", pRodCislo, null, -99));
        if (p != null) {
            if (p.removeHospitalizacia(pId)) {
                file.Update(p);
                logs.add("Hospitalizacia uspesne vymazana");
            }
            else
                logs.add("Hospitalizacia nebola vymazana");
        }else
            logs.add("Pacient neexistuje");
        return logs;
    }
    public ArrayList<String> task7( String paRodCislo) {
        ArrayList<String> logs = new ArrayList<>();
        if (file.Delete(new Pacient("", "", paRodCislo, null, -99)))
            logs.add("Patient successfully deleted");
        else
            logs.add("Error");
        //logs.addAll(n.vytvorit_doklady(pMesiac));

        return logs;
    }

    public ArrayList<String> vypis() {
        return file.vypis();
    }
    public ArrayList<String> genConf() {
        ArrayList<String> logs = new ArrayList<>();
        file.writeConfToFile();
        logs.add("Konfiguracie boli ulozene");
        return logs;
    }

}
