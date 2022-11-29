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
        DynamicHashing<Pacient> hashing2 = new DynamicHashing<>("newFile.txt", "treeFile", "FreeBlocks", 10, pacient);
        hashing2.Insert(pacient);
        //for (Pacient p: pacienti)
        //    hashing2.Insert2(p);
        hashing2.vypis();
        hashing2.Find(new Pacient("", "", "0712200196", LocalDate.now(), 99));


        TestClass test = new TestClass();
        test.testFile(hashing2, 500 );

        System.out.println("-----------------------------------------------------");



        System.out.println("Hello");
    }
}
