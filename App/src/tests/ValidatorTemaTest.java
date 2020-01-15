package tests;

import org.junit.jupiter.api.Test;
import repository.InMemoryRepository;
import validators.Tema;
import validators.ValidationException;
import validators.ValidatorTema;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTemaTest {

    private ValidatorTema val = new ValidatorTema();
    private InMemoryRepository<Long, Tema> repo = new InMemoryRepository<>(val);
    private Tema tema = new Tema("",3  ,1);
    private Tema tema2 = new Tema("abc",Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date()))-40+1  ,Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date()))-40+1+1);


    @Test
    void validate() {
        int currentWeek =Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date()))-40+1;
        if(currentWeek==13)
            currentWeek=100;
        if(currentWeek==14)
            currentWeek=101;
        if(currentWeek>14)
            currentWeek-=2;


        tema.setId(1L);
        tema2.setId(1L);
        try{
            repo.save(tema);
            repo.save(tema2);
            assertEquals(repo.findOne(1L).getStartWeek(),currentWeek);

        } catch (ValidationException e) {
            assertEquals(e.getMessage(),"entity not valid");
        }


    }
}