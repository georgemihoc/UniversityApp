package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InMemoryRepository;
import repository.StudentRepository;
import validators.Student;
import validators.ValidationException;
import validators.Validator;
import validators.ValidatorStudent;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoryTest<ID,E> {
    private Map<ID,E> entities;
    private Validator<E> validator;
    InMemoryRepository repositoryTest;
    private Student s1 = new Student("abc",2,"abc","abc");
    private Student s2 = new Student("aaaa",3,"aaaa","aaa");

    @BeforeEach
    void setUp() {
        this.entities = new HashMap<>();
        this.validator = (Validator<E>) new ValidatorStudent();
        repositoryTest = new StudentRepository(validator);
    }

    @Test
    void findOne() throws ValidationException {
        setUp();
        save();
        assertEquals(repositoryTest.findOne(5L),null);
        assertEquals(repositoryTest.findOne(1L),s1);

    }

    @Test
    void findAll() throws ValidationException {
        setUp();
        save();
        int size = 0;
        for(Object x:repositoryTest.findAll()) {
            if (++size==1)
                assertEquals(x, s1);
            else if(size == 2)
                assertEquals(x,s2);
        }
    }

    @Test
    void save() throws ValidationException {
        setUp();
        s1.setId(repositoryTest.findID());
        repositoryTest.save(s1);
        s2.setId(repositoryTest.findID());
        repositoryTest.save(s2);
    }

    @Test
    void delete() throws ValidationException {
        setUp();
        save();
        repositoryTest.delete(1L);
        assertEquals(repositoryTest.findOne(1L),null);
    }

    @Test
    void update() throws ValidationException {
        setUp();
        save();
        Student up = new Student("up",22,"up","up");
        up.setId(1L);
        repositoryTest.update(up);
        assertEquals(repositoryTest.findOne(1L),up);
    }

    @Test
    void findID() throws ValidationException {
        setUp();
        save();
        assertEquals(repositoryTest.findID(),3L);

    }
}