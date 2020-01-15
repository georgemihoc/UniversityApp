//package com.company;
//
//import repository.*;
//import service.Service;
//import service.ServiceNota;
//import validators.*;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Scanner;
//
//
//
//public class UI {
//    Service service;
//    static ServiceNota serviceNota;
//
//    public UI(Service service,ServiceNota serviceNota) {
//        this.service = service;
//        this.serviceNota = serviceNota;
//    }
//
//    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//    private static int citire()
//    {
//        try {
//            Scanner scanner = new Scanner(System.in);
//            return Integer.parseInt(scanner.nextLine());
//        }catch (Exception ex)
//        {
//            return -1;
//        }
//    }
//    private static  void meniu(){
//        System.out.println("\nMENU");
//        System.out.println("1.Adaugati Student");
//        System.out.println("2.Stergeti  Student");
//        System.out.println("3.Updatati Student");
//        System.out.println("4.Printati Studentii");
//        System.out.println("---------------------");
//        System.out.println("5.Adaugati Tema");
//        System.out.println("6.Stergeti  Tema");
//        System.out.println("7.Updatati Tema");
//        System.out.println("8.Printati Temele");
//        System.out.println("---------------------");
//        System.out.println("9.Adaugati Nota");
//        System.out.println("10.Printati Notele");
//        System.out.println("11.Student File");
//        System.out.println("---------------------");
//        System.out.println("12.Afisati toti studentii dintr-o anumita grupa");
//        System.out.println("13.Afisati toti studentii care au predat o anumita tema");
//        System.out.println("14.Afisati toti studentii care au predat o anumita tema unui profesor anume");
//        System.out.println("15.Afisati toate notele la o anumita tema, dintr-o saptamana data");
//        System.out.println("0.EXIT");
//
//    }
//
//    private static void loadStudents(Service service){
//        String nume,email,cadruDidactic;
//        int grupa;
//
//        String csvFile = "/Users/george/Documents/UBB/Anul 2/MAP/seminar4/src/students.csv";
//        String line;
//        try {
//            Scanner br = new Scanner(new File(csvFile));
//            while ((line = br.nextLine()) != null) {
//                String[] sir = line.split(";");
//                nume = sir[0];
//                grupa = Integer.parseInt(sir[1]);
//                email = sir[2];
//                cadruDidactic = sir[3];
//                service.addStudent(nume,grupa,email,cadruDidactic);
//            }
//
//        }catch (Exception ex){}
//    }
//    private static void loadTema(Service service){
//        String descriere;
//        int currentWeek,deadlineWeek;
//
//        String csvFile = "/Users/george/Documents/UBB/Anul 2/MAP/seminar4/src/tema.csv";
//        String line;
//        try {
//            Scanner br = new Scanner(new File(csvFile));
//            while ((line = br.nextLine()) != null) {
//                String[] sir = line.split(";");
//                descriere = sir[0];
//                currentWeek = Integer.parseInt(sir[1]);
//                deadlineWeek = Integer.parseInt(sir[2]);
//                service.addTema(descriere,currentWeek,deadlineWeek);
//            }
//        }catch (Exception ex){}
//    }
//    private static void loadNota(Service service){
//        long idStudent,idTema;
//        String feedback;
//        String csvFile = "/Users/george/Documents/UBB/Anul 2/MAP/seminar4/src/note.csv";
//        String line;
//        try {
//            Scanner br = new Scanner(new File(csvFile));
//            while ((line = br.nextLine()) != null) {
//                String[] sir = line.split(";");
//                idStudent = Long.parseLong(sir[0]);
//                idTema = Long.parseLong(sir[1]);
//                float nota = Float.parseFloat(sir[2]);
//                int gradeWeek = Integer.parseInt(sir[3]);
//                feedback = sir[4];
//
//                serviceNota.addNota(idStudent,idTema,nota,gradeWeek,feedback);
//            }
//        }catch (Exception ex){}
//    }
//
//    private static void addStudent(Service service){
//        String nume,email,cadruDidactic;
//        int grupa;
//
//        try {
//
//            System.out.println("Introduceti numele studentului:");
//            Scanner scanner = new Scanner(System.in);
//            nume = scanner.nextLine();
//            System.out.println("Introduceti grupa studentului:");
//            scanner = new Scanner(System.in);
//            grupa = Integer.parseInt(scanner.nextLine());
//            System.out.println("Introduceti email-ul studentului:");
//            scanner = new Scanner(System.in);
//            email = scanner.nextLine();
//            System.out.println("Introduceti cadrul didactic:");
//            scanner = new Scanner(System.in);
//            cadruDidactic = scanner.nextLine();
//
//            service.addStudent(nume,grupa,email,cadruDidactic);
//
//        }catch (Exception ex){
//            //System.out.println("Student invalid");
//        }
//    }
//    private static void deleteStudent(Service service){
//        try {
//            int id;
//            System.out.println("Introduceti id-ul:");
//            Scanner scanner = new Scanner(System.in);
//            id = Integer.parseInt(scanner.nextLine());
//            service.deleteStudent(id);
//        }catch (Exception ex)
//        {
//            System.out.println("Id invalid");
//        }
//    }
//    private static void updateStudent(Service service) {
//        String nume, email, cadruDidactic;
//        int grupa;
//        long id;
//        try {
//            System.out.println("Introduceti id-ul studentului:");
//            Scanner scanner = new Scanner(System.in);
//            id = Long.parseLong(scanner.nextLine());
//            System.out.println("Introduceti numele studentului:");
//            scanner = new Scanner(System.in);
//            nume = scanner.nextLine();
//            System.out.println("Introduceti grupa studentului:");
//            scanner = new Scanner(System.in);
//            grupa = Integer.parseInt(scanner.nextLine());
//            System.out.println("Introduceti email-ul studentului:");
//            scanner = new Scanner(System.in);
//            email = scanner.nextLine();
//            System.out.println("Introduceti cadrul didactic:");
//            scanner = new Scanner(System.in);
//            cadruDidactic = scanner.nextLine();
//
//            service.updateStudent(id,nume,grupa,email,cadruDidactic);
//
//        } catch (IllegalArgumentException il) {
//            System.out.println(il.getMessage());
//        }
//    }
//    private static void updateTema(Service service) {
//        String descriere;
//        int startWeek,currentWeek, deadlineWeek;
//        long id;
//        currentWeek = Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date())) - 40 + 1;
//        try {
//            System.out.println("Introduceti id-ul temei:");
//            Scanner scanner = new Scanner(System.in);
//            id = Long.parseLong(scanner.nextLine());
//            System.out.println("Introduceti descrierea temei:");
//            scanner = new Scanner(System.in);
//            descriere = scanner.nextLine();
//
//            System.out.println("Introduceti deadline week:");
//            scanner = new Scanner(System.in);
//            deadlineWeek = Integer.parseInt(scanner.nextLine());
//
//            service.updateTema(id,descriere,currentWeek,deadlineWeek);
//        }catch (IllegalArgumentException il)
//        {
//            //System.out.println(il.getMessage());
//        }
//    }
//
//    private static void addTema(Service service){
//        String descriere;
//        int currentWeek,deadlineWeek;
//        currentWeek =Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date()))-40+1;
//
//        try {
//            System.out.println("Introduceti descrierea temei:");
//            Scanner scanner = new Scanner(System.in);
//            descriere = scanner.nextLine();
//
//            System.out.println("Introduceti deadline week:");
//            scanner = new Scanner(System.in);
//            deadlineWeek = Integer.parseInt(scanner.nextLine());
//
//            service.addTema(descriere,currentWeek,deadlineWeek);
//        }catch (Exception ex){
//        }
//    }
//    private static void deleteTema(Service service){
//        try {
//            int id;
//            System.out.println("Introduceti id-ul:");
//            Scanner scanner = new Scanner(System.in);
//            id = Integer.parseInt(scanner.nextLine());
//            service.deleteTema(id);
//        }catch (Exception ex)
//        {
//            System.out.println("Id invalid");
//        }
//    }
//    private static void printStudents(Service service){
//        if(((Collection<Student>)service.findAllStudents()).size() == 0)
//            System.out.println("No entries");
//        else
//            service.findAllStudents().forEach(System.out::println);
//    }
//    private static void printTema(Service service){
//        if(((Collection<Tema>)service.findAllTema()).size() == 0)
//            System.out.println("No entries");
//        else
//            service.findAllTema().forEach(System.out::println);
//    }
//
//    private static void addNota(Service service){
//        long idStudent, idTema;
//        float nota;
//        int currentWeek =Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date()))-40+1;
//        int deadlineWeek=0;
//        boolean ok = false;
//
//
//        try {
//            System.out.println("Introduceti id-ul studentului:");
//            Scanner scanner = new Scanner(System.in);
//            idStudent = Long.parseLong(scanner.nextLine());
//
//            System.out.println("Introduceti id-ul temei:");
//            scanner = new Scanner(System.in);
//            idTema = Long.parseLong(scanner.nextLine());
//
//            System.out.println("Introduceti nota:");
//            scanner = new Scanner(System.in);
//            nota = Float.parseFloat(scanner.nextLine());
//
//            System.out.println("Introduceti feedback:");
//            scanner = new Scanner(System.in);
//            String feedback = scanner.nextLine();
//
//            for (Tema t:service.findAllTema()){
//                if(t.getId()==idTema) {
//                    deadlineWeek = t.getDeadlineWeek();
//                    ok = true;
//                }
//            }
//            if(currentWeek>deadlineWeek){
//                System.out.println("Motivare?");
//                scanner = new Scanner(System.in);
//                String motivare = scanner.nextLine();
//                int nr=0;
//                ///DE SCOS SI PUS IN UI
//                if(motivare.equals("nu")) {
//                    System.out.println("Cat timp?");
//                    scanner = new Scanner(System.in);
//                    nr = Integer.parseInt(scanner.nextLine());
//                }
//                if(currentWeek-deadlineWeek>2)
//                    if(motivare.equals("nu") || currentWeek - deadlineWeek>nr)
//                        nota = 0;
//                    else {
//                        nota = nota - currentWeek + deadlineWeek;
//                    }
//            }
//
//            serviceNota.addNota(idStudent,idTema,nota,currentWeek,feedback);
//        }catch (Exception ex){
//            System.out.println(ex.getMessage());
//        }
//    }
//    private static void printNota(Service service){
//        if(((Collection<Nota>)serviceNota.findAllNota()).size() == 0)
//            System.out.println("No entries");
//        else
//            serviceNota.findAllNota().forEach(System.out::println);
//    }
//    private static void filterGrupa(Service service){
//        try {
//            System.out.println("Introduceti grupa studentului:");
//            Scanner scanner = new Scanner(System.in);
//            int grupa = Integer.parseInt(scanner.nextLine());
//            List<Student> students = new ArrayList<>();
//            for (Student s : service.findAllStudents())
//                students.add(s);
//            List<Student> rez = service.report1(students,grupa);
//            for (Student s:rez) {
//                System.out.println(s);
//            }
//        }catch (Exception ex){
//            System.out.println("Grupa invalida");
//        }
//    }
//    private static void filterStudentTema(Service service){
//        try {
//            System.out.println("Introduceti id-ul temei:");
//            Scanner scanner = new Scanner(System.in);
//            long id_tema = Long.parseLong(scanner.nextLine());
//            List<Nota> note = new ArrayList<>();
//            for (Nota n : serviceNota.findAllNota())
//                note.add(n);
//            List<Nota> rez = service.report2(note,id_tema);
//            for (Nota n:rez) {
//                System.out.println(service.findStudent(n.getIdStudent()));
//            }
//        }catch (Exception ex){
//            System.out.println("Tema invalida");
//        }
//    }
//    private static void filterStudentTemaProfesor(Service service){
//        try {
//            System.out.println("Introduceti id-ul temei:");
//            Scanner scanner = new Scanner(System.in);
//            long id_tema = Long.parseLong(scanner.nextLine());
//
//            System.out.println("Introduceti cadrul didactic:");
//            scanner = new Scanner(System.in);
//            String cadruDidactic = scanner.nextLine();
//
//            List<Nota> note = new ArrayList<>();
//            for (Nota n : serviceNota.findAllNota())
//                note.add(n);
//            List<Nota> rez = service.report3(note,id_tema,cadruDidactic);
//            for (Nota n:rez) {
//                System.out.println(service.findStudent(n.getIdStudent()));
//            }
//        }catch (Exception ex){
//            System.out.println("Tema invalida");
//        }
//    }
//    private static void filterNoteTemaWeek(Service service){
//        try {
//            System.out.println("Introduceti id-ul temei:");
//            Scanner scanner = new Scanner(System.in);
//            long id_tema = Long.parseLong(scanner.nextLine());
//
//            System.out.println("Introduceti saptamana:");
//             scanner = new Scanner(System.in);
//            int week = Integer.parseInt(scanner.nextLine());
//
//            List<Nota> note = new ArrayList<>();
//            for (Nota n : serviceNota.findAllNota())
//                note.add(n);
//            List<Nota> rez = service.report4(note,id_tema,week);
//            for (Nota n:rez) {
//                System.out.println(n);
//            }
//        }catch (Exception ex){
//            System.out.println("Tema invalida");
//        }
//    }
//    private void studentFile(Service service) {
//        //service.studentFileGeneral();
//    }
//    public void run(String[] args)  {
////        Validator validator = new ValidatorStudent();
////        StudentFileRepository repository = new StudentFileRepository(validator,"/Users/george/Documents/UBB/Anul 2/MAP/seminar4/src/students.csv");
////
////        Validator validator1 = new ValidatorTema();
////        TemaFileRepository repository1 = new TemaFileRepository(validator1,"/Users/george/Documents/UBB/Anul 2/MAP/seminar4/src/tema.csv");
////
////
////        Validator validator2 = new ValidatorNota();
////        InMemoryRepository<Long, Tema> repository2 = new NotaRepository(validator2);
////
////        Service service = new Service(validator,repository,validator1,repository1,validator2,repository2);
////
//        service.fileDelete();
//        //service.studentFileGeneral();
//
//
//        int cmd=2;
//        while(true){
//            meniu();
//            cmd = citire();
//            switch (cmd) {
//                case 1:
//                    addStudent(service);
//                    //service.writeStudentFile();
//                    break;
//                case 2:
//                    deleteStudent(service);
//                    //service.writeStudentFile();
//                    break;
//                case 3:
//                    updateStudent(service);
//                    //service.writeStudentFile();
//                    break;
//                case 4:
//                    printStudents(service);
//                    break;
//                case 5:
//                    addTema(service);
//                    //service.writeTemaFile();
//                    break;
//                case 6:
//                    deleteTema(service);
//                    //service.writeTemaFile();
//                    break;
//                case 7:
//                    updateTema(service);
//                    //service.writeTemaFile();
//                    break;
//                case 8:
//                    printTema(service);
//                    break;
//                case 9:
//                    addNota(service);
//                    //service.writeNotaFile();
//                    break;
//                case 10:
//                    printNota(service);
//                    break;
//                case 11:
//                    studentFile(service);
//                    break;
//                case 12:
//                    filterGrupa(service);
//                    break;
//                case 13:
//                    filterStudentTema(service);
//                    break;
//                case 14:
//                    filterStudentTemaProfesor(service);
//                    break;
//                case 15:
//                    filterNoteTemaWeek(service);
//                    break;
//                case 0:
//                    System.out.println("EXIT");
//                    return;
//                default:
//                    System.out.println("Comanda invalida");
//            }
//        }
//    }
//}