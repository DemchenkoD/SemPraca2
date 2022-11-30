import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestClass {
    ArrayList<Integer> insertedIndexes;
    public TestClass() {
        insertedIndexes = new ArrayList<>();

    }
    public void testFile(Hashing<Pacient> file, int pacientCount) {
        Generator g = new Generator();
        g.generatePacient(pacientCount);
        ArrayList<Pacient> pacienti = g.getPacienti();
        ArrayList<Pacient> removePacients = new ArrayList<>();
        for (Pacient p: pacienti) {
            for(int i = 0; i < 10; i++){
                LocalDateTime from = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
                from = from.plusSeconds(ThreadLocalRandom.current().nextLong(10000));
                LocalDateTime to = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
                long days = from.until(to, ChronoUnit.DAYS);
                long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
                LocalDateTime datumZaciatku = from.plusDays(randomDays);
                long daysHospitalized = ThreadLocalRandom.current().nextLong(150 + 1);
                LocalDateTime datumKoniec = datumZaciatku.plusDays(daysHospitalized);

                String diagnoza = g.genString(10);
                p.addHospitalizacia(datumZaciatku, datumKoniec,diagnoza);
            }
            boolean insert_passed  = file.Insert(p);
            if(!insert_passed) {
                System.out.println("Insert didn't pass for patient" + p.toString());
                removePacients.add(p);
                break;
            }
        }
        for (Pacient p: removePacients)
            pacienti.remove(p);

        for (Pacient p: pacienti) {
            Pacient found_patient = file.Find(p);
            if (!p.fullyCompare(found_patient)) {
                System.out.println("Patient not equals");
                return;
            }
            file.Delete(p);
            found_patient = file.Find(p);
            if (found_patient != null) {
                System.out.println("Patient exists");
                return;
            }
        }
        System.out.println("Test passed");

    }

    public void testDynamicFile(DynamicHashing<Pacient> file, int pacientCount) {

        Generator g = new Generator();
        g.generatePacient(pacientCount);
        ArrayList<Pacient> pacienti = g.getPacienti();
        ArrayList<Pacient> removePacients = new ArrayList<>();
        for (Pacient p: pacienti) {
            for(int i = 0; i < 10; i++){
                LocalDateTime from = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
                from = from.plusSeconds(ThreadLocalRandom.current().nextLong(10000));
                LocalDateTime to = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
                long days = from.until(to, ChronoUnit.DAYS);
                long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
                LocalDateTime datumZaciatku = from.plusDays(randomDays);
                long daysHospitalized = ThreadLocalRandom.current().nextLong(150 + 1);
                LocalDateTime datumKoniec = datumZaciatku.plusDays(daysHospitalized);

                String diagnoza = g.genString(10);
                p.addHospitalizacia(datumZaciatku, datumKoniec,diagnoza);
            }
            boolean insert_passed  = file.Insert(p);
            if(!insert_passed) {
                System.out.println("Insert didn't pass for patient" + p.toString());
                removePacients.add(p);
                break;
            }
        }

        file.vypis();
        for (Pacient p: removePacients)
            pacienti.remove(p);

        for (Pacient p: pacienti) {
            Pacient found_patient = (Pacient) file.Find(p);
            if (!p.fullyCompare(found_patient)) {
                System.out.println("Patient not equals");
                return;
            }
            file.Delete(p);
            found_patient = (Pacient) file.Find(p);
            if (found_patient != null) {
                System.out.println("Patient exists");
                return;
            }
        }
        file.vypis();
        System.out.println("Test passed");

    }
    public void testFile2(DynamicHashing<Pacient> file, int pacientCount) {
        Generator g = new Generator();
        g.generatePacient(pacientCount);
        ArrayList<Pacient> pacienti = g.getPacienti();
        ArrayList<Pacient> removePacients = new ArrayList<>();
        for (int seed = 0; seed < 10; seed++) {
            Random r = new Random(seed);
            System.out.println("SEEED " + seed);
            for (Pacient p : pacienti) {
                for (int i = 0; i < 10; i++) {
                    LocalDateTime from = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
                    from = from.plusSeconds(ThreadLocalRandom.current().nextLong(10000));
                    LocalDateTime to = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
                    long days = from.until(to, ChronoUnit.DAYS);
                    long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
                    LocalDateTime datumZaciatku = from.plusDays(randomDays);
                    long daysHospitalized = ThreadLocalRandom.current().nextLong(150 + 1);
                    LocalDateTime datumKoniec = datumZaciatku.plusDays(daysHospitalized);

                    String diagnoza = g.genString(10);
                    p.addHospitalizacia(datumZaciatku, datumKoniec, diagnoza);
                }
                boolean insert_passed = file.Insert(p);
                if (!insert_passed) {
                    System.out.println("Insert didn't pass for patient" + p.toString());
                    removePacients.add(p);
                    break;
                }
            }
            for (Pacient p : removePacients)
                pacienti.remove(p);

            for (Pacient p : pacienti) {
                Pacient found_patient = file.Find(p);
                if (found_patient == null)
                    System.out.println("NUUL");
                if (!p.fullyCompare(found_patient)) {
                    System.out.println("Patient not equals");
                    return;
                }
                file.Delete(p);
                found_patient = file.Find(p);
                if (found_patient != null) {
                    System.out.println("Patient exists");
                    return;
                }
            }
            if(file.freeAdresses.size()!= 0) {
                System.out.println("Error");
                break;
            }
            //file.vypis();
            System.out.println("Test passed");
        }

    }



}
