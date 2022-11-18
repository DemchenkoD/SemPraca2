import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
    //private ArrayList<Nemocnica> nemocnice;
    private ArrayList<Pacient> pacienti;
    private  ArrayList<Hospitalizacia> hospitalizacie;
    private HashSet<String> generatedRodCisla;
    public Generator() {
        //nemocnice = new ArrayList<Nemocnica>();
        pacienti = new ArrayList<Pacient>();
        hospitalizacie = new ArrayList<Hospitalizacia>();
        generatedRodCisla = new HashSet<String>();
    }

    //public ArrayList<Nemocnica> getNemocnice() {
    //    return nemocnice;
    //}

    public ArrayList<Pacient> getPacienti() {
        return pacienti;
    }

    public ArrayList<Hospitalizacia> getHospitalizacie() {
        return hospitalizacie;
    }

    public void generate (int numNemocnice, int numPacienti, int numHospitalizacie) {
        //generateNemocnice(numNemocnice);
        generatePacient(numPacienti);
        generateHospitalizacie(numHospitalizacie);
    }
    public void generateHospitalizacie(int pocet) {
/*
        Random r = new Random();
        if (pacienti.size() == 0 || nemocnice.size() == 0)
            return;
        for (int i = 0; i < pocet; i ++) {

            Pacient pacient = pacienti.get(r.nextInt(pacienti.size()));
            Nemocnica nemocnica = nemocnice.get(r.nextInt(nemocnice.size()));

            LocalDateTime from = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
            from = from.plusSeconds(ThreadLocalRandom.current().nextLong(10000));
            LocalDateTime to = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
            long days = from.until(to, ChronoUnit.DAYS);
            long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
            LocalDateTime datumZaciatku = from.plusDays(randomDays);
            long daysHospitalized = ThreadLocalRandom.current().nextLong(150 + 1);
            LocalDateTime datumKoniec = datumZaciatku.plusDays(daysHospitalized);

            String diagnoza = genString(10);
            hospitalizacie.add(new Hospitalizacia(nemocnica.getNazov(), pacient.getRod_cislo(), datumZaciatku, datumKoniec, diagnoza, pacient.getZdr_poistovna()));
            pacient.addHospitalizacia(nemocnica.getNazov(), datumZaciatku, datumKoniec, diagnoza);

            nemocnica.addHospitalizacia(pacient, datumZaciatku, datumKoniec, diagnoza);

        }
        */

    }
    public void generatePacient(int pocet) {
        for (int i = 0; i < pocet; i ++) {
            String meno = genString(10);
            String priezvisko = genString(15);
            String rod_cislo;
            do {
                rod_cislo = genRodCislo();
            } while (generatedRodCisla.contains(rod_cislo));
            generatedRodCisla.add(rod_cislo);

            LocalDate from = LocalDate.of(1920, 1, 1);
            LocalDate to = LocalDate.of(2010, 1, 1);
            long days = from.until(to, ChronoUnit.DAYS);
            long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
            LocalDate d_narodenia = from.plusDays(randomDays);

            String zdr_poistovna = genString(10);
            pacienti.add(new Pacient(meno, priezvisko, rod_cislo, d_narodenia, zdr_poistovna));
        }
    }
    /*
    public void generateNemocnice(int pocet) {
        for (int i = 0; i < pocet; i ++) {
            nemocnice.add(new Nemocnica(genString(15)));
        }
    }
    */
    private String genString(int length) {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index= (int)(characters.length() * Math.random());
            sb.append(characters.charAt(index));
        }
        return sb.toString();

    }
    private String genRodCislo() {

        String numbers = "0123456789";
        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index= (int)(numbers.length() * Math.random());
            sb.append(numbers.charAt(index));
        }
        return sb.toString();

    }
    private String genDate(int yearStart, int yearEnd) {
        GregorianCalendar gc = new GregorianCalendar();
        Random r = new Random();
        int year = r.nextInt(yearEnd - yearStart) + yearStart;

        gc.set(gc.YEAR, year);

        int dayOfYear = r.nextInt( gc.getActualMaximum(gc.DAY_OF_YEAR)) + 1;

        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return (gc.get(gc.DAY_OF_MONTH) + "." + (gc.get(gc.MONTH) + 1) + "." + gc.get(gc.YEAR));

    }
}
