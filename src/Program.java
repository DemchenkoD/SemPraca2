import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public Program() {
    }
    public ArrayList<String> fillDatabase(String nemNum, String pacNum, String hospNum) {
        ArrayList<String> logs = new ArrayList<>();
        try {
            Generator g = new Generator();
            int nemocnici_num = Integer.parseInt(nemNum);
            int pacienti_num = Integer.parseInt(pacNum);
            int hositalizacie_num = Integer.parseInt(hospNum);
            g.generate(nemocnici_num, pacienti_num, hositalizacie_num);
            //nemocnice.multipleInsert(g.getNemocnice());
            //pacienti.multipleInsert(g.getPacienti());
            logs.add("Data boli uspesne vygenerovane");
        } catch (Exception e) {
            logs.add(e.getMessage());
        }
        return logs;
    }
    public ArrayList<String> task1 (String pRodCislo, String pNazovNemocnice) {
        ArrayList<String> logs = new ArrayList<>();

        return logs;
    }

    public ArrayList<String> task2 (String pNazovNemocnice,String pMeno, String pPriezvisko) {
        ArrayList<String> logs = new ArrayList<>();

        return logs;
    }
    public ArrayList<String> task3(String pRodCislo, String pNazovNemocnice, LocalDateTime pZaciatok, String pDiagnoza) {

        ArrayList<String> logs = new ArrayList<String>();

        return logs;
    }
    public ArrayList<String> task4(String pRodCislo, String pNazovNemocnice, LocalDateTime pKoniec) {
        ArrayList<String> logs = new ArrayList<String>();

        return logs;
    }
    public ArrayList<String> task5(String pNazovNemocnice, LocalDateTime pOd, LocalDateTime pDo) {
        ArrayList<String> logs = new ArrayList<>();

        return logs;
    }
    public ArrayList<String> task6(String pMeno, String pPriezvisko, String pRod_cislo, LocalDate pD_narodenia, String pZdr_poistovna) {
        ArrayList<String> logs = new ArrayList<String>();
        //if (pacienti.insert(new Pacient(pMeno, pPriezvisko, pRod_cislo, pD_narodenia, pZdr_poistovna)))
        //    logs.add("Pacient bol uspesne pridany");
        //else
        //    logs.add("Chyba");
        return logs;
    }
    public ArrayList<String> task7( String pNazovNemocnice, LocalDateTime pMesiac) {
        ArrayList<String> logs = new ArrayList<>();

        //logs.addAll(n.vytvorit_doklady(pMesiac));

        return logs;
    }

}
