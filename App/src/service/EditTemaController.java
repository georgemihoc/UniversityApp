package service;



import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import validators.Tema;
import validators.ValidationException;


public class EditTemaController {
    public AnchorPane mainAnchorPane;
    @FXML
    private TextField textFieldDescriere;
    @FXML
    private TextField textFieldStartWeek;
    @FXML
    private TextField textFieldDeadlineWeek;

    private Service service;
    Stage dialogStage;
    Tema tema;
    Long id;

    @FXML
    private void initialize() {
    }


    public void setService(Service service,  Stage stage, Tema t) {
        this.service = service;
        this.dialogStage=stage;
        this.tema=t;
        if (null != t) {
            setFields(t);
        }
    }

    @FXML
    public void handleSave(){
        try {
            String descriere = textFieldDescriere.getText();
            Integer startWeek = Integer.valueOf(textFieldStartWeek.getText());
            Integer deadlineWeek = Integer.valueOf(textFieldDeadlineWeek.getText());
            Tema t = new Tema(descriere, startWeek, deadlineWeek);
            if (null == this.tema)
                saveMessage(t);
            else
                updateMessage(t);
        }catch (Exception e){
            MessageAlert.showErrorMessage(null, e.getMessage(), mainAnchorPane);
        }
    }

    private void updateMessage(Tema t)
    {
        try {
            Tema s= this.service.updateTema(id,t.getDescriere(),t.getStartWeek(),t.getDeadlineWeek());
            if (s!=null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Modificare tema","Tema a fost modificata");
        }catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage(), mainAnchorPane);
        }
        dialogStage.close();
    }


    private void saveMessage(Tema t)
    {
        try {

            Tema s = this.service.addTema(t.getDescriere(),t.getStartWeek(),t.getDeadlineWeek());
            if (s==null)
                dialogStage.close();
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Slavare mesaj","Mesajul a fost salvat");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage(), mainAnchorPane);
        }
        dialogStage.close();
    }

    private void clearFields() {
        textFieldDescriere.setText("");
        textFieldStartWeek.setText("");
        textFieldDeadlineWeek.setText("");
    }
    private void setFields(Tema t)
    {
        textFieldDescriere.setText(t.getDescriere());
        textFieldStartWeek.setText(String.valueOf(t.getStartWeek()));
        textFieldDeadlineWeek.setText(String.valueOf(t.getDeadlineWeek()));
        id = t.getId();
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
