import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    private static Hashing<Pacient> propertyHashing;



    public static void main(String[] args) {

        //GUI gui = new GUI();

        Pacient pacient = new Pacient("Dmytro", "Demchenko", "0712200196", LocalDate.now(), 99);

        //Hashing<Pacient> hashing = new Hashing<>("random2.txt", 10, 10000, pacient);
        File myObj = new File("newFile.txt"); //TODO remove
        myObj.delete();
        Generator g = new Generator();
        ArrayList<Pacient> pacienti = g.genPatientsForFile(10);
        DynamicHashing<Pacient> hashing2 = new DynamicHashing<>("newFile.txt", "treeFile", "FreeBlocks", 2, pacient);
        hashing2.Insert2(pacient);
        for (Pacient p: pacienti)
            hashing2.Insert2(p);
        hashing2.vypis();
        //hashing2.Find2(new Pacient("", "", "0712200196", LocalDate.now(), 99));
        //TestClass test = new TestClass();
        //test.testFile(hashing, 5000 );
        Pacient p = pacienti.get(2);
        p = hashing2.Find2(pacienti.get(2));
        p =  hashing2.Find2(pacient);
        System.out.println("-----------------------------------------------------");
        //hashing2.Delete2(p);
        ArrayList<ExternyVrchol> res3 = hashing2.getExterneVrcholi();
        for(ExternyVrchol ext : res3)
            ext.print();

        hashing2.writeTreeToFile("tree.txt");
        hashing2.readTreeFromFile("tree.txt");
        hashing2.writeTreeToFile("tree2.txt");

        //for (Pacient p2: pacienti)
        //    hashing2.Delete2(p2);
        hashing2.vypis();
        System.out.println("Hello");
    }
}
