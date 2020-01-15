package repository;

import validators.Validator;

public class TemaRepository<E> extends InMemoryRepository {
    public TemaRepository(Validator validator) {
        super(validator);
    }
}
