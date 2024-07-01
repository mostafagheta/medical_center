public class Billing {
    private int code ;

    private double totalAmount;
    private Doctor doctor ;
    private Patient patient;


    public Billing(  double totalAmount, Doctor doctor, Patient patient) {
        this.totalAmount = totalAmount;
        this.doctor = doctor;
        this.patient = patient;
    }

    public Billing() {
    }

    public int getCode() {
        return code;
    }

    public void setCode() {
        this.code = patient.getId()+10;
    }


    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Billing{" +
                "code=" + code +
                ", totalAmount=" + totalAmount +
                ", doctor=" + doctor +
                ", patient=" + patient +
                '}';
    }
}
