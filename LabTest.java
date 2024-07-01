import java.util.ArrayList;

public class LabTest {
    String name;
    String type;
    private ArrayList<Patient> patients;

    public LabTest() {
        this.patients =new ArrayList<Patient>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }


}
