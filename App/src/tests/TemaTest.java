package tests;

import org.junit.jupiter.api.Test;
import validators.Tema;

import static org.junit.jupiter.api.Assertions.*;

class TemaTest {
    private Tema tema = new Tema("abc", 4, 6);
    @Test
    void getDescriere() {
        assertEquals(tema.getDescriere(),"abc");
    }

    @Test
    void setDescriere() {
        tema.setDescriere("bcd");
        assertEquals(tema.getDescriere(),"bcd");
    }

    @Test
    void getStartWeek() {
        assertEquals(tema.getStartWeek(),4);
    }

    @Test
    void setStartWeek() {
        tema.setStartWeek(5);
        assertEquals(tema.getStartWeek(),5);

    }

    @Test
    void getDeadlineWeek() {
        assertEquals(tema.getDeadlineWeek(),6);
    }

    @Test
    void setDeadlineWeek() {
        tema.setDeadlineWeek(7);
        assertEquals(tema.getDeadlineWeek(),7);
    }

    @Test
    void toString1() {
        assertEquals(tema.toString(),"Tema{id=" + tema.getId()+
                ",descriere='" + tema.getDescriere() + '\'' +
                ", startWeek=" + tema.getStartWeek() +
                ", deadlineWeek=" + tema.getDeadlineWeek() +
                '}');
    }
}