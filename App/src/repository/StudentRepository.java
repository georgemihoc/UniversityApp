package repository;

import validators.Entity;
import validators.Validator;

public class StudentRepository<E> extends InMemoryRepository {
    public StudentRepository(Validator validator) {
        super(validator);
    }
}
