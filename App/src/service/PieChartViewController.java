package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import utils.events.ChangeEvent;
import utils.observer.Observer;
import validators.Nota;
import validators.Student;
import validators.Tema;

import java.util.ArrayList;
import java.util.List;

import java.lang.Math;


public class PieChartViewController implements Observer<ChangeEvent> {

    @FXML
    PieChart pieChart;

    CategoryAxis xAxis    = new CategoryAxis();

    NumberAxis yAxis = new NumberAxis();


    @FXML
    BarChart barChart = new BarChart(xAxis, yAxis);

    int raport;

    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();


    private Service service;
    private ServiceNota serviceNota;

    @FXML
    public void initialize(){

    }
    public void initModel1(int raport){
        this.raport = raport;
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Numar studenti / fiecare medie");

        Iterable<Nota> note = ServiceNota.findAllNota();
        Iterable<Student> students = Service.findAllStudents();
        Iterable<Tema> teme = Service.findAllTema();
        int[] numar = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] note1 = new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        int nrSaptamani=0;
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
                    suma += n.getNota()*(service.findTema(n.getIdTema()).getDeadlineWeek()-service.findTema(n.getIdTema()).getStartWeek());
            }
            suma /= nrSaptamani;
            numar[10-Math.round(suma)] ++;
        }
        for (int i = 0;i<=10;i++) {
            dataSeries1.getData().add(new XYChart.Data(Integer.toString(10-i),numar[i]));
        }
        barChart.getData().add(dataSeries1);
    }
    public void initModel3(int raport){
        this.raport = raport;
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Studenti");

        Iterable<Nota> note = ServiceNota.findAllNota();
        Iterable<Student> students = Service.findAllStudents();
        Iterable<Tema> teme = Service.findAllTema();
        int nrSaptamani=0;
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
                    suma += n.getNota()*(service.findTema(n.getIdTema()).getDeadlineWeek()-service.findTema(n.getIdTema()).getStartWeek());
            }
            suma /= nrSaptamani;
            if( suma>4 || raport!=3 ) {
                pieChartData.add(new PieChart.Data(s.getNume(), suma));
                dataSeries1.getData().add(new XYChart.Data(s.getNume(),suma));
            }
        }
        pieChart.setData(pieChartData);
        barChart.getData().add(dataSeries1);
        if(raport==3)
            pieChart.setVisible(false);
    }
    public void initModel2(int raport){
        this.raport = raport;
        XYChart.Series dataSeries = new XYChart.Series();

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
            suma /= nr;
            System.out.println(suma);
            if(suma<min) {
                min = suma;
                index = Math.toIntExact(t.getId())-1;
            }
            dataSeries.getData().add(new XYChart.Data(t.getDescriere(),suma));
            pieChartData.add(new PieChart.Data(t.getDescriere(), suma));

        }
//        long finalIndex = index+1;
//        Service.findAllTema().forEach(tema -> {
//            if(tema.getId().equals(finalIndex)) {
//                pieChartData.add(new PieChart.Data(tema.getDescriere(),100));
//            }
//        });
        pieChart.setData(pieChartData);
        pieChart.setVisible(false);
        barChart.getData().add(dataSeries);

    }
    public void initModel4(int raport){
        this.raport = raport;
        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Studenti");

        Iterable<Nota> note = ServiceNota.findAllNota();
        Iterable<Student> students = Service.findAllStudents();
        Iterable<Tema> teme = Service.findAllTema();
        int nrSaptamani=0;
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
                    suma += n.getNota() * (service.findTema(n.getIdTema()).getDeadlineWeek() - service.findTema(n.getIdTema()).getStartWeek());
                    if (n.getDate() > service.findTema(n.getIdTema()).getDeadlineWeek() && n.getFeedback().contains("DIMINUATĂ"))
                        ok = false;
                }
            }
            if(nrTemePredate!= Service.findAllTema().spliterator().getExactSizeIfKnown())
                ok = false;
            suma /= nrSaptamani;
            if(ok && suma!=0){
                pieChartData.add(new PieChart.Data(s.getNume(),suma));
                dataSeries1.getData().add(new XYChart.Data(s.getNume(),suma));

            }
        }
        pieChart.setData(pieChartData);
        pieChart.setVisible(false);
        barChart.getData().add(dataSeries1);

    }
    void setService(Service service, ServiceNota serviceNota, int raport) {
        this.service= service;
        this.serviceNota = serviceNota;
        service.addObserver(this);
        serviceNota.addObserver(this);
        if(raport == 1)
            initModel1(raport);
        else if (raport == 2)
            initModel2(raport);
        else if (raport == 3)
            initModel3(raport);
        else if (raport == 4)
            initModel4(raport);
    }

    @Override
    public void update(ChangeEvent changeEvent) {
        barChart.getData().clear();
        pieChart.getData().clear();
        if(raport==1 )
            initModel1(raport);
        if(raport==3)
            initModel3(raport);
        else if(raport == 2)
            initModel2(raport);
        else if(raport == 4)
            initModel4(raport);
    }
}
