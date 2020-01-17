package service;

import anUniversitar.AnUniversitar;
import configuration.ApplicationContext;
import javafx.event.ActionEvent;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.data.domain.Pageable;
import repository.NotaFileRepository;
import repository.StudentFileRepository;
import repository.TemaFileRepository;
import utils.events.ChangeEventType;
import utils.events.ChangeEvent;
import utils.observer.Observable;
import utils.observer.Observer;
import validators.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class Service implements Observable<ChangeEvent> {
    private static StudentFileRepository repositoryStudent;
    private Validator validatorStudent;
    private static TemaFileRepository repositoryTema;
    private Validator validatorTema;
    private static NotaFileRepository repositoryNota;
    private Validator validatorNota;


    public Service(Validator validatorStudent, StudentFileRepository repositoryStudent,
                   Validator validatorTema, TemaFileRepository repositoryTema, NotaFileRepository repository2, Validator validator2) {
        this.validatorStudent = validatorStudent;
        this.repositoryStudent = repositoryStudent;
        this.validatorTema = validatorTema;
        this.repositoryTema = repositoryTema;
        this.repositoryNota = repository2;
        this.validatorNota = validator2;
    }
    public Iterable<Student> findPage(Pageable pageable)
    {
        return repositoryStudent.findPage(pageable);
    }
    /**
     * Functie de add student
     * input-  nume, grupa, email, cadruDidactic
     * preconditii- nume-String ; grupa-int ; email- String ; cadruDidactic- String
     * output-
     * postconditii-
     */
    public Student addStudent(String nume, int grupa, String email, String cadruDidactic) {
        Student s = new Student(nume, grupa, email, cadruDidactic);
        s.setId(repositoryStudent.findID());

        try {
            Student task = repositoryStudent.save(s);
            notifyObservers(new ChangeEvent(ChangeEventType.ADD,task));
            return s;
        } catch (ValidationException | IllegalArgumentException ex) {
        } catch (Exception ex) {
        }
        return null;
    }
    /**
     * Functie de delete student
     * input- id
     * preconditii- id- long ;
     * output-
     * postconditii-
     */
    public Student deleteStudent(long id){
        Student task=repositoryStudent.delete(id);
        if(task==null)
            System.out.println("Nu exista acest id");
        else {
                notifyObservers(new ChangeEvent(ChangeEventType.DELETE, task));
            return findStudent(id);
        }
        return null;
    }
    /**
     * Functie de update student
     * input- id, nume, grupa, email, cadruDidactic
     * preconditii- id- long ; nume-String ; grupa-int ; email- String ; cadruDidactic- String
     * output-
     * postconditii-
     */
    public Student updateStudent(long id,String nume, int grupa, String email, String cadruDidactic){
        Student oldTask=repositoryStudent.findOne(id);
        if(findStudent(id)!= null) {
            Student s = new Student(nume, grupa, email, cadruDidactic);
            s.setId(id);
            repositoryStudent.update(s);
            if(oldTask!=null)
                notifyObservers(new ChangeEvent(ChangeEventType.UPDATE, s, oldTask));
            return s;
        }
        else return null;
        //rewriteFileXML();
    }
    /**
     * Functie de adaugare unei Teme
     * input- descriere, currentWeek, deadlineWeek, feedback
     * preconditii- descriere- String ; currentWeek, deadlineWeek- int;
     * output-
     * postconditii-
     */
    public Tema addTema(String descriere, int currentWeek, int deadlineWeek) {
        AnUniversitar an = new AnUniversitar();
        int startWeek;
        List<String> lista = null;
        InputStream ins = null; // raw byte-stream
        Reader r = null; // cooked reader
        BufferedReader br = null; // buffered for readLine()
        int ok = 1;
        try {
            String s;
            ins = new FileInputStream("/Users/george/Documents/UBB/Anul 2/MAP/seminar4/src/anUniversitar/StructuraAn.txt");
            r = new InputStreamReader(ins, "UTF-8"); // leave charset out for default
            br = new BufferedReader(r);
            while ((s = br.readLine()) != null) {
                //System.out.println(s);
                if(s.equals(currentWeek))
                    ok = 1;

            }
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage()); // handle exception
        }

//        if (an.saptamani.get(currentWeek).vacanta) {
//            startWeek = an.getWeek(currentWeek);
//        } else {
//            startWeek = currentWeek;
//        }
        if (ok==1) {
            startWeek = an.getWeek(currentWeek);
        } else {
            startWeek = currentWeek;
        }
        try {
            Tema t = new Tema(descriere, startWeek, deadlineWeek);
            t.setId((long) repositoryTema.findID());
            repositoryTema.save(t);
            notifyObservers(new ChangeEvent(ChangeEventType.ADD, t));

//            for (Student s :
//                    Service.findAllStudents()) {
//                SendEmail sendEmail = new SendEmail("T", "root", s.getNume(), "Adaugare tema", " A fost adaugata o noua tema : " +
//                        " " + descriere);
//                sendEmail.start();
//            }
            String Bcc = "";
            int nr =0;
            for (Student s:
                    Service.findAllStudents()) {
                if(nr!=0) {
                    Bcc += s.getNume();
                    Bcc += ',';
                }
                nr =1;
            }
            SendEmail sendEmail = new SendEmail("T","root", findStudent(1L).getNume(), "Add tema", " A fost adaugata tema " +
                    " " + descriere , Bcc);
            sendEmail.start();
            return t;
        }
        catch (Exception ignore){}
        //rewriteFileXML();
        throw new ValidationException("Tema invalida");
    }
    public Tema deleteTema(long id){
        Tema task=repositoryTema.delete(id);
        String Bcc = "";
        int nr =0;
        for (Student s:
                Service.findAllStudents()) {
            if(nr!=0) {
                Bcc += s.getNume();
                Bcc += ',';
            }
            nr =1;
        }
        SendEmail sendEmail = new SendEmail("T","root", findStudent(1L).getNume(), "Delete tema", " A fost stearsa tema " +
                " " + task.getDescriere() , Bcc);
        sendEmail.start();
        List<Nota> list = new CopyOnWriteArrayList<Nota>((Collection<? extends Nota>) findAllNota());
        for(Nota n: list)
        {
            if(n.getIdTema().equals(id))
                deleteNota(n.getIdStudent(),n.getIdTema());
        }

        if(task==null)
            System.out.println("Nu exista acest id");
        else {
            notifyObservers(new ChangeEvent(ChangeEventType.DELETE, (Nota) null));
            return findTema(id);
        }
        return null;
    }

    private Nota deleteNota(Long idStudent, Long idTema) {
        Nota nota=repositoryNota.delete(new Pair(idStudent,idTema));
        notifyObservers(new ChangeEvent(ChangeEventType.DELETE, nota));
        return nota;
    }

    /**
     * Functie de update a unei teme
     * input- id, descriere, currentWeek, deadlineWeek
     * preconditii- id- long ; descriere- String ; currentWeek,deadlineWeek- int ;
     * output-
     * postconditii-
     */
    public Tema updateTema(long id, String descriere, int currentWeek, int deadlineWeek ){
//        for (Student s:
//                Service.findAllStudents()) {
//            SendEmail sendEmail = new SendEmail("T","root", s.getNume(), "Modificare tema", " A fost modificata tema " +
//                    " " + Service.findTemaStatic(id).getDescriere() );
//            sendEmail.start();
//        }
        String Bcc = "";
        int nr =0;
        for (Student s:
                Service.findAllStudents()) {
            if(nr!=0) {
                Bcc += s.getNume();
                Bcc += ',';
            }
            nr =1;
        }
        SendEmail sendEmail = new SendEmail("T","root", findStudent(1L).getNume(), "Update tema", " A fost modificata tema " +
                " " + Service.findTemaStatic(id).getDescriere() , Bcc);
        sendEmail.start();
        Tema oldTask=repositoryTema.findOne(id);
        AnUniversitar an = new AnUniversitar();
        int startWeek;
        if(an.saptamani.get(currentWeek).vacanta)
        {
            startWeek = an.getWeek(currentWeek);
        }
        else {
            startWeek = currentWeek;
        }

        Tema t = new Tema(descriere, startWeek, deadlineWeek);
        t.setId(id);
        repositoryTema.update(t);
        notifyObservers(new ChangeEvent(ChangeEventType.UPDATE, t, oldTask));

        //rewriteFileXML();
        return t;
    }
    /**
     * Functie de stergere a fisierelor personale
     * input-
     * preconditii-
     * output-
     * postconditii-
     */
    public void fileDelete(){
        for(Student s: this.findAllStudents()){
            String  path = "/Users/george/Documents/UBB/Anul 2/MAP/seminar4/src/";
            path = path + s.getNume() + ".txt";

            File file = new File(path);
            try {
                Files.deleteIfExists(Paths.get(path));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    public void studentFileGeneral(){
        for(Nota n: ServiceNota.findAllNota()){
            studentFile(n,n.getDate(),findTema(n.getIdTema()).getDeadlineWeek(),n.getFeedback());
        }
    }
    /**
     * Functie de creeare a fisierului personal cu nota
     * input- n, gradeWeek, deadlineWeek, feedback
     * preconditii- n- Nota ; gradeWeek, deadlineWeek- integer ; feedback- string;
     * output-
     * postconditii-
     */
    public static void studentFile(Nota n, int gradeWeek, int deadlineWeek, String feedback){

        String  nume="",path = "/Users/george/Documents/UBB/Anul 2/MAP/seminar4/src/";

        for(Student s: findAllStudents()){
            if(s.getId() == n.getIdStudent())
                nume=s.getNume();
        }
        path = path + nume +".txt";
        FileWriter fw = null;
        try {
            fw = new FileWriter(path, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);

        out.print("ID-ul temei:");
        out.println(n.getIdTema());
        //
        out.print("Nota:");
        out.println(n.getNota());
        //
        out.print("Predata in saptamana:");
        out.println(gradeWeek);
        //
        out.print("Deadline:");
        out.println(deadlineWeek);
        //
        out.print("Feedback:");
        out.println(feedback);
        out.println();
        out.close();
    }

    /**
     * Functie care returneaza toti studentii unei grupe anume
     * input- students, grupa
     * preconditii- students (List<Student>), grupa (int)
     * output - collector
     * postconditii - List<student>
     */
    public static List<Student> report1(List<Student> students, int grupa) {
        Predicate<Student> p1 = x -> x.getGrupa() == grupa;
        //Predicate<Student> p = p1.and(p2);

        return students.stream()
                .filter(p1)
                .collect(Collectors.toList());
    }
    /**
     * Functie care returneaza toti studentii care au predat o anumita tema
     * input- note, id
     * preconditii- note (List<Nota>), id (long)
     * output - collector
     * postconditii - List<Nota>
     */
    public static List<Nota> report2(List<Nota> note, long id)
    {
        Predicate<Nota> p1 = x -> x.getIdTema() == id;
        return note.stream()
                .filter(p1)
                .collect(Collectors.toList());
    }
    /**
     * Functie care returneaza toti studentii care au predat o anumita tema unui profesor anume
     * input- note, id, cadruDidactic
     * preconditii- note (List<Nota>), id (long), cadruDidactic (String)
     * output - collector
     * postconditii - List<Nota>
     */
    public static List<Nota> report3(List<Nota> note, long id, String cadruDidactic)
    {
        Predicate<Nota> p1 = x -> x.getIdTema() == id;
        Predicate<Nota> p2 = x-> x.getProfesor().equals(cadruDidactic);
        Predicate<Nota> p = p1.and(p2);

        return note.stream()
                .filter(p)
                .collect(Collectors.toList());
    }
    /**
     * Functie care returneaza toate notele la o anumita tema dintr-o saptamana data
     * input- note, id, week
     * preconditii- note (List<Nota>), id (long), week (int)
     * output - collector
     * postconditii - List<Nota>
     */
    public static List<Nota> report4(List<Nota> note, long id, int week)
    {
        Predicate<Nota> p1 = x -> x.getIdTema() == id;
        Predicate<Nota> p2 = x-> x.getDate()== week;
        Predicate<Nota> p = p1.and(p2);

        return note.stream()
                .filter(p)
                .collect(Collectors.toList());
    }

    public List<Raport1> raport1_3(int raport) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(25, 750);

        if(raport==1)
            contentStream.showText("Medii Studenti");
        else if(raport == 3)
            contentStream.showText("Studentii care pot intra in examen");

        contentStream.newLine();
        contentStream.newLine();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

        Iterable<Nota> note = ServiceNota.findAllNota();
        Iterable<Student> students = Service.findAllStudents();
        Iterable<Tema> teme = Service.findAllTema();
        List<Raport1> medie = new ArrayList<>();
        int nrSaptamani=0;
        int nr=0;
        for (Tema t:
                teme) {
            nrSaptamani += (t.getDeadlineWeek()-t.getStartWeek());
        }
        for (Student s:
                students) {
            float suma= 0;
            for (Nota n:
                    note) {
                if(s.getId().equals(n.getIdStudent()))
                    suma += n.getNota()*(this.findTema(n.getIdTema()).getDeadlineWeek()-this.findTema(n.getIdTema()).getStartWeek());
            }
            suma /= nrSaptamani;
            if( suma>4 || raport!=3 ) {

                medie.add(new Raport1(s.getNume(), suma));
                contentStream.showText("Student: " + s.getNume() + "     Medie: " + suma);
                contentStream.newLine();
                nr++;
                if(nr==50){
                    contentStream.endText();
                    contentStream.close();
                    nr = 0;
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page,true,true);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                    contentStream.setLeading(14.5f);
                    contentStream.newLineAtOffset(25, 750);

                }
            }
        }

        contentStream.endText();
        contentStream.close();
        //Saving the document
        if(raport==1) {
            String path = ApplicationContext.getPROPERTIES().getProperty("raport.first");
            document.save(path);
        }
        else
        {
            String path = ApplicationContext.getPROPERTIES().getProperty("raport.third");
            document.save(path);
        }
        System.out.println("PDF created");
        //Closing the document
        document.close();

        return medie;
    }
    public int raport2() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(25, 750);
        contentStream.showText("Cea mai grea tema");
        contentStream.newLine();
        contentStream.newLine();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);



        Iterable<Nota> note = ServiceNota.findAllNota();
        Iterable<Tema> teme = Service.findAllTema();
        float min = 10;
        int index = 0;
        for (Tema t:
                teme) {
            int nr = 0;
            float suma = 0;
            for (Nota n:
                    note) {
                if(t.getId().equals(n.getIdTema()))
                {
                    suma+= n.getNota();
                    nr++;
                }
            }
            if(nr!= 0)
                suma /= nr;
            System.out.println(suma);
            if(suma<min) {
                min = suma;
                index = Math.toIntExact(t.getId())-1;
            }
        }
        long finalIndex = index+1;
        Service.findAllTema().forEach(tema -> {
            if(tema.getId().equals(finalIndex)) {
                try {
                    contentStream.showText(tema.getDescriere());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        contentStream.endText();
        contentStream.close();
        //Saving the document
        String path = ApplicationContext.getPROPERTIES().getProperty("raport.second");
        document.save(path);
        System.out.println("PDF created");
        //Closing the document
        document.close();

        return index;
    }
    public List<Raport1> raport4(int raport) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(25, 750);
        contentStream.showText("Studentii care au predat toate temele la timp");
        contentStream.newLine();
        contentStream.newLine();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);


        Iterable<Nota> note = ServiceNota.findAllNota();
        Iterable<Student> students = Service.findAllStudents();
        Iterable<Tema> teme = Service.findAllTema();
        List<Raport1> raport4 = new ArrayList<>();
        int nrSaptamani=0;
        int nr = 0;
        for (Tema t:
                teme) {
            nrSaptamani += (t.getDeadlineWeek()-t.getStartWeek());
        }
        for (Student s:
                students) {
            boolean ok = true;
            float suma= 0;
            int nrTemePredate=0;
            for (Nota n :
                    note) {
                if(s.getId().equals(n.getIdStudent())) {
                    nrTemePredate++;
                    suma += n.getNota() * (this.findTema(n.getIdTema()).getDeadlineWeek() - this.findTema(n.getIdTema()).getStartWeek());
                    if (n.getDate() > this.findTema(n.getIdTema()).getDeadlineWeek() && n.getFeedback().contains("DIMINUATĂ"))
                        ok = false;
                }
            }
            if(nrTemePredate!= Service.findAllTema().spliterator().getExactSizeIfKnown())
                ok = false;
            suma /= nrSaptamani;
            if(ok && suma!=0){
                raport4.add(new Raport1(s.getNume(),suma));
                contentStream.showText(s.getNume());
                contentStream.newLine();
                nr++;
                if(nr==50){
                    contentStream.endText();
                    contentStream.close();
                    nr = 0;
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page,true,true);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                    contentStream.setLeading(14.5f);
                    contentStream.newLineAtOffset(25, 750);

                }
            }
        }

        contentStream.endText();
        contentStream.close();
        //Saving the document
        String path = ApplicationContext.getPROPERTIES().getProperty("raport.fourth");
        document.save(path);
        System.out.println("PDF created");
        //Closing the document
        document.close();

        return raport4;
    }
    /**
     * Functie de findAll pt studenti
     * input-
     * preconditii-
     * output- repositoryStudent.findAll()
     * postconditii- repositoryStudent.findAll()- Iterable<Student>
     */
    public static Iterable<Student> findAllStudents(){
        return repositoryStudent.findAll();
    }
    /**
     * Functie de find student
     * input-id
     * preconditii-id (long)
     * output- repositoryStudent.findOne()
     * postconditii- repositoryStudent.findAll()- Student
     */
    public Student findStudent(long id){
        return repositoryStudent.findOne(id);
    }
    public static Student findStudentStatic(long id){
        return repositoryStudent.findOne(id);
    }
    public Tema findTema(long id){
        return repositoryTema.findOne(id);
    }
    public static Tema findTemaStatic(long id){
        return repositoryTema.findOne(id);
    }
    /**
     * Functie de findAll pt teme
     * input-
     * preconditii-
     * output- repositoryTema.findAll()
     * postconditii- repositoryTema.findAll()- Iterable<Tema>
     */
    public static Iterable<Tema> findAllTema(){
        return repositoryTema.findAll();
    }
    public static Iterable<Nota> findAllNota(){
        return repositoryNota.findAll();
    }

    private List<Observer<ChangeEvent>> observers=new ArrayList<>();
//    private List<Observer<NotaChangeEvent>> observersNota=new ArrayList<>();

    @Override
    public void addObserver(Observer<ChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<ChangeEvent> e) {
//        observers.remove(e);
    }//    @Override

//    public void notifyObserversNota(NotaChangeEvent t) {
//        observers.stream().forEach(x->x.update(t));
//    }

    @Override
    public void notifyObservers(ChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }

    public Long findIdStudent(String name) {
        for (Student s:
             repositoryStudent.findAll()) {
            if(s.getNume().equals(name))
                return s.getId();
        }
        return null;
    }
    public Long findIdTema(String descriere) {
        for (Tema t:
                repositoryTema.findAll()) {
            if(t.getDescriere().equals(descriere))
                return t.getId();
        }
        return null;
    }
}