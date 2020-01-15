package validators;

public class Raport1 extends Entity<Pair> {
    private String studentName;
    private float nota;

    public Raport1(String studentName, float nota){
        this.studentName = studentName;

        this.nota = nota;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }


    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }


    @Override
    public String toString() {
        return "Raport1{" +
                "studentName='" + studentName + '\'' +
                ", nota=" + nota + '}';
    }
}
