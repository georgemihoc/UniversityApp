package validators;

public class Student extends Entity<Long> implements Comparable<Student> {
    private String nume;
    private int grupa;
    private String email;
    private String cadruDidactic;

    public Student( String nume, int grupa, String email, String cadruDidactic) {
        this.nume = nume;
        this.grupa = grupa;
        this.email = email;
        this.cadruDidactic = cadruDidactic;
    }


    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCadruDidactic() {
        return cadruDidactic;
    }

    public void setCadruDidactic(String cadruDidactic) {
        this.cadruDidactic = cadruDidactic;
    }

    @Override
    public String toString() {
        return "Student{id=" + getId() +
                ", nume='" + nume + '\'' +
                ", grupa=" + grupa +
                ", email='" + email + '\'' +
                ", cadruDidactic='" + cadruDidactic + '\'' +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        return 0;
    }
}
