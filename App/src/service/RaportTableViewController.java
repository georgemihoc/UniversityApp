package service;

import configuration.ApplicationContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import utils.events.ChangeEvent;
import utils.observer.Observer;
import validators.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RaportTableViewController implements Observer<ChangeEvent> {
    @FXML
    TableView<Raport1> tableViewRaport;
    @FXML
    TableColumn<Raport1,String> tableColumnStudent;
    @FXML
    TableColumn<Raport1,String> tableColumnNota;
    int raport;

    private Service service;
    private ServiceNota serviceNota;
    ObservableList<Raport1> modelRaport = FXCollections.observableArrayList();
    @FXML
    public void initialize() {

        tableColumnStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        tableColumnNota.setCellValueFactory(new PropertyValueFactory<>("nota"));
        tableViewRaport.setItems(modelRaport);

    }

    public void setService(Service service, ServiceNota serviceNota, int raport) {
        this.service = service;
        this.serviceNota = serviceNota;
        service.addObserver(this);
        serviceNota.addObserver(this);
        if(raport != 4) {
            try {
                initModel(raport);
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
        else {
            try {
                initModel2(raport);
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
    }

    private void initModel(int raport) throws IOException {
        this.raport = raport;
        modelRaport.setAll(service.raport1_3(raport));

        tableColumnNota.setSortType(TableColumn.SortType.DESCENDING);
        tableViewRaport.getSortOrder().add(tableColumnNota);
    }
    private void initModel2(int raport) throws IOException {
        this.raport = raport;
        modelRaport.setAll(service.raport4(raport));

        tableColumnNota.setSortType(TableColumn.SortType.DESCENDING);
        tableViewRaport.getSortOrder().add(tableColumnNota);
    }

    @Override
    public void update(ChangeEvent changeEvent) {
        try {
            initModel(this.raport);
            if(raport == 4)
                initModel2(this.raport);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
