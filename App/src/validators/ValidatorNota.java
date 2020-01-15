package validators;

public class ValidatorNota implements Validator<Nota> {
    @Override
    public void validate(Nota entity) throws ValidationException {
        if(entity.getProfesor() == "")
            throw new ValidationException("Nota invalida");
        if(entity.getNota()>10.0 || entity.getNota()<1.0)
            throw new ValidationException("Nota invalida");

    }
}
