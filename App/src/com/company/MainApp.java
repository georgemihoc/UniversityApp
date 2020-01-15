package com.company;

import configuration.ApplicationContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.util.DigestUtils;
import repository.*;
import service.*;
import validators.*;


import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainApp extends Application {

    Validator validator ;
    StudentFileRepository repository;

    Validator validator1;
    TemaFileRepository repository1;


    Validator validator2 ;
    InMemoryRepository<Long, Tema> repository2 ;

    Service service;
    ServiceNota serviceNota;
//
//    public MainApp(Service service) {
//        this.service = service;
//    }

    public void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws IOException, NoSuchAlgorithmException {
        String fileStudents = ApplicationContext.getPROPERTIES().getProperty("data.students");
        String fileTeme = ApplicationContext.getPROPERTIES().getProperty("data.teme");
        String fileNote = ApplicationContext.getPROPERTIES().getProperty("data.note");

        Validator validator = new ValidatorStudent();
        StudentFileRepository repository = new StudentFileRepository(validator,fileStudents);

        Validator validator1 = new ValidatorTema();
        TemaFileRepository repository1 = new TemaFileRepository(validator1,fileTeme);


        Validator validator2 = new ValidatorNota();
        NotaFileRepository repository2 = new NotaFileRepository(validator2,fileNote);

        this.service = new Service(validator,repository,validator1,repository1);
        this.serviceNota = new ServiceNota(repository2,validator2);

        service.fileDelete();
        service.studentFileGeneral();

// MAIN WINDOW pornire directa
//        init1(primaryStage);
//        primaryStage.setWidth(800);

//LOGIN WINDOW FIRST
        init2(primaryStage);
        primaryStage.show();
        primaryStage.setResizable(false);


//        System.out.println ("Thread " +
//                Thread.currentThread().getId() +
//                " is running");
//        SendEmail sendEmail = new SendEmail("1","root@scs.ubbcluj.ro", "abc", "Add nota", " Nota 10 la tema 3");
//        sendEmail.start();
    }

    private void init2(Stage primaryStage) throws IOException {
        FXMLLoader mainScene = new FXMLLoader();
        String loginView = ApplicationContext.getPROPERTIES().getProperty("view.login");
        mainScene.setLocation(getClass().getResource(loginView));

        AnchorPane studentLayout = mainScene.load();
        primaryStage.setScene(new Scene(studentLayout));

        LoginViewController loginViewController = mainScene.getController();
        loginViewController.setService(service,serviceNota,primaryStage);
    }

    private void init1(Stage primaryStage) throws IOException {

        FXMLLoader mainScene = new FXMLLoader();
        String mainView = ApplicationContext.getPROPERTIES().getProperty("view.main");
        mainScene.setLocation(getClass().getResource(mainView));

        AnchorPane studentLayout = mainScene.load();
        primaryStage.setScene(new Scene(studentLayout));

        MainViewController mainViewController = mainScene.getController();
        mainViewController.setStudentService(service,serviceNota, "admin",null,primaryStage);
        //primaryStage.show();
    }
}