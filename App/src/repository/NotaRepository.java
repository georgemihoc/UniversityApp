package repository;

import validators.Validator;

public class NotaRepository<E> extends InMemoryRepository {
    public NotaRepository(Validator validator) {
        super(validator);
    }
}
