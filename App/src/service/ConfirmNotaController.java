package service;

import anUniversitar.AnUniversitar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import validators.*;

public class ConfirmNotaController {

    public AnchorPane mainAnchorPane;
    @FXML
    private TextField textFieldStudent ;
    @FXML
    private TextField textFieldTema;
    @FXML
    private TextField textFieldNota;
    @FXML
    private TextArea textAreaFeedback;

    private Service service;
    Stage dialogStage;
    NotaDto nota;
    Pair id;
    String feedback;
    private ServiceNota serviceNota;
    Boolean motivat, intarziat;

    @FXML
    CheckBox checkBoxMotivat;
    @FXML
    CheckBox checkBoxIntarziat;

    Integer value;

    @FXML
    private void initialize() {
    }


    public void setService(Service service, ServiceNota serviceNota, Stage stage, NotaDto n, String feedback,
                           Boolean motivat, Boolean intarziat, Integer value) {
        this.service = service;
        this.serviceNota = serviceNota;
        this.dialogStage=stage;
        this.nota=n;
        this.feedback = feedback;
        this.motivat = motivat;
        this.intarziat = intarziat;
        this.value = value;
        if (null != n) {
            setFields(n,motivat,intarziat,value);
        }
        textFieldNota.setEditable(false);
        textAreaFeedback.setEditable(false);
        textFieldStudent.setEditable(false);
        textFieldStudent.setEditable(false);
    }
    private void setFields(NotaDto n, Boolean motivat, Boolean intarziat, Integer value)
    {
        Long idTema = service.findIdTema(n.getTemaName());
        int intarziere = AnUniversitar.getCurrentWeek()-service.findTema(idTema).getDeadlineWeek();
        if(intarziere<0)
            intarziere = 0;
        textFieldStudent.setText(n.getStudentName());
        textFieldTema.setText(n.getTemaName());
        if(motivat){
            if(value >= intarziere) {
                textFieldNota.setText(String.valueOf(n.getNota()));
                textAreaFeedback.setText(feedback);
            }

            else {
                textFieldNota.setText(String.valueOf(n.getNota() - intarziere + value));
                textAreaFeedback.setText(feedback + '\n' + "NOTA A FOST DIMINUATĂ CU " + (intarziere-value) + "\n PUNCTE DATORITĂ ÎNTÂRZIERILOR");
            }
//            System.out.println(n.getNota()-intarziere + value);
        }
        else if(intarziat){
                textFieldNota.setText(String.valueOf(n.getNota()));
                textAreaFeedback.setText(feedback);
        }
        else {
            textFieldNota.setText(String.valueOf(n.getNota() - intarziere));
            if (intarziere > 0)
                textAreaFeedback.setText(feedback + '\n' + "NOTA A FOST DIMINUATĂ CU " + intarziere + "\n PUNCTE DATORITĂ ÎNTÂRZIERILOR");
            else
                textAreaFeedback.setText(feedback);
        }
        checkBoxMotivat.setSelected(motivat);
        checkBoxIntarziat.setSelected(intarziat);
        id = n.getId();
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }

    public void handleOK(ActionEvent actionEvent) {
        try {
            Long idStudent = service.findIdStudent(textFieldStudent.getText());
            Long idTema = service.findIdTema(textFieldTema.getText());
            Float nota1 = Float.valueOf(textFieldNota.getText());
            int currentWeek = AnUniversitar.getCurrentWeek();
            String feedback = textAreaFeedback.getText();
//            String feedback = this.feedback;
            Nota nota = serviceNota.addNota(idStudent,idTema,nota1,currentWeek,feedback);
           if (nota!= null)
               MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Add", "Nota a fost adaugata cu succes!");
        }catch (Exception e){
            MessageAlert.showErrorMessage(null, e.getMessage(), mainAnchorPane);
        }
        dialogStage.close();

    }
}
