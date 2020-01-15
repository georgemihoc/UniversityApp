package com.company;

import configuration.ApplicationContext;
import repository.*;
import service.Service;
import service.ServiceNota;
import validators.*;

import java.util.List;

public class Main {
    private static void runConsole(String[] args){

//        String fileStudents = ApplicationContext.getPROPERTIES().getProperty("data.students");
//        String fileTeme = ApplicationContext.getPROPERTIES().getProperty("data.teme");
//        String fileNote = ApplicationContext.getPROPERTIES().getProperty("data.note");
//
//        Validator validator = new ValidatorStudent();
//        StudentFileRepository repository = new StudentFileRepository(validator,fileStudents);
//
//        Validator validator1 = new ValidatorTema();
//        TemaFileRepository repository1 = new TemaFileRepository(validator1,fileTeme);
//
//
//        Validator validator2 = new ValidatorNota();
//        NotaFileRepository repository2 = new NotaFileRepository(validator2, fileNote);
//
//        Service service = new Service(validator,repository,validator1,repository1,validator2,repository2);
//        service.studentFileGeneral();
//        ServiceNota serviceNota = new ServiceNota(repository2,validator2);
//
////        CONSOLE RUN
//        UI ui = new UI(service,serviceNota);
//        ui.run(args);
    }
    private static void runGUI(String[] args){
        ///GUI
          MainApp mainApp = new MainApp();
          mainApp.main(args);
    }
    public static void main(String[] args)  {
//        runConsole(args);
//        StudentFileRepository repo = new StudentFileRepository()


        runGUI(args);
    }
}