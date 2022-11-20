import java.time.LocalDate;
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
    public void runBasicTestBST(BsTree tree, int nRun, int preInsertedData) {
        int maxNum = nRun * 100;
        Random r = new Random();
        int nPreInsertedData = preInsertedData;

        if (nPreInsertedData > 0) {
            for (int i = 0; i < nPreInsertedData; i ++) {
                int var = r.nextInt(maxNum);
                boolean ok = tree.insert(var);
                if (ok) {
                    insertedIndexes.add(var);
                }
            }

        }

        for(int i = 0; i < nRun; i ++ ) {
            int index = 0;

            double possibility = r.nextDouble();

            if (possibility < 0.33) { //insert

                int newRecord = r.nextInt(maxNum);
                boolean ok = tree.insert(newRecord);
                if (ok) {
                    insertedIndexes.add(newRecord);
                }
            } else if (possibility < 0.66) { // delete
                if (insertedIndexes.size() > 0) {
                    index = r.nextInt(insertedIndexes.size());
                    tree.delete(insertedIndexes.get(index));
                    insertedIndexes.remove(index);
                }
            } else { //search
                if (insertedIndexes.size() > 0) {
                    index = r.nextInt(insertedIndexes.size());
                    int data = (int) tree.getData(insertedIndexes.get(index));
                }
            }

        }
        System.out.println("Test status: Successful");
    }

    public void testInsertDelete(BsTree tree ){
        for (int seed = 0; seed < 1000; seed++) {
            Random r = new Random(seed);
            ArrayList<Integer> arr = new ArrayList<>();
            System.out.println("-------------------NEW SEED------------------------" + seed);
            for (int i = 0; i < 20; i ++) {
                int num = r.nextInt(100);
                boolean ok = tree.insert(num);
                if (ok) {
                    //System.out.println("Inserted " + num);
                    arr.add(num);
                }
            }
            //tree.inorder();
            while (arr.size() != 0){
                int index = r.nextInt(arr.size());
                //System.out.println("delete " + arr.get(index));
                tree.delete(arr.get(index));
                arr.remove(index);
            }
        }
    }
    public void testMultipleInsert(BsTree tree ){

        for (int seed = 0; seed < 1000; seed++) {
            Random r = new Random(seed);
            ArrayList<Integer> arr = new ArrayList<>();

            System.out.println("-------------------NEW SEED------------------------" + seed);
            for (int i = 0; i < 1000; i ++) {
                int num = r.nextInt(100);
                if (!arr.contains(num))
                    arr.add(num);
            }

            tree.multipleInsert(arr);
            int maxLevel = tree.getMaxLevel();
            int balancedMaxLevel = (int) Math.ceil(Math.log(tree.size+1) / Math.log(2));
            int maxLevelAfterBalance = tree.getMaxLevel();
            if (maxLevelAfterBalance == balancedMaxLevel) {
                System.out.println("Tree balanced");
            } else {
                System.out.println("ERROR ---------- max level at the beginning" + maxLevel + " max level after balancing " + maxLevelAfterBalance + "Expected level " + balancedMaxLevel );
            }
            tree.cleanup();
        }
    }



    public void testBalancedTree(BsTree tree ){
        for (int seed = 0; seed < 100; seed++) {
            Random r = new Random(seed);
            ArrayList<Integer> arr = new ArrayList<>();
            System.out.println("-------------------NEW SEED------------------------" + seed);
            for (int i = 0; i < 100; i ++) {
                int num = r.nextInt(1000);
                boolean ok = tree.insert(num);
                if (ok) {
                    //System.out.println("Inserted " + num);
                    arr.add(num);
                }
            }

            int maxLevel = tree.getMaxLevel();
            int balancedMaxLevel = (int) Math.ceil(Math.log(tree.size+1) / Math.log(2));
            tree.makeBalance();
            int maxLevelAfterBalance = tree.getMaxLevel();
            if (maxLevelAfterBalance == balancedMaxLevel) {
                System.out.println("Tree balanced");
            } else {
                System.out.println("ERROR ---------- max level at the beginning" + maxLevel + " max level after balancing " + maxLevelAfterBalance + "Expected level " + balancedMaxLevel );
            }
            tree.cleanup();
        }
    }
    public void testDeleteRoot(BsTree tree ){
        for (int seed = 0; seed < 100; seed++) {
            Random r = new Random(seed);
            ArrayList<Integer> arr = new ArrayList<>();
            System.out.println("-------------------NEW SEED------------------------" + seed);
            for (int i = 0; i < 20; i ++) {
                int num = r.nextInt(100);
                boolean ok = tree.insert(num);
                if (ok) {
                    //System.out.println("Inserted " + num);
                    arr.add(num);
                }
            }
            int n = arr.size();
            for (int i = 0; i  < n; i ++){
                //System.out.println("delete Root");
                tree.delete(tree.getRoot().getData());

            }
        }
    }

    public void testInsertRotations(BsTree tree1, BsTree tree2) {
        ArrayList<Double> result = new ArrayList<>();
        for (int seed = 0; seed < 1000; seed++) {
            Random r = new Random(seed);
            tree1.cleanup();
            tree2.cleanup();
            ArrayList<Integer> arr = new ArrayList<>();
            System.out.println("-------------------NEW SEED------------------------" + seed);
            for (int i = 0; i < 50; i ++) {
                int num = r.nextInt(100000);
                boolean ok1 = tree1.insert(num);
                boolean ok2 = tree2.insertWithRotations(num);
                if (ok1 && ok2) {
                    //System.out.println("Inserted " + num);
                    arr.add(num);
                }
            }
            result.add((double) (tree2.getMaxLevel() / (double) tree1.getMaxLevel()));
        }
        double sum  =0;
        for (int i = 0; i < result.size(); i ++)
            sum+=result.get(i);

        System.out.println(sum/ result.size());
    }
    public void testDeleteRotations(BsTree tree1, BsTree tree2) {
        ArrayList<Double> resultMain = new ArrayList<>();
        ArrayList<Double> result = new ArrayList<>();
        for (int seed = 0; seed < 1000; seed++) {
            Random r = new Random(seed);
            tree1.cleanup();
            tree2.cleanup();
            ArrayList<Integer> arr = new ArrayList<>();
            System.out.println("-------------------NEW SEED------------------------" + seed);
            for (int i = 0; i < 50; i ++) {
                int num = r.nextInt(100000);
                boolean ok1 = tree1.insert(num);
                boolean ok2 = tree2.insert(num);
                if (ok1 && ok2) {
                    //System.out.println("Inserted " + num);
                    arr.add(num);
                }
            }
            for (int i = 0; i < arr.size(); i++) {
                int index = r.nextInt(arr.size());
                tree1.delete(arr.get(index));
                tree2.deleteWithRotations(arr.get(index));
                result.add((double) (tree2.getMaxLevel() / (double) tree1.getMaxLevel()));
            }
            double sum  =0;
            for (int i = 0; i < result.size(); i ++)
                sum+=result.get(i);
            resultMain.add(sum/ result.size());
        }
        double sum  =0;
        for (int i = 0; i < resultMain.size(); i ++)
            sum+=resultMain.get(i);

        System.out.println(sum/ resultMain.size());
    }
    public void testFile(Hashing<Pacient> file, int pacientCount) {
        Generator g = new Generator();
        g.generatePacient(pacientCount);
        ArrayList<Pacient> pacienti = g.getPacienti();
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
                file.Insert(p);
            }
        }
        for (Pacient p: pacienti) {
            Pacient found_pacient = file.Find(p);
            if (!p.fullyCompare(found_pacient)) {
                System.out.println("Patients not equals");
                return;
            }
        }
        System.out.println("Test passed");

    }
}
