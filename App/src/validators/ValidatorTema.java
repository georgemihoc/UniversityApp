package validators;

import java.text.SimpleDateFormat;

public class ValidatorTema implements Validator<Tema> {

    @Override
    public void validate(Tema entity) throws ValidationException {
        int currentWeek =Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date()))-40+1;
        if(currentWeek==13)
            currentWeek=100;
        if(currentWeek==14)
            currentWeek=101;
        if(currentWeek>14)
            currentWeek-=2;
        //System.out.println(currentWeek);
        if(entity.getStartWeek()>14 || entity.getStartWeek()<1 || entity.getDeadlineWeek()>14 || entity.getDeadlineWeek()<1)
            throw new ValidationException("Tema invalida");
        if(entity.getDeadlineWeek()<entity.getStartWeek())
            throw new ValidationException("Tema invalida");
//        if(entity.getDeadlineWeek()<currentWeek)
//            throw new ValidationException("Tema invalida");
        if(entity.getDescriere().equals(""))
            throw new ValidationException("Tema invalida");
    }
}