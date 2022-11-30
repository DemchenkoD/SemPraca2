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

        File myObj = new File("newFile.txt"); //TODO remove
        myObj.delete(); //TODO remove
        //Generator g = new Generator();
        //ArrayList<Pacient> pacienti = g.genPatientsForFile(10);
        DynamicHashing<Pacient> hashing2 = new DynamicHashing<>("newFile.txt", "treeFile", "FreeBlocks", 1, pacient);

        System.out.println("-----------------------------------------------------");

        TestClass test = new TestClass();
        //test.testFile(hashing2, 500 );
        //test.testDynamicFile(hashing2, 500);
        test.testFile2(hashing2, 500);
        System.out.println("-----------------------------------------------------");

    }
}
