import java.util.ArrayList;

public class Medicine {
        String name;
        String type;
        float price ;
        private ArrayList<Patient> patients;
        public Medicine(String name, String type, float price) {
            this.name = name;
            this.type = type;
            this.price = price;
            patients =new ArrayList<Patient>();
        }

        public Medicine() {
            patients =new ArrayList<Patient>();
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

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }

    @Override
        public String toString() {
            return "Medicine{" +
                    "name='" + name + '\'' +
                    ", type=" + type +
                    ", price=" + price +
                    '}';
        }
    }

