package validators;

public class Tema extends Entity<Long> implements Comparable<Tema> {
    private String descriere;
    private int startWeek;
    private int deadlineWeek;

    public Tema(String descriere, int startWeek, int deadlineWeek) {
        this.descriere = descriere;
        this.startWeek = startWeek;
        this.deadlineWeek = deadlineWeek;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getDeadlineWeek() {
        return deadlineWeek;
    }

    public void setDeadlineWeek(int deadlineWeek) {
        this.deadlineWeek = deadlineWeek;
    }

//    @Override
//    public String toString() {
//        return "Tema{id=" + getId()+
//                ",descriere='" + descriere + '\'' +
//                ", startWeek=" + startWeek +
//                ", deadlineWeek=" + deadlineWeek +
//                '}';
//    }
    @Override
    public String toString() {
        return descriere;
    }
    @Override
    public int compareTo(Tema o) {
        return 0;
    }
}
