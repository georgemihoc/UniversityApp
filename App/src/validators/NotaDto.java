package validators;

public class NotaDto extends Entity<Pair> {
    private String studentName;
    private String temaName;
    private int date;
    private float nota;

    public NotaDto(String studentName, String temaName,int date, float nota){
        this.studentName = studentName;
        this.temaName = temaName;
        this.date = date;
        this.nota = nota;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTemaName() {
        return temaName;
    }

    public void setTemaName(String temaName) {
        this.temaName = temaName;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }


    @Override
    public String toString() {
        return "NotaDto{" +
                "studentName='" + studentName + '\'' +
                ", temaName='" + temaName + '\'' +
                ", nota=" + nota + '}';
    }
}
