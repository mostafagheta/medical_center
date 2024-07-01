import java.util.ArrayList;

public class MedicalCenter {
    private ArrayList<Doctor> doctors ;
    private  ArrayList<Patient> patients;

    private  ArrayList<Billing> billings;



    private static   String address = "Menouf" ;
    private static   String name = "GK medical center";
    private static   String  phone = "3535320";

    public MedicalCenter() {
        this.doctors = new ArrayList<Doctor>();
        this.patients = new ArrayList<Patient>();
        this.billings = new ArrayList<Billing>();

    }

    public static String getName(){
        return name;
    }
    public static String getAddress() {
        return address;
    }

    public static String getPhone() {
        return phone;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }



    public ArrayList<Billing> getBillings() {
        return billings;
    }



}
