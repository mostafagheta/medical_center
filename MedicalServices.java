import java.util.ArrayList;

public class MedicalServices {
    MedicalCenter medicalCenter;


    public MedicalServices(MedicalCenter medicalCenter) {
        this.medicalCenter = medicalCenter;
    }

    public ArrayList<Doctor> getDoctors() {
        return medicalCenter.getDoctors();
    }
    public ArrayList<Patient> getPatients() {
        return medicalCenter.getPatients();
    }
    public MedicalCenter getMedicalCenter() {
        return medicalCenter;
    }
    public void addPatient( Patient patient ){
        this.medicalCenter.getPatients().add(patient);
    }
    public void addDoctor( Doctor doctor ){
        this.medicalCenter.getDoctors().add(doctor);
    }

    public void addBillings( Billing billing ){
        this.medicalCenter.getBillings().add(billing);
    }
    public void removePatient( Patient patient ){
        this.medicalCenter.getPatients().remove(patient);
    }
    public void removeDoctor( Doctor doctor ){
        this.medicalCenter.getDoctors().remove(doctor);
    }

    public void removeBillings( Billing billing ){
        this.medicalCenter.getBillings().remove(billing);
    }
    public Doctor getDoctorByName(String name)
    {
        Doctor x = null ;
        for(Doctor doctor : this.medicalCenter.getDoctors())
        {
            if(doctor.getName().equals(name)){
                x = doctor ;
                break ;
            }
        }
        return x;
    }
    public Doctor getDoctorByID(int Id)
    {
        Doctor x = null ;
        for(Doctor doctor : this.medicalCenter.getDoctors())
        {
            if(doctor.getId() == Id){
                x = doctor ;
                break ;
            }
        }
        return x;
    }
    public ArrayList<Doctor> getDoctorsByCategory(String category)
    {
        ArrayList<Doctor> x = new ArrayList<Doctor>() ;
        for(Doctor doctor : this.medicalCenter.getDoctors())
        {
            if(doctor.getCategory().equals(category)){
                x.add(doctor) ;

            }
        }
        return x;
    }
    public ArrayList<Patient> getPatientsByDoctorId(int id)
    {
        ArrayList<Patient> x = new ArrayList<Patient>() ;
        for(Billing billing : this.medicalCenter.getBillings())
        {
            if(billing.getDoctor().getId()==id){
                x.add(billing.getPatient()) ;

            }
        }
        return x;
    }

    public Patient getPatientByName(String name)
    {
        Patient x = null ;
        for(Patient patient : this.medicalCenter.getPatients())
        {
            if(patient.getName().equals(name)){
                x = patient ;
                break ;
            }
        }
        return x;
    }
    public Patient getPatientByID(int id)
    {
        Patient x = null ;
        for(Patient patient : this.medicalCenter.getPatients())
        {
            if(patient.getId() == id){
                x = patient ;
                break ;
            }
        }
        return x;
    }
    public Billing getBillingByCode(int code)
    {
        Billing x = null ;
        for(Billing billing : this.medicalCenter.getBillings())
        {
            if(billing.getCode()==code){
                x = billing ;
                break ;
            }
        }
        return x;
    }

    public void addAppointment( Appointment appointment, int id ){
        Patient v = getPatientByID(id);
        if(v ==null)
        {
            return;
        }
        else {
            v.setAppointment(appointment);
        }
    }

    public void removeMedicine( Medicine medicine ){
        //this.medicalCenter.getPatients().getMedicines().remove(medicine);
    }



}
