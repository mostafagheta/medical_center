import java.sql.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Scanner;

public class Hmi_db
{
    public static void start() throws SQLException {
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/medical_center",
                "root",
                "0000"

        );
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement = null;
        ResultSet  resultSet ;
        Scanner scanner = new Scanner(System.in);
        MedicalCenter medicalCenter = new MedicalCenter();
        MedicalServices medicalServices = new MedicalServices(medicalCenter);
        System.out.println(medicalCenter.getName());
        System.out.println(medicalCenter.getAddress());
        System.out.println(medicalCenter.getPhone());
        int action ;
        System.out.println("Welcome to our medical center " );
        do{System.out.println("For patient services press : 1");
            System.out.println("For doctor services press : 2");
            System.out.println("For exit press : 0");
            System.out.println("please enter choice :");
            action = scanner.nextInt();
            if(1 ==action)
            { int i;
                do {
                    System.out.println("If you are registering a reservation for the first time press 1");
                    System.out.println("If you are already registered :2");
                    System.out.println("For exit press : 0");
                    System.out.println("please enter choice :");
                    i = scanner.nextInt();
                    if(i == 1){
                        Patient patient = new Patient();
                        Billing billing =  new Billing();
                        Doctor doctor = new Doctor();
                        System.out.println("please enter your first name");
                        patient.setName(scanner.next());
                        System.out.println("please enter your last name");
                        patient.setLast_name(scanner.next());
                        System.out.println("please enter your id");
                        patient.setId(scanner.nextInt());
                        System.out.println("please enter your Phone");
                        patient.setPhone(scanner.nextInt());
                        System.out.println("please enter your age");
                        patient.setAge(scanner.nextInt());
                        System.out.println("please enter your consultation type");
                        System.out.println("DENTISTRY");
                        System.out.println("ENT");
                        System.out.println("NEUROLOGY");
                        System.out.println("DERMATOLOGY");
                        patient.setCategory(scanner.next());
                        Appointment appointment = new Appointment();
                        String f ;
                        int doctor_id ;
                        System.out.println("please choose id of one doctor to register with him");
                        try{
                            preparedStatement = connection.prepareStatement("SELECT * FROM doctor WHERE category = ?");
                            preparedStatement.setString(1,patient.getCategory());
                            resultSet = preparedStatement.executeQuery();
                            while (resultSet.next())
                            {
                                System.out.println(resultSet.getString("name"));
                                System.out.println(resultSet.getString("startTime"));
                                System.out.println(resultSet.getString("endTime"));
                                System.out.println(resultSet.getString("daysAvailable1"));
                                System.out.println(resultSet.getString("daysAvailable2"));
                                System.out.println(resultSet.getString("daysAvailable3"));
                                System.out.println((resultSet.getInt("id")));
                            }}
                        catch (SQLException e){
                            e.printStackTrace();
                        }
                        doctor_id = scanner.nextInt();
                        preparedStatement = connection.prepareStatement("SELECT * FROM doctor WHERE id = ?");
                        preparedStatement.setInt(1,doctor_id);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next())
                        {
                            doctor.setName(resultSet.getString("name"));
                            doctor.setLast_name(resultSet.getString("last_name"));

                            doctor.setStartTime(resultSet.getString("startTime"));
                            doctor.setEndTime(resultSet.getString("endTime"));
                            String[] arr_day = new String[3];
                            arr_day[0]=resultSet.getString("daysAvailable1");
                            arr_day[1]=resultSet.getString("daysAvailable2");
                            arr_day[2]=resultSet.getString("daysAvailable3");
                            doctor.setDaysAvailable(arr_day);

                            doctor.setId(resultSet.getInt("id"));
                            doctor.setPhone(resultSet.getInt("phone"));
                            doctor.setSalary(resultSet.getFloat("salary"));
                            doctor.setCategory(resultSet.getString("category"));
                        }
                        billing.setDoctor(doctor);
                        if(patient.getCategory().toUpperCase().equals("DENTISTRY"))
                        {
                            billing.setTotalAmount(300);
                        } else if(patient.getCategory().toUpperCase().equals( "DERMATOLOGY"))//امراض جلدية
                        {
                            billing.setTotalAmount(250);
                        }
                        else if(patient.getCategory().toUpperCase().equals( "ENT"))
                        {
                            billing.setTotalAmount(250);
                        }
                        else if(patient.getCategory().toUpperCase().equals( "NEUROLOGY"))
                        {
                            billing.setTotalAmount(350);
                        }
                        else {
                            System.out.println("error!! please try again ");
                        }
                        System.out.println("for appointment please enter your day from days of your doctor");
                        f = scanner.next();
                        appointment.setDay(f);
                        System.out.println("please enter your time in this format HH:mm:ss from the period of the doctor's presence ");
                        f = scanner.next();
                        appointment.setTime(f);
                        patient.setAppointment(appointment);
                        patient.setBilling(billing);
                        billing.setPatient(patient);
                        billing.setCode();
                        billing.setDoctor(doctor);
                        medicalCenter.getBillings().add(billing);
                        medicalCenter.getPatients().add(patient);
                        medicalCenter.getDoctors().add(doctor);
                        preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE id =?");
                        preparedStatement.setInt(1,patient.getId());
                        resultSet = preparedStatement.executeQuery();
                        ArrayList<Integer> list = new ArrayList<Integer>();
                        while (resultSet.next())
                        {
                            list.add(resultSet.getInt("id")) ;
                        }

                        if(list.isEmpty())
                        {
                            medicalServices.addPatient(patient);
                            medicalServices.addBillings(billing);
                            medicalServices.addDoctor(doctor);
                            try{
                                preparedStatement = connection.prepareStatement("INSERT INTO patient(id,name ,last_name, age , phone ,category,appointement_day,appointement_time,billing_code) VALUES (?,?,?,?,?,?,?,?,?)");
                                preparedStatement.setInt(1,patient.getId());
                                preparedStatement.setString(2,patient.getName());
                                preparedStatement.setString(3,patient.getLast_name());
                                preparedStatement.setInt(4,patient.getAge());
                                preparedStatement.setInt(5,patient.getPhone());
                                preparedStatement.setString(6,patient.getCategory());
                                preparedStatement.setString(7,appointment.getDay());
                                preparedStatement.setString(8,appointment.getTime());
                                preparedStatement.setInt(9,billing.getCode());
                                preparedStatement.executeUpdate();}
                            catch (SQLException e){
                                e.printStackTrace();
                            }

                            try{
                                preparedStatement = connection.prepareStatement("INSERT INTO billing(code,totalAmount,doctor_id,patient_id) VALUES (?,?,?,?)");
                                preparedStatement.setInt(1,billing.getCode());
                                preparedStatement.setDouble(2,billing.getTotalAmount());
                                preparedStatement.setInt(3,doctor.getId());
                                preparedStatement.setInt(4,patient.getId());
                                preparedStatement.executeUpdate();}
                            catch (SQLException e){
                                e.printStackTrace();
                            }
                            try {
                                preparedStatement = connection.prepareStatement("UPDATE patient  SET billing_code = ? WHERE id = ?");
                                preparedStatement.setInt(1,billing.getCode());
                                preparedStatement.setInt(2,patient.getId());
                            }
                            catch (SQLException e){
                                e.printStackTrace();
                            }
                            try{
                                preparedStatement = connection.prepareStatement("INSERT INTO medical_cente(doctor_id,patient_id,billing_code) VALUES (?,?,?)");
                                preparedStatement.setInt(1,doctor.getId());
                                preparedStatement.setInt(2,patient.getId());
                                preparedStatement.setInt(3,billing.getCode());
                                preparedStatement.executeUpdate();}
                            catch (SQLException e){
                                e.printStackTrace();
                            }

                        }
                        else {
                            System.out.println("You are already registered once");
                            i = 0;
                        }

                    }
                    else if( 2 == i)  { int id;
                        System.out.println("please enter your id");
                        id = scanner.nextInt();
                        preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE id =?");
                        preparedStatement.setInt(1,id);
                        resultSet = preparedStatement.executeQuery();
                        ArrayList<Integer> list = new ArrayList<Integer>();
                        while (resultSet.next())
                        {
                            list.add(resultSet.getInt("id")) ;
                        }
                        if(list.isEmpty())
                        {
                            System.out.println("You aren't registered yet");
                            i = 0;
                        }
                        else { int k ;
                            System.out.println("To get your information press : 1");
                            System.out.println("To register new appointment press : 2");
                            System.out.println("To change your consultation type press : 3");
                            System.out.println("To get your medical record press: 4");
                            System.out.println("To set your medicine press: 5");
                            System.out.println("To set your lab test press: 6 ");
                            System.out.println("please enter choice :");
                            k = scanner.nextInt();
                            switch (k){
                                case 1 :
                                    System.out.println("-----------------------------------------");
                                    preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE id =?");
                                    preparedStatement.setInt(1,id);
                                    resultSet = preparedStatement.executeQuery();
                                    while (resultSet.next()) {
                                        System.out.println("Name :"+ resultSet.getString("name")+" "+resultSet.getString("last_name"));
                                        System.out.println("Consultation type :  "+resultSet.getString("category"));
                                        System.out.println("Your day is : " + (resultSet.getString("appointement_day")));
                                        System.out.println("your time is : "+resultSet.getString("appointement_time"));
                                        System.out.println("Age : "+resultSet.getInt("age"));
                                        System.out.println("Your id :" + (resultSet.getInt("id")));
                                        System.out.println("-----------------------------------------");
                                    }

                                    preparedStatement = connection.prepareStatement("SELECT * FROM billing WHERE patient_id =?");
                                    preparedStatement.setInt(1,id);
                                    resultSet = preparedStatement.executeQuery();
                                    int d_id =0;
                                    while (resultSet.next())
                                    {
                                        System.out.println("Cost : "+resultSet.getDouble("totalAmount"));
                                        System.out.println("Doctor Id : "+resultSet.getInt("doctor_id"));
                                        d_id = resultSet.getInt("doctor_id");
                                    }
                                    preparedStatement = connection.prepareStatement("SELECT * FROM doctor WHERE id =?");
                                    preparedStatement.setInt(1,d_id);
                                    resultSet = preparedStatement.executeQuery();
                                    while (resultSet.next())
                                    {
                                        System.out.println("Doctor name : "+resultSet.getString("name")+" "+resultSet.getString("last_name"));

                                    }
                                    System.out.println("-----------------------------------------");
                                    break;
                                case 2:
                                    Appointment appointment = new Appointment();
                                    try{
                                        preparedStatement = connection.prepareStatement("SELECT doctor_id FROM billing WHERE patient_id =?");
                                        preparedStatement.setInt(1,id);
                                        resultSet = preparedStatement.executeQuery(); }
                                    catch (SQLException e){
                                        e.printStackTrace();
                                    }
                                    int doctor_id = 0;
                                    while (resultSet.next())
                                    {
                                        doctor_id = resultSet.getInt("doctor_id");
                                    }
                                    preparedStatement = connection.prepareStatement("SELECT * FROM doctor WHERE id = ?");
                                    preparedStatement.setInt(1,doctor_id);
                                    resultSet = preparedStatement.executeQuery();
                                    while (resultSet.next())
                                    {
                                        System.out.println(resultSet.getString("name") +" "+resultSet.getString("last_name"));
                                        System.out.println(resultSet.getString("startTime"));
                                        System.out.println(resultSet.getString("endTime"));
                                        System.out.println(resultSet.getString("daysAvailable1"));
                                        System.out.println(resultSet.getString("daysAvailable2"));
                                        System.out.println(resultSet.getString("daysAvailable3"));
                                        System.out.println((resultSet.getInt("id")));
                                        System.out.println("-----------------------------------------");
                                    }
                                    String f ;
                                    System.out.println("please enter your day from these days");
                                    f = scanner.next();

                                    appointment.setDay(f);
                                    System.out.println("please enter your time in this format HH:mm:ss from the period of the doctor's presence ");
                                    f = scanner.next();
                                    appointment.setTime(f);
                                    try{
                                        preparedStatement = connection.prepareStatement("UPDATE patient SET appointement_day = ? WHERE id =?");
                                        preparedStatement.setString(1,appointment.getDay());
                                        preparedStatement.setInt(2,id);
                                        preparedStatement.executeUpdate();}
                                    catch (SQLException e){
                                        e.printStackTrace();
                                    }
                                    try{
                                        preparedStatement = connection.prepareStatement("UPDATE patient SET appointement_time = ? WHERE id =?");
                                        preparedStatement.setString(1,appointment.getTime());
                                        preparedStatement.setInt(2,id);
                                        preparedStatement.executeUpdate();}
                                    catch (SQLException e){
                                        e.printStackTrace();
                                    }
                                    break;
                                case 3 :
                                    System.out.println("please enter your new consultation type");
                                    System.out.println("DENTISTRY");
                                    System.out.println("ENT");
                                    System.out.println("NEUROLOGY");
                                    System.out.println("DERMATOLOGY");
                                    f = scanner.next();
                                    Double Amount=0.0;
                                    if(f.equals("DENTISTRY"))
                                    {
                                        Amount = 300.0;
                                    } else if(f.toUpperCase().equals( "DERMATOLOGY"))//امراض جلدية
                                    {
                                        Amount =250.0;
                                    }
                                    else if(f.toUpperCase().equals( "ENT"))
                                    {
                                        Amount =250.0;
                                    }
                                    else if(f.toUpperCase().equals( "NEUROLOGY"))
                                    {
                                        Amount =350.0;
                                    }
                                    else {
                                        System.out.println("error!! please try again ");
                                    }
                                    try{
                                        preparedStatement = connection.prepareStatement("UPDATE patient SET category = ? WHERE id =?");
                                        preparedStatement.setString(1,f);
                                        preparedStatement.setInt(2,id);
                                        preparedStatement.executeUpdate();}
                                    catch (SQLException e){
                                        e.printStackTrace();
                                    }
                                    try{preparedStatement = connection.prepareStatement("UPDATE billing SET totalAmount = ? WHERE patient_id =?");
                                        preparedStatement.setDouble(1,Amount);
                                        preparedStatement.setInt(2,id);
                                        preparedStatement.executeUpdate();

                                    }
                                    catch (SQLException e){
                                        e.printStackTrace();
                                    }

                                    int doctor_id_ =0 ;
                                    System.out.println("please choose id of one doctor to register with him");
                                    try{
                                        preparedStatement = connection.prepareStatement("SELECT * FROM doctor WHERE category = ?");
                                        preparedStatement.setString(1,f);
                                        resultSet = preparedStatement.executeQuery();
                                        while (resultSet.next())
                                        {
                                            System.out.println(resultSet.getString("name") +" "+ resultSet.getString("last_name"));
                                            System.out.println((resultSet.getInt("id")));
                                        }}
                                    catch (SQLException e){
                                        e.printStackTrace();
                                    }
                                    doctor_id_ = scanner.nextInt();
                                    try{
                                        preparedStatement = connection.prepareStatement("UPDATE billing SET doctor_id = ? WHERE patient_id =?");
                                        preparedStatement.setInt(1,doctor_id_);
                                        preparedStatement.setInt(2,id);
                                        preparedStatement.executeUpdate();

                                    }
                                    catch (SQLException e){
                                        e.printStackTrace();
                                    }

                                    System.out.println("please go to select your appointment from the last list !!!!!!!");
                                    System.out.println("********************************************************");
                                    break;
                                case 4:
                                    System.out.println("-----------------------------------------");
                                    preparedStatement = connection.prepareStatement("SELECT * FROM medical_record WHERE patient_id =?");
                                    preparedStatement.setInt(1,id);
                                    resultSet = preparedStatement.executeQuery();
                                    while (resultSet.next()) {
                                        System.out.println("Diagnosis :  "+resultSet.getString("diagnosis"));
                                        System.out.println("-----------------------------------------");
                                    }
                                    break;
                                case 5:
                                    System.out.println("-----------------------------------------");
                                    preparedStatement = connection.prepareStatement("SELECT * FROM medicine WHERE patient_id =?");
                                    preparedStatement.setInt(1,id);
                                    resultSet = preparedStatement.executeQuery();
                                    while (resultSet.next()) {
                                        System.out.println("Medicines name  :  "+resultSet.getString("name"));
                                        System.out.println("Medicines type  :  "+resultSet.getString("type"));
                                        System.out.println("Medicines price  :  "+resultSet.getString("price"));
                                        System.out.println("-----------------------------------------");
                                    }
                                    break;
                                case 6:
                                    System.out.println("-----------------------------------------");
                                    preparedStatement = connection.prepareStatement("SELECT * FROM labtest WHERE patient_id =?");
                                    preparedStatement.setInt(1,id);
                                    resultSet = preparedStatement.executeQuery();
                                    while (resultSet.next()) {
                                        System.out.println("Lab name  :  "+resultSet.getString("name"));
                                        System.out.println("Type  :  "+resultSet.getString("type"));
                                        System.out.println("-----------------------------------------");
                                    }
                                    break;
                                default:
                                    System.out.println("Error!!");

                            }
                        }
                    }

                }while (!(i==0));
            }
            else if (action== 2) { int i;


                do { String password;
                    System.out.println("please enter password");
                    password = scanner.next();
                    if (password.equals("0000"))
                    {
                        System.out.println("If you are registering a reservation for the first time press 1");
                        System.out.println("If you are already registered :2");
                        System.out.println("For exit press : 0");
                        i = scanner.nextInt();
                        if (i == 1) {
                            Doctor doctor = new Doctor();
                            System.out.println("please enter your first name");
                            doctor.setName(scanner.next());
                            System.out.println("please enter your last name");
                            doctor.setLast_name(scanner.next());
                            System.out.println("please enter your id");
                            doctor.setId(scanner.nextInt());
                            System.out.println("please enter your Phone");
                            doctor.setPhone(scanner.nextInt());
                            System.out.println("please enter your age");
                            doctor.setAge(scanner.nextInt());
                            System.out.println("please enter your Category");
                            System.out.println("DENTISTRY");
                            System.out.println("ENT");
                            System.out.println("NEUROLOGY");
                            System.out.println("DERMATOLOGY");
                            doctor.setCategory(scanner.next());
                            System.out.println("please enter your available days");
                            String[] days = new String[3];
                            for (int index = 0; index < 3; index++) {

                                days[index] = scanner.next();
                            }
                            doctor.setDaysAvailable(days);
                            System.out.println("please enter your salary");
                            doctor.setSalary(scanner.nextFloat());
                            System.out.println("please enter your  start time in this format HH:mm:ss");
                            doctor.setStartTime(scanner.next());
                            System.out.println("please enter your  end time in this format HH:mm:ss");
                            doctor.setEndTime(scanner.next());
                            preparedStatement = connection.prepareStatement("SELECT * FROM doctor WHERE id =?");
                            preparedStatement.setInt(1,doctor.getId());
                            resultSet = preparedStatement.executeQuery();
                            ArrayList<Integer> list = new ArrayList<Integer>();
                            while (resultSet.next())
                            {
                                list.add(resultSet.getInt("id")) ;
                            }

                            if(list.isEmpty())
                            {

                                medicalServices.addDoctor(doctor);
                                try{
                                    preparedStatement = connection.prepareStatement("INSERT INTO doctor VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
                                    preparedStatement.setInt(1,doctor.getId());
                                    preparedStatement.setString(2,doctor.getName());
                                    preparedStatement.setString(3,doctor.getLast_name());
                                    preparedStatement.setFloat(4,doctor.getSalary());
                                    preparedStatement.setString(5,doctor.getCategory());
                                    preparedStatement.setInt(6,doctor.getPhone());
                                    preparedStatement.setInt(7,doctor.getAge());
                                    preparedStatement.setString(8,days[0]);
                                    preparedStatement.setString(9,days[1]);
                                    preparedStatement.setString(10,days[2]);
                                    preparedStatement.setString(11,doctor.getStartTime());
                                    preparedStatement.setString(12,doctor.getEndTime());
                                    preparedStatement.executeUpdate();}
                                catch (SQLException e){
                                    e.printStackTrace();
                                }

                            }
                            else {
                                System.out.println("You are already registered once");
                                i = 0;
                            }



                        }
                        else if (i ==2)
                        {
                            int id;
                            System.out.println("please enter your id");
                            id = scanner.nextInt();
                            preparedStatement = connection.prepareStatement("SELECT * FROM doctor WHERE id =?");
                            preparedStatement.setInt(1,id);
                            resultSet = preparedStatement.executeQuery();
                            ArrayList<Integer> list = new ArrayList<Integer>();
                            while (resultSet.next())
                            {
                                list.add(resultSet.getInt("id")) ;
                            }
                            if(list.isEmpty())
                            {
                                System.out.println("You aren't registered yet");
                                i = 0;
                            }
                            else { int k ;
                                System.out.println("To change your available days : 1");
                                System.out.println("To change your start & end time in this format HH:mm:ss press : 2");
                                System.out.println("To set a medical record for a patient  press: 3");
                                System.out.println("To set a medicine for a patient  press: 4");
                                System.out.println("To set a lab test for a patient  press: 5 ");
                                System.out.println("To get your information press : 6 ");
                                k = scanner.nextInt();
                                switch (k) {
                                    case 1 :
                                        String[] days = new String[3];
                                        for (int index = 0 ;index < 3 ;index++){
                                            System.out.println("please enter day"+(index+1));
                                            days[index]= scanner.next();
                                        }
                                        try{
                                            preparedStatement = connection.prepareStatement("UPDATE doctor SET daysAvailable1 = ? WHERE id =?");
                                            preparedStatement.setString(1,days[0]);
                                            preparedStatement.setInt(2,id);
                                            preparedStatement.executeUpdate();

                                        }
                                        catch (SQLException e){
                                            e.printStackTrace();
                                        }
                                        try{
                                            preparedStatement = connection.prepareStatement("UPDATE doctor SET daysAvailable2 = ? WHERE id =?");
                                            preparedStatement.setString(1,days[1]);
                                            preparedStatement.setInt(2,id);
                                            preparedStatement.executeUpdate();

                                        }
                                        catch (SQLException e){
                                            e.printStackTrace();
                                        }
                                        try{
                                            preparedStatement = connection.prepareStatement("UPDATE doctor SET daysAvailable3 = ? WHERE id =?");
                                            preparedStatement.setString(1,days[2]);
                                            preparedStatement.setInt(2,id);
                                            preparedStatement.executeUpdate();

                                        }
                                        catch (SQLException e){
                                            e.printStackTrace();
                                        }

                                        break;
                                    case 2 :
                                        String f ;
                                        System.out.println("please enter your  start time in this format HH:mm:ss");
                                        f = scanner.next();
                                        try{
                                            preparedStatement = connection.prepareStatement("UPDATE doctor SET startTime = ? WHERE id =?");
                                            preparedStatement.setString(1,f);
                                            preparedStatement.setInt(2,id);
                                            preparedStatement.executeUpdate();

                                        }
                                        catch (SQLException e){
                                            e.printStackTrace();
                                        }
                                        System.out.println("please enter your  end time in this format HH:mm:ss");
                                        f = scanner.next();
                                        try{
                                            preparedStatement = connection.prepareStatement("UPDATE doctor SET endTime = ? WHERE id =?");
                                            preparedStatement.setString(1,f);
                                            preparedStatement.setInt(2,id);
                                            preparedStatement.executeUpdate();

                                        }
                                        catch (SQLException e){
                                            e.printStackTrace();
                                        }
                                        break;
                                    case 3:
                                        ArrayList<Integer> patients_id = new ArrayList<Integer>();
                                        MedicalRecord medicalRecord = new MedicalRecord();
                                        Doctor doctor_1 =new Doctor();
                                        Patient patient_1 =new Patient();
                                        preparedStatement = connection.prepareStatement("SELECT * FROM doctor WHERE id = ?");
                                        preparedStatement.setInt(1,id);
                                        resultSet = preparedStatement.executeQuery();
                                        while (resultSet.next())
                                        {
                                            doctor_1.setName(resultSet.getString("name"));
                                            doctor_1.setLast_name(resultSet.getString("last_name"));
                                            doctor_1.setStartTime(resultSet.getString("startTime"));
                                            doctor_1.setEndTime(resultSet.getString("endTime"));
                                            String[] arrDay = new String[3];
                                            arrDay[0]=resultSet.getString("daysAvailable1");
                                            arrDay[1]=resultSet.getString("daysAvailable2");
                                            arrDay[2]=resultSet.getString("daysAvailable3");
                                            doctor_1.setDaysAvailable(arrDay);
                                            doctor_1.setId(resultSet.getInt("id"));
                                            doctor_1.setPhone(resultSet.getInt("phone"));
                                            doctor_1.setSalary(resultSet.getFloat("salary"));
                                            doctor_1.setCategory(resultSet.getString("category"));

                                        }
                                        medicalRecord.setDoctor(doctor_1);
                                        try{
                                            preparedStatement = connection.prepareStatement("SELECT patient_id FROM billing WHERE doctor_id = ?");
                                            preparedStatement.setInt(1,id);
                                            resultSet = preparedStatement.executeQuery();
                                            while (resultSet.next())
                                            {

                                                patients_id.add(resultSet.getInt("patient_id"));
                                            }
                                        }
                                        catch (SQLException e){
                                            e.printStackTrace();
                                        }
                                        for(int index_arr = 0 ; index_arr < patients_id.size() ;index_arr++)
                                        {
                                            preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE id = ?");
                                            preparedStatement.setInt(1, patients_id.get(index_arr));
                                            resultSet = preparedStatement.executeQuery();
                                            while (resultSet.next()) {
                                                patient_1.setName(resultSet.getString("name"));
                                                patient_1.setLast_name(resultSet.getString("last_name"));
                                                patient_1.setAge(resultSet.getInt("age"));
                                                Appointment appointment1 = new Appointment();
                                                appointment1.setDay(resultSet.getString("appointement_day"));
                                                appointment1.setTime(resultSet.getString("appointement_time"));
                                                patient_1.setAppointment(appointment1);
                                                patient_1.setPhone(resultSet.getInt("phone"));
                                                patient_1.setId(resultSet.getInt("id"));
                                                patient_1.setCategory(resultSet.getString("category"));
                                            }/**/

                                            medicalRecord.getPatients().add(patient_1);

                                            StringBuilder longText = new StringBuilder();
                                            String line;
                                            System.out.println(" please enter diagnosis of "+patient_1.getName()+" which his id :"+patient_1.getId());
                                            System.out.println("please end your diagnosis with word (end) in the next line ");
                                            while (!(line = scanner.nextLine()).equalsIgnoreCase("end"))
                                            {
                                                longText.append(line).append("\n");
                                            }
                                            medicalRecord.setDiagnosis(longText);
                                            String textToInsert = longText.toString();
                                            try{
                                                preparedStatement = connection.prepareStatement("INSERT INTO medical_record VALUES (?,?,?)");
                                                preparedStatement.setString(1,textToInsert);
                                                preparedStatement.setInt(2,doctor_1.getId());
                                                preparedStatement.setInt(3,patient_1.getId());
                                                preparedStatement.executeUpdate();
                                            }
                                            catch (SQLException e){
                                                e.printStackTrace();
                                            }

                                        }
                                        break;
                                    case 4:
                                        ArrayList<Integer> patients_id1 = new ArrayList<Integer>();
                                        Medicine medicine = new Medicine();
                                        Patient  patient_2 = new Patient();
                                        try{
                                            preparedStatement = connection.prepareStatement("SELECT patient_id FROM billing WHERE doctor_id = ?");
                                            preparedStatement.setInt(1,id);
                                            resultSet = preparedStatement.executeQuery();
                                            while (resultSet.next())
                                            {

                                                patients_id1.add(resultSet.getInt("patient_id"));
                                            }
                                        }
                                        catch (SQLException e){
                                            e.printStackTrace();
                                        }
                                        for(int index_arr = 0 ; index_arr < patients_id1.size() ;index_arr++)
                                        {
                                            preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE id = ?");
                                            preparedStatement.setInt(1, patients_id1.get(index_arr));
                                            resultSet = preparedStatement.executeQuery();
                                            while (resultSet.next())
                                            {
                                                patient_2.setName(resultSet.getString("name"));
                                                patient_2.setLast_name(resultSet.getString("last_name"));
                                                patient_2.setAge(resultSet.getInt("age"));
                                                Appointment appointment1 = new Appointment();
                                                appointment1.setDay(resultSet.getString("appointement_day"));
                                                appointment1.setTime(resultSet.getString("appointement_time"));
                                                patient_2.setAppointment(appointment1);
                                                patient_2.setPhone(resultSet.getInt("phone"));
                                                patient_2.setId(resultSet.getInt("id"));
                                                patient_2.setCategory(resultSet.getString("category"));
                                            }
                                            medicine.getPatients().add(patient_2);
                                            StringBuilder longText = new StringBuilder();
                                            String line;
                                            System.out.println(" please enter medicine for "+patient_2.getName()+" which his id :"+patient_2.getId());
                                            System.out.println("please end your medicine with word (end) in the next line ");
                                            while (!(line = scanner.nextLine()).equalsIgnoreCase("end"))
                                            {
                                                longText.append(line).append("\n");
                                            }
                                            String textToInsert = longText.toString();
                                            medicine.setName(textToInsert);
                                            System.out.println("please enter the type of medicines for your patient ");
                                            System.out.println("TABLET,\n" +
                                                    "INJECTION,\n" +
                                                    "SYRUP");
                                            System.out.println("please end your type of medicines with word (end) in the next line ");
                                            while (!(line = scanner.nextLine()).equalsIgnoreCase("end"))
                                            {
                                                longText.append(line).append("\n");
                                            }
                                             textToInsert = longText.toString();
                                            medicine.setType(textToInsert);
                                            System.out.println("please enter the price of medicine");
                                            medicine.setPrice(scanner.nextFloat());
                                            try{
                                                preparedStatement = connection.prepareStatement("INSERT INTO medicine VALUES (?,?,?,?)");
                                                preparedStatement.setString(1,medicine.getName());
                                                preparedStatement.setString(2,medicine.getType());
                                                preparedStatement.setFloat(3,medicine.getPrice());
                                                preparedStatement.setInt(4,patient_2.getId());
                                                preparedStatement.executeUpdate();
                                            }
                                            catch (SQLException e){
                                                e.printStackTrace();
                                            }

                                        }


                                        break;
                                    case 5:
                                        ArrayList<Integer> patients_id2 = new ArrayList<Integer>();
                                        LabTest labTest = new LabTest();
                                        Patient  patient_3 = new Patient();
                                        try{
                                            preparedStatement = connection.prepareStatement("SELECT patient_id FROM billing WHERE doctor_id = ?");
                                            preparedStatement.setInt(1,id);
                                            resultSet = preparedStatement.executeQuery();
                                            while (resultSet.next())
                                            {

                                                patients_id2.add(resultSet.getInt("patient_id"));
                                            }
                                        }
                                        catch (SQLException e){
                                            e.printStackTrace();
                                        }
                                        for(int index_arr = 0 ; index_arr < patients_id2.size() ;index_arr++)
                                        {
                                            preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE id = ?");
                                            preparedStatement.setInt(1, patients_id2.get(index_arr));
                                            resultSet = preparedStatement.executeQuery();
                                            while (resultSet.next())
                                            {
                                                patient_3.setName(resultSet.getString("name"));
                                                patient_3.setLast_name(resultSet.getString("last_name"));
                                                patient_3.setAge(resultSet.getInt("age"));
                                                Appointment appointment1 = new Appointment();
                                                appointment1.setDay(resultSet.getString("appointement_day"));
                                                appointment1.setTime(resultSet.getString("appointement_time"));
                                                patient_3.setAppointment(appointment1);
                                                patient_3.setPhone(resultSet.getInt("phone"));
                                                patient_3.setId(resultSet.getInt("id"));
                                                patient_3.setCategory(resultSet.getString("category"));
                                            }
                                            labTest.getPatients().add(patient_3);
                                            StringBuilder longText = new StringBuilder();
                                            String line;
                                            System.out.println(" please enter the name of lab for "+patient_3.getName()+" which his id :"+patient_3.getId());
                                            System.out.println("please end your name of lab with word (end) in the next line ");
                                            while (!(line = scanner.nextLine()).equalsIgnoreCase("end"))
                                            {
                                                longText.append(line).append("\n");
                                            }
                                            String textToInsert = longText.toString();
                                            labTest.setName(textToInsert);
                                            System.out.println("please enter the type of test for your patient ");
                                            System.out.println("please end your type of test with word (end) in the next line ");
                                            while (!(line = scanner.nextLine()).equalsIgnoreCase("end"))
                                            {
                                                longText.append(line).append("\n");
                                            }
                                            textToInsert = longText.toString();
                                            labTest.setType(textToInsert);
                                            try{
                                                preparedStatement = connection.prepareStatement("INSERT INTO labTest VALUES (?,?,?)");
                                                preparedStatement.setString(1,labTest.getName());
                                                preparedStatement.setString(2,labTest.getType());
                                                preparedStatement.setInt(3,patient_3.getId());
                                                preparedStatement.executeUpdate();
                                            }
                                            catch (SQLException e){
                                                e.printStackTrace();
                                            }

                                        }


                                        break;
                                    case 6 :
                                        System.out.println("-----------------------------------------");
                                        preparedStatement = connection.prepareStatement("SELECT * FROM doctor WHERE id =?");
                                        preparedStatement.setInt(1,id);
                                        resultSet = preparedStatement.executeQuery();
                                        while (resultSet.next()) {
                                            System.out.println("Name :"+ resultSet.getString("name")+" "+resultSet.getString("last_name"));
                                            System.out.println("Category :  "+resultSet.getString("category"));
                                            System.out.println("Your days : " + (resultSet.getString("daysAvailable1")+" "+resultSet.getString("daysAvailable2"))+" "+resultSet.getString("daysAvailable3"));
                                            System.out.println("your start time is : "+resultSet.getString("startTime"));
                                            System.out.println("your end time is : "+resultSet.getString("endTime"));
                                            System.out.println("Age : "+resultSet.getInt("age"));
                                            System.out.println("Your id :" + (resultSet.getInt("id")));
                                        }
                                        break;
                                    default:
                                        System.out.println("Error!!");

                                }
                            }

                        }
                    }
                    else {
                        System.out.println("Invalid password !!");
                        i =0;
                    }
                }while (!(i==0));
            }
        }while (!(action==0));
    }

}
