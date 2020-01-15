package tests;

import validators.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student = new Student("Mihai",224,"abc@gmail.com", "Suciu Dan");

    @org.junit.jupiter.api.Test
    void getNume() {
        assertEquals(student.getNume(),"Mihai");
    }

    @org.junit.jupiter.api.Test
    void setNume() {
        student.setNume("Alex");
        assertEquals(student.getNume(),"Alex");

    }

    @org.junit.jupiter.api.Test
    void getGrupa() {
        assertEquals(student.getGrupa(),224);
    }

    @org.junit.jupiter.api.Test
    void setGrupa() {
        student.setGrupa(223);
        assertEquals(student.getGrupa(),223);
    }

    @org.junit.jupiter.api.Test
    void getEmail() {
        assertEquals(student.getEmail(),"abc@gmail.com");
    }

    @org.junit.jupiter.api.Test
    void setEmail() {
        student.setEmail("dfg@gmail.com");
        assertEquals(student.getEmail(),"dfg@gmail.com");
    }

    @org.junit.jupiter.api.Test
    void getCadruDidactic() {
        assertEquals(student.getCadruDidactic(),"Suciu Dan");
    }

    @org.junit.jupiter.api.Test
    void setCadruDidactic() {
        student.setCadruDidactic("Darius Bufnea");
        assertEquals(student.getCadruDidactic(),"Darius Bufnea");
    }

    @org.junit.jupiter.api.Test
    void toString1() {
        assertEquals(student.toString(),"Student{id=" + student.getId() +
                ", nume='" + student.getNume() + '\'' +
                ", grupa=" + student.getGrupa() +
                ", email='" + student.getEmail() + '\'' +
                ", cadruDidactic='" + student.getCadruDidactic() + '\'' +
                '}');
    }
}