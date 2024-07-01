import java.util.ArrayList;

public class Patient extends Person {

    private Appointment appointment ;
    private Billing billing ;

    private  String category ;


    public Patient() {

    }

    public Patient(int id, String name , int phone,int age,  Billing billing,Appointment appointment ) {
        super(id, name, phone,age);
        this.billing = billing;
        this.appointment = appointment ;
    }
    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}