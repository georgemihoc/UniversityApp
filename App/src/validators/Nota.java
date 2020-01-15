package validators;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Nota extends Entity<Pair>  implements Comparable<Nota> {
    private Pair p;
    private int date;
    private Float nota;
    private String profesor;
    private String feedback;


    public Nota(Long idStudent, Long idTema, int date, Float nota, String profesor) {
        this.p = new Pair(idStudent,idTema);
        //this.idStudent = idStudent;
        //this.idTema = idTema;
        //this.setId(p);
        this.date = date;
        this.nota = nota;
        this.profesor = profesor;
    }

    public Nota(Long idStudent, Long idTema, int date, Float nota, String profesor, String feedback) {
        this.p = new Pair(idStudent,idTema);
        //this.idStudent = idStudent;
        //this.idTema = idTema;
        //this.setId(p);
        this.date = date;
        this.nota = nota;
        this.profesor = profesor;
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Long getIdStudent() {
        return p.getFirst();
    }

    public void setIdStudent(Long idStudent) {
        Pair p = new Pair(idStudent,this.getId().getSecond());
        this.setId(p);
    }

    public Long getIdTema() {
        return p.getSecond();
    }

    public void setIdTema(Long idTema) {
        Pair p = new Pair(this.getId().getFirst(),idTema);
        this.setId(p);
    }

    public Float getNota() {
        return nota;
    }

    public void setNota(Float nota) {
        this.nota = nota;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        return "Nota{idStudent=" + p.getFirst() +
                ", idTema=" + p.getSecond() +
                ", week='" + date + '\'' +
                ", nota=" + nota +
                ", profesor='" + profesor + '\'' +
                '}';
    }

    @Override
    public int compareTo(Nota o) {
        return 0;
    }
}
