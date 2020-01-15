package validators;

public class ValidatorStudent implements Validator<Student> {

    @Override
    public void validate(Student entity) throws ValidationException {
        String exception = "";
        if(entity.getNume() == null)
            exception += "Nume invalid";
        if(entity.getGrupa()<0 || entity.getGrupa()>1000)
            exception += "Grupa invalida";
        if(entity.getEmail().equals(""))
            exception += "Email invalid";
        if(entity.getCadruDidactic().equals(""))
            throw new ValidationException("Cadru didactic invalid");
        if(!exception.equals(""))
            throw new ValidationException("Student invalid");
    }
}
