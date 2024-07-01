import java.time.DayOfWeek;
import java.time.LocalTime;

public class Appointment {
    private String day ;
    private String time ;

    public Appointment() {
    }

    public Appointment(String day, Patient patient, Doctor doctor, String time) {
        this.day = day;
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "day=" + day +
                ", time=" + time +
                '}';
    }
}
