package service;



import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import validators.Student;
import validators.ValidationException;


public class EditStudentController {
    public AnchorPane mainAnchorPane;
    @FXML
    private TextField textFieldNume;
    @FXML
    private TextField textFieldGrupa;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldCadruDidactic;

    private Service service;
    Stage dialogStage;
    Student student;
    Long id;

    @FXML
    private void initialize() {
    }


    public void setService(Service service,  Stage stage, Student s) {
        this.service = service;
        this.dialogStage=stage;
        this.student=s;
        if (null != s) {
            setFields(s);
        }
    }

    @FXML
    public void handleSave(){
        try {
            String nume = textFieldNume.getText();
            Integer grupa = Integer.valueOf(textFieldGrupa.getText());
            String email = textFieldEmail.getText();
            String cadruDidactic = textFieldCadruDidactic.getText();
            Student m = new Student(nume, grupa, email, cadruDidactic);
            if (null == this.student)
                saveMessage(m);
            else
                updateMessage(m);
        }catch (Exception e){
            MessageAlert.showErrorMessage(null, e.getMessage(), mainAnchorPane);
        }
    }

    private void updateMessage(Student m)
    {
        try {
            Student s= this.service.updateStudent(id,m.getNume(),m.getGrupa(),m.getEmail(),m.getCadruDidactic());
            if (s!=null)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Modificare mesaj","Mesajul a fost modificat");
        }catch (ValidationException e) {
            MessageAlert.showErrorMessage(null, e.getMessage(), mainAnchorPane);
        }
        dialogStage.close();
    }


    private void saveMessage(Student m)
    {
        try {

            Student s = this.service.addStudent(m.getNume(),m.getGrupa(),m.getEmail(),m.getCadruDidactic());
            if (s==null)
                dialogStage.close();
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Slavare mesaj","Mesajul a fost salvat");
        } catch (ValidationException e) {
            MessageAlert.showErrorMessage(null,e.getMessage(), mainAnchorPane);
        }
        dialogStage.close();
    }

    private void clearFields() {
        textFieldNume.setText("");
        textFieldGrupa.setText("");
        textFieldEmail.setText("");
        textFieldCadruDidactic.setText("");
    }
    private void setFields(Student s)
    {
        textFieldNume.setText(s.getNume());
        textFieldGrupa.setText(String.valueOf(s.getGrupa()));
        textFieldEmail.setText(s.getEmail());
        textFieldCadruDidactic.setText(s.getCadruDidactic());
        id = s.getId();
    }

    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
}
