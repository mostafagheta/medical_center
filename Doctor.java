import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

public class Doctor extends  Person {
    private  float salary ;
    /**/
    private  String category ;

    private String[] daysAvailable =new String[3] ;

    private String  startTime;

    private String  endTime;

    public Doctor() {

    }

    public Doctor(int id, String name, int phone, int age) {
        super(id, name, phone, age);
    }

    public Doctor(int id, String name, int phone,int age , float salary, String category, String[] daysAvailable, String startTime, String endTime) {
        super(id, name, phone, age);
        this.salary = salary;
        this.category = category;
        this.daysAvailable = daysAvailable;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(String[] daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

