package service;

import anUniversitar.AnUniversitar;
import repository.NotaFileRepository;
import utils.events.ChangeEventType;
import utils.events.ChangeEvent;
import utils.observer.Observable;
import utils.observer.Observer;
import validators.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceNota implements Observable<ChangeEvent> {
    private static NotaFileRepository repositoryNota;
    private Validator validatorNota;

    private List<Observer<ChangeEvent>> observers=new ArrayList<>();

    public ServiceNota(NotaFileRepository repositoryNota, Validator validatorNota) {
        this.repositoryNota = repositoryNota;
        this.validatorNota = validatorNota;
    }

    public ServiceNota() {
    }

    /**
     * Functie de adaugare a unei note
     * input- idStudent, idTemma, nota, gradeWeek, feedback
     * preconditii- idStudent,idTema- long ; nota- float ; gradeWeek- integer ; feedback- string;
     * output-
     * postconditii-
     */
    public Nota addNota(long idStudent,long idTema,float nota, int gradeWeek, String feedback ){
        String cadruDidactic="";
//        int currentWeek =Integer.parseInt(new SimpleDateFormat("w").format(new java.util.Date()))-40+1;
        int currentWeek = AnUniversitar.getCurrentWeek();
        int deadlineWeek=currentWeek;

        for (Student s:Service.findAllStudents()) {
            if(s.getId()== idStudent){
                cadruDidactic= s.getCadruDidactic();
            }
        }

        if(cadruDidactic.equals(""))
            throw new ValidationException("Student inexistent");
        boolean ok = false;
        for (Tema t:Service.findAllTema()){
            if(t.getId()==idTema) {
                deadlineWeek = t.getDeadlineWeek();
                ok = true;
            }
        }
        if(!ok)
            throw new ValidationException("Tema inexistenta");


//        if(gradeWeek>deadlineWeek){
//            if(gradeWeek-deadlineWeek>2)
//                nota = 0;
//            else
//                nota = nota - gradeWeek+deadlineWeek;
//        }
        for (Nota n:this.findAllNota()){
            if(n.getIdStudent()==idStudent && n.getIdTema() == idTema)
                throw new ValidationException("Nota deja existenta");
        }
        try{
            Nota n = new Nota(idStudent,idTema,gradeWeek,nota,cadruDidactic,feedback);
            //n.setId(repositoryNota.findID());
            n.setId(new Pair(idStudent,idTema));
            Service.studentFile(n,gradeWeek,deadlineWeek,feedback);

            Nota task =repositoryNota.save(n);

            cadruDidactic = cadruDidactic.replaceAll("\\s+","");
            SendEmail sendEmail = new SendEmail("T",cadruDidactic, Service.findStudentStatic(idStudent).getNume(), "Add nota", " A fost adaugata nota " +
                    " " + nota + " la tema " + Service.findTemaStatic(idTema).getDescriere(),null);
            sendEmail.start();
            notifyObservers(new ChangeEvent(ChangeEventType.ADDNOTA,task));
            return task;
        }catch (Exception ex){
        }
        return null;
    }
    public Nota deleteNota(long idStudent, long idTema){
        Nota nota=repositoryNota.delete(new Pair(idStudent,idTema));
        notifyObservers(new ChangeEvent(ChangeEventType.DELETE, nota));
        return nota;
    }
    public static Nota deleteNotaStatic(long idStudent, long idTema){
        Nota nota=repositoryNota.delete(new Pair(idStudent,idTema));
//        ServiceNota foo = new ServiceNota();
//        foo.notifyObservers(new ChangeEvent(ChangeEventType.DELETE, nota));
//        notifyObservers(new ChangeEvent(ChangeEventType.DELETE, nota));
        return nota;
    }
    /**
     * Functie de findAll pt note
     * input-
     * preconditii-
     * output- repositoryNota.findAll()
     * postconditii- repositoryNota.findAll()- Iterable<Nota>
     */
    public static Iterable<Nota> findAllNota(){
        return repositoryNota.findAll();
    }

    @Override
    public void addObserver(Observer<ChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<ChangeEvent> e) {

    }

    @Override
    public void notifyObservers(ChangeEvent t) {
        observers.stream().forEach(x->x.update(t));
    }
}