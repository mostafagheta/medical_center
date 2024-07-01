import java.util.ArrayList;

public class MedicalRecord {
    private ArrayList<Patient>  patients;
    private Doctor doctor;
    private StringBuilder diagnosis ;



    public MedicalRecord() {
        patients =new ArrayList<Patient>();
        diagnosis = new StringBuilder();
    }

    public MedicalRecord( Doctor doctor) {

        patients =new ArrayList<Patient>();
        this.doctor = doctor;
        diagnosis = new StringBuilder();

    }





    public Doctor getDoctor() {
        return doctor;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setDiagnosis(StringBuilder diagnosis) {
        this.diagnosis = diagnosis;
    }

    public StringBuilder getDiagnosis() {
        return diagnosis;
    }
}

