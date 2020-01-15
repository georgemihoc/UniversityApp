package tests;

import org.junit.jupiter.api.Test;
import repository.InMemoryRepository;
import validators.Student;
import validators.ValidationException;
import validators.ValidatorStudent;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorStudentTest {
    private ValidatorStudent val = new ValidatorStudent();
    private InMemoryRepository<Long, Student> repo = new InMemoryRepository<>(val);
    private Student student = new Student("",224,"abc","");
    private Student student2 = new Student("aaa",224,"aaa","aaa");
    @Test
    void validate() {
        student.setId(1L);
        student2.setId(2L);
        try{
            repo.save(student);
            repo.save(student2);

        } catch (ValidationException e) {
            assertEquals(e.getMessage(),"entity not valid");
        }
        //assertEquals();
    }
}