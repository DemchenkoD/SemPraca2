import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {

    private static Hashing<Pacient> propertyHashing;



    public static void main(String[] args) {

        GUI gui = new GUI();

        //propertyHashing = new Hashing<Pacient>("file.dat", 13);


        Pacient pacient = new Pacient("Dmytro", "Demchenko", "0712200196", LocalDate.now(), 99);
        //pacient.addHospitalizacia(LocalDateTime.now(), LocalDateTime.now().plusDays(1), "Diagnoza");
        /*
        Hashing<Pacient> hashing = new Hashing<>("random2.txt", 3, 20, pacient);
        TestClass test = new TestClass();
        test.testFile(hashing, 12);

         */
        //hashing.Insert(pacient);
        //Pacient p = hashing.Find(new Pacient("", "", "0712200196", null, 0));
        //hashing.Delete(new Pacient("", "", "0712200196", null, 0));
        System.out.println("Hello");
        /*
        Pacient pacient = new Pacient("Dmytro", "Demchenko", "0712200196", LocalDate.now(), "sadas");
        pacient.addHospitalizacia(LocalDateTime.now(), LocalDateTime.now().plusDays(1), "diagnoza");
        pacient.addHospitalizacia(LocalDateTime.now(), LocalDateTime.now().plusDays(1), "diagnoza2");
        pacient.addHospitalizacia(LocalDateTime.now(), LocalDateTime.now().plusDays(1), "diagnoza3");
        pacient.FromByteArray(pacient.ToByteArray());
        //propertyHashing.Insert (hlpProperty) ;

*/
    }
}
