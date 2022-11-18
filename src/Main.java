import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {

    private static Hashing<Pacient> propertyHashing;



    public static void main(String[] args) {

        //GUI gui = new GUI();

        //propertyHashing = new Hashing<Pacient>("file.dat", 13);


        Pacient pacient = new Pacient("Dmytro", "Demchenko", "0712200196", LocalDate.now(), "sadas");
        Hashing<Pacient> hashing = new Hashing<>("random.txt", 2, 5, pacient);
        hashing.Insert(pacient);
        Pacient p = hashing.Find(new Pacient("", "", "0712200196", null, ""));
        hashing.Delete(new Pacient("", "", "0712200196", null, ""));
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
