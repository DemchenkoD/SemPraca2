import java.sql.SQLOutput;
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

    public void testFileSpeed(Hashing<Pacient> file, int pacientCount) {
        ArrayList<Double> timeInsert = new ArrayList<>();
        ArrayList<Double> timeFind = new ArrayList<>();
        ArrayList<Double> timeDelete = new ArrayList<>();
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
            long startTime = System.nanoTime();
            boolean insert_passed  = file.Insert(p);
            long endTime = System.nanoTime();
            timeInsert.add((double)(endTime - startTime) / 1000000);
            if(!insert_passed) {
                //System.out.println("Insert didn't pass for patient" + p.toString());
                removePacients.add(p);
            }
        }
        for (Pacient p: removePacients)
            pacienti.remove(p);

        for (Pacient p: pacienti) {
            long startTime = System.nanoTime();
            Pacient found_patient = file.Find(p);
            long endTime = System.nanoTime();
            timeFind.add((double)(endTime - startTime) / 1000000);
            if (!p.fullyCompare(found_patient)) {
                System.out.println("Patient not equals");
                return;
            }
            startTime = System.nanoTime();
            file.Delete(p);
            endTime = System.nanoTime();
            timeDelete.add((double)(endTime - startTime) / 1000000);
            found_patient = file.Find(p);
            if (found_patient != null) {
                System.out.println("Patient exists");
                return;
            }
        }

        double sum = 0;
        for(int i = 0; i < timeInsert.size(); i++)
            sum += timeInsert.get(i);
        double averageInsert = sum / timeInsert.size();
        sum = 0;
        for(int i = 0; i < timeFind.size(); i++)
            sum += timeFind.get(i);
        double averageFind = sum / timeFind.size();
        sum = 0;
        for(int i = 0; i < timeDelete.size(); i++)
            sum += timeDelete.get(i);
        double averageDelete = sum / timeDelete.size();
        //System.out.println("Test passed");
        System.out.println("Average Insert time in milliseconds: " + averageInsert);
        System.out.println("Average Find time in milliseconds: " + averageFind);
        System.out.println("Average Delete time in milliseconds: " + averageDelete);

    }




}
