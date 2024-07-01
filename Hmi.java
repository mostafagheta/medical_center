import java.util.ArrayList;
import java.util.Scanner;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class Hmi {
    /*public static void  start()
    {
        Scanner scanner = new Scanner(System.in);
        MedicalCenter medicalCenter = new MedicalCenter();
        MedicalServices medicalServices = new MedicalServices(medicalCenter);
        System.out.println(medicalCenter.getName());
        System.out.println(medicalCenter.getAddress());
        int action ;
        System.out.println("Welcome to our medical center");
        do{
            System.out.println("For patient services press : 1");
            System.out.println("For doctor services press : 2");
            System.out.println("For exit press : 0");
            action = scanner.nextInt();
           if(1 ==action)
           { int i;
              do {
                  System.out.println("If you are registering a reservation for the first time press 1");
                  System.out.println("If you are already registered :2");
                  System.out.println("For exit press : 0");
                  i = scanner.nextInt();
                  if(i == 1){
                      Patient patient = new Patient();
                      Billing billing =  new Billing();
                      Doctor doctor = new Doctor();
                      System.out.println("please enter your name");
                      patient.setName(scanner.next());
                      System.out.println("please enter your id");
                      patient.setId(scanner.nextInt());
                      System.out.println("please enter your Phone");
                      patient.setPhone(scanner.nextInt());
                      System.out.println("please enter your age");
                      patient.setAge(scanner.nextInt());
                      System.out.println("please enter your consultation type");
                      patient.setCategory(scanner.next());
                      Appointment appointment = new Appointment();
                      DayOfWeek dayOfWeek ;
                      String f ;
                      System.out.println("for appointment please enter your day");
                      f = scanner.next();
                      dayOfWeek = DayOfWeek.valueOf(f.toUpperCase());
                      appointment.setDay(dayOfWeek);
                      System.out.println("please enter your time in this format HH:mm:ss");
                      f = scanner.next();
                      LocalTime localTime;
                      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                      localTime = LocalTime.parse(f, formatter);
                      appointment.setTime(localTime);
                      patient.setAppointment(appointment);
                      System.out.println("please choose name of one doctor to register with him");
                      System.out.println(medicalServices.getDoctorsByCategory(patient.getCategory()));
                      f = scanner.next();
                      doctor = medicalServices.getDoctorByName(f);
                      billing.setDoctor(doctor);
                     if(doctor.getCategory().toUpperCase().equals("DENTISTRY"))
                     {
                         billing.setTotalAmount(300);
                     } else if(doctor.getCategory().toUpperCase().equals( "DERMATOLOGY"))//امراض جلدية
                     {
                         billing.setTotalAmount(250);
                     }
                     else if(doctor.getCategory().toUpperCase().equals( "ENT"))
                     {
                         billing.setTotalAmount(250);
                     }
                     else if(doctor.getCategory().toUpperCase().equals( "NEUROLOGY"))
                     {
                         billing.setTotalAmount(350);
                     }
                     else {
                         System.out.println("error!! please try again ");
                     }
                     patient.setBilling(billing);
                     billing.setPatient(patient);

                      if(null==medicalServices.getPatientByID(patient.getId()))
                      {
                          medicalServices.addPatient(patient);
                          medicalServices.addBillings(billing);
                      }
                      else {
                          System.out.println("You are already registered once");
                          i = 0;
                      }

                  }
                  else{ int id;
                      System.out.println("please enter your id");
                      id = scanner.nextInt();
                      if(null==medicalServices.getPatientByID(id))
                      {
                          System.out.println("You aren't registered yet");
                          i = 0;
                      }
                      else { int k ;
                          System.out.println("To get your information press : 1");
                          System.out.println("To register new appointment press : 2");
                          System.out.println("To change your consultation type press : 3");
                          k = scanner.nextInt();
                          Patient c;
                          c =medicalServices.getPatientByID(id);
                          switch (k){
                              case 1 :
                                System.out.println(c.toString());
                                  break;
                              case 2:
                                  Appointment appointment = new Appointment();
                                  DayOfWeek dayOfWeek ;
                                  String f ;
                                  System.out.println("please enter your day");
                                  f = scanner.next();
                                  dayOfWeek = DayOfWeek.valueOf(f.toUpperCase());
                                  appointment.setDay(dayOfWeek);
                                  System.out.println("please enter your time in this format HH:mm:ss");
                                  f = scanner.next();
                                  LocalTime localTime;
                                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                                  localTime = LocalTime.parse(f, formatter);
                                  appointment.setTime(localTime);
                                  c.setAppointment(appointment);
                                  break;
                              case 3 :
                                  System.out.println("please enter your new consultation type");
                                  c.setCategory(scanner.next());
                                  break;
                              default:
                                  System.out.println("Error!!");

                          }
                      }
                  }

              }while (!(i==0));
           }
           else if (action== 2) { int i;


               do { String password ;
                   System.out.println("please enter password");
                    password = scanner.next();
                    if(password.equals("0000"))
                    {
                   System.out.println("If you are registering a reservation for the first time press 1");
                   System.out.println("If you are already registered :2");
                   System.out.println("For exit press : 0");
                   i = scanner.nextInt();
                    if( i ==1)
                    {
                        Doctor doctor = new Doctor();
                        System.out.println("please enter your name");
                        doctor.setName(scanner.next());
                        System.out.println("please enter your id");
                        doctor.setId(scanner.nextInt());
                        System.out.println("please enter your Phone");
                        doctor.setPhone(scanner.nextInt());
                        System.out.println("please enter your age");
                        doctor.setAge(scanner.nextInt());
                        System.out.println("please enter your Category");
                        doctor.setCategory(scanner.next());
                        System.out.println("please enter your available days");
                        DayOfWeek[] days = new DayOfWeek[3];
                        for (int index = 0 ;index < 3 ;index++){
                            DayOfWeek dayOfWeek ;

                            String f ;
                            f = scanner.next();
                            dayOfWeek = DayOfWeek.valueOf(f.toUpperCase());
                            days[index]= dayOfWeek;
                        }
                        doctor.setDaysAvailable(days);
                        System.out.println("please enter your salary");
                        doctor.setSalary(scanner.nextFloat());
                        String f ;
                        System.out.println("please enter your  start time in this format HH:mm:ss");
                        f = scanner.next();
                        LocalTime localTime;
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        localTime = LocalTime.parse(f, formatter);
                        doctor.setStartTime(localTime);
                        System.out.println("please enter your  end time in this format HH:mm:ss");
                        f = scanner.next();
                         formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        localTime = LocalTime.parse(f, formatter);
                        doctor.setEndTime(localTime);
                        if(null==medicalServices.getPatientByID(doctor.getId()))
                        {
                            medicalServices.addDoctor(doctor);
                        }
                        else {
                            System.out.println("You are already registered once");
                            i = 0;
                        }



                    } else if (i ==2) {
                        int id;
                        System.out.println("please enter your id");
                        id = scanner.nextInt();
                        if(null==medicalServices.getPatientByID(id))
                        {
                            System.out.println("You aren't registered yet");
                            i = 0;
                        }
                        else { int k ;
                            System.out.println("To change your available days : 1");
                            System.out.println("To change your start & end time in this format HH:mm:ss press : 2");
                            System.out.println("To set a medical record for a patient  press: 3");
                            System.out.println("To set a medicine for a patient  press: 4");
                            k = scanner.nextInt();
                            Doctor doctor;
                            doctor =medicalServices.getDoctorByID(id);
                            switch (k) {
                                case 1 :
                                    DayOfWeek[] days = new DayOfWeek[3];
                                    for (int index = 0 ;index < 3 ;index++){
                                        DayOfWeek dayOfWeek ;

                                        String f ;
                                        f = scanner.next();
                                        dayOfWeek = DayOfWeek.valueOf(f.toUpperCase());
                                        days[index]= dayOfWeek;
                                    }
                                    doctor.setDaysAvailable(days);
                                    break;
                                case 2 :
                                    String f ;
                                    System.out.println("please enter your  start time in this format HH:mm:ss");
                                    f = scanner.next();
                                    LocalTime localTime;
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                                    localTime = LocalTime.parse(f, formatter);
                                    doctor.setStartTime(localTime);
                                    System.out.println("please enter your  end time in this format HH:mm:ss");
                                    f = scanner.next();
                                    formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                                    localTime = LocalTime.parse(f, formatter);
                                    doctor.setEndTime(localTime);
                                    break;
                                case 3:
                                    ArrayList<Patient> arr = new ArrayList<Patient>();
                                    MedicalRecord medicalRecord = new MedicalRecord();
                                    arr = medicalServices.getPatientsByDoctorId(id);
                                    for(Patient patient1 :arr){
                                        System.out.println("please enter the diagnosis for your patient");
                                        medicalRecord.setDiagnosis(scanner.next());
                                        patient1.setMedicalRecords(medicalRecord);
                                    }
                                    break;
                                case 4:
                                    ArrayList<Patient> arr2 = new ArrayList<Patient>();
                                    Medicine medicine = new Medicine();
                                    arr2 = medicalServices.getPatientsByDoctorId(id);
                                    for(Patient patient1 :arr2){
                                        System.out.println("please enter the name of medicine for your patient");
                                        medicine.setName(scanner.next());
                                        System.out.println("please enter the type of medicine for your patient ");
                                        System.out.println("TABLET,\n" +
                                                "    INJECTION,\n" +
                                                "    SYRUP");
                                        medicine.setType(scanner.next());
                                        System.out.println("please enter the price of medicine");
                                        medicine.setPrice(scanner.nextFloat());
                                        patient1.setMedicine(medicine);

                                    }
                                    break;

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

*/}
