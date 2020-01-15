package service;

import anUniversitar.AnUniversitar;
import configuration.ApplicationContext;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import utils.events.ChangeEvent;
import utils.observer.Observer;
import validators.Nota;
import validators.NotaDto;
import validators.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import validators.Tema;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainViewController implements Observer<ChangeEvent>  {
    public Label numeStudentText;
    public Label temaText;
    public Label notaText;
    public Label feedbackText;
    public Button buttonAddStudent;
    public Button buttonUpdateStudent;
    public Button buttonDeleteStudent;
    public Button buttonDeleteNota;
    public Button buttonDeleteTema;
    public Button buttonAddTema;
    public Button buttonUpdateTema;
    public Button b1;
    public Button b3;
    public Button b5;
    public Button b7;
    public Label text2;
    public Button b2;
    public Button b4;
    public Button b6;
    public Button b8;
    public Label text3;
    public Label text4;
    public Label text5;
    public Separator separator1;
    public Label text1;
    public Separator separator2;
    public Separator separator3;
    public Separator separator4;
    public Label titluNote;
    public Label titluTeme;
    public Label titluStudenti;
    public AnchorPane mainAnchorPane;
//    public AnchorPane studentAnchorPane;
    Service service;
    ServiceNota serviceNota;
    ObservableList<Student> model = FXCollections.observableArrayList();
    ObservableList<NotaDto> modelNote = FXCollections.observableArrayList();
    ObservableList<Tema> modelTeme = FXCollections.observableArrayList();
    ObservableList<Tema> modelComboBox = FXCollections.observableArrayList();
    ObservableList<Integer> modelComboBoxMotivare = FXCollections.observableArrayList(0,1, 2, 3, 4, 5,6,7,8,9,10,11,12,13,14);

    @FXML
    TableView<Student> tableView;
    @FXML
    TableColumn<Student,String> tableColumnNume;
    @FXML
    TableColumn<Student,String> tableColumnGrupa;
    @FXML
    TableColumn<Student,String> tableColumnEmail;
    @FXML
    TableColumn<Student,String> tableColumnCadruDidactic;

    @FXML
    Pagination pagination;


    @FXML
    TableView<NotaDto> tableViewNote;
    @FXML
    TableColumn<NotaDto,String> tableColumnStudent;
    @FXML
    TableColumn<NotaDto,String> tableColumnTema;
    @FXML
    TableColumn<NotaDto,String> tableColumnNota;
    @FXML
    TableColumn<NotaDto,String> tableColumnSaptamana;

    @FXML
    TableView<Tema> tableViewTeme;
    @FXML
    TableColumn<Tema,String> tableColumnDescriere;
    @FXML
    TableColumn<Tema,String> tableColumnStartWeek;
    @FXML
    TableColumn<Tema,String> tableColumnDeadlineWeek;
//    @FXML
//    TableColumn<NotaDto,String> tableColumnProfesor;
//    @FXML
//    TableColumn<NotaDto,String> tableColumnFeedback;

    @FXML
    TextField textFieldStudent;
    @FXML
    ComboBox<Tema> comboBox;
    @FXML
    TextField textFieldNota;
    @FXML
    TextArea textFieldFeedback;

    @FXML
    CheckBox checkBoxMotivare;
    @FXML
    CheckBox checkBoxIntarziere;

    @FXML
    ComboBox comboBoxMotivare;

    public Button buttonAddNota;


    List<Student> studentList = new ArrayList<Student>();
    private Stage dialogStage;
    private Stage stage;
    private String user;

    public void setStudentService(Service service, ServiceNota serviceNota, String user, Stage dialogStage, Stage stage) {
        this.service = service;
        this.serviceNota = serviceNota;
        this.dialogStage = dialogStage;
        this.stage = stage;
        this.user = user;
        service.addObserver(this);
        serviceNota.addObserver(this);
//        initModel();
//        initModelNote();
//        initModelTeme();
        update(null);

        initComboBox();

//        if(!user.equals("admin") && !user.equals("profesor"))
        if(!user.equals("profesor"))
        {
//            textFieldStudent.setEditable(false);
//            comboBox.setEditable(false);
//            textFieldNota.setEditable(false);
//            textFieldFeedback.setEditable(false);
//            comboBoxMotivare.setEditable(false);
//            comboBox.setItems(null);
//            comboBoxMotivare.setItems(null);
            buttonAddNota.setVisible(false);
            textFieldStudent.setVisible(false);
            textFieldFeedback.setVisible(false);
            textFieldNota.setVisible(false);
            comboBoxMotivare.setVisible(false);
            comboBox.setVisible(false);
            checkBoxIntarziere.setVisible(false);
            checkBoxMotivare.setVisible(false);
            numeStudentText.setVisible(false);
            feedbackText.setVisible(false);
            notaText.setVisible(false);
            temaText.setVisible(false);
            buttonDeleteTema.setVisible(false);
            buttonDeleteNota.setVisible(false);
            buttonAddTema.setVisible(false);
            buttonUpdateTema.setVisible(false);
            
            
            text1.setVisible(false);
            text2.setVisible(false);
            text3.setVisible(false);
            text4.setVisible(false);
            text5.setVisible(false);
            separator1.setVisible(false);
            separator2.setVisible(false);
            separator3.setVisible(false);
            separator4.setVisible(false);
            b1.setVisible(false);
            b2.setVisible(false);
            b3.setVisible(false);
            b4.setVisible(false);
            b5.setVisible(false);
            b6.setVisible(false);
            b7.setVisible(false);
            b8.setVisible(false);
        }
        if(!user.equals("admin"))
        {
            buttonAddStudent.setVisible(false);
            buttonDeleteStudent.setVisible(false);
            buttonUpdateStudent.setVisible(false);
        }
        else{
            tableViewTeme.setVisible(false);
            tableViewNote.setVisible(false);
            titluNote.setVisible(false);
            titluTeme.setVisible(false);
        }
        if(!(user.equals("admin")  || user.equals("profesor")))
        {
            tableView.setVisible(false);
            titluStudenti.setVisible(false);
            pagination.setVisible(false);
            tableViewNote.setLayoutX(5);
            tableViewNote.setLayoutY(55);
            titluNote.setLayoutX(4);
            titluNote.setLayoutY(29);
        }
    }


    @FXML
    public void initialize() {
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Student, String>("nume"));
        tableColumnGrupa.setCellValueFactory(new PropertyValueFactory<Student, String>("grupa"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        tableColumnCadruDidactic.setCellValueFactory(new PropertyValueFactory<Student, String>("cadruDidactic"));
        tableView.setItems(model);

        tableColumnStudent.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        tableColumnTema.setCellValueFactory(new PropertyValueFactory<>("temaName"));
        tableColumnNota.setCellValueFactory(new PropertyValueFactory<>("nota"));
        tableColumnSaptamana.setCellValueFactory(new PropertyValueFactory<>("date"));
//        tableColumnProfesor.setCellValueFactory(new PropertyValueFactory<>("profesor"));
//        tableColumnFeedback.setCellValueFactory(new PropertyValueFactory<>("feedback"));
        tableViewNote.setItems(modelNote);

        textFieldStudent.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                handleFilter();
            }
        });

        suggestStudent();

        tableColumnDescriere.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        tableColumnStartWeek.setCellValueFactory(new PropertyValueFactory<>("startWeek"));
        tableColumnDeadlineWeek.setCellValueFactory(new PropertyValueFactory<>("deadlineWeek"));
        tableViewTeme.setItems(modelTeme);

        comboBox.setItems(modelComboBox);
        comboBoxMotivare.setItems(modelComboBoxMotivare);
    }

    private void suggestStudent() {
        TextFields.bindAutoCompletion(textFieldStudent,StreamSupport.stream(Service.findAllStudents().spliterator(), false)
                .map(Student::getNume)
                .collect(Collectors.toList()));
    }

    private void handleFilter() {

        if(textFieldStudent.getText().isEmpty())
            modelNote.setAll(getNotaDTOList());
        else {
            try {
                String output = textFieldStudent.getText().substring(0, 1).toUpperCase() + textFieldStudent.getText().substring(1);
                Predicate<NotaDto> byName = n -> n.getStudentName().contains(output);
//                Predicate<NotaDto> byName = n -> n.getStudentName().contains(textFieldStudent.getText());
//        Predicate<NotaDto> byTema = n -> n.getTemaName().contains(comboBox.getValue().getDescriere());
                List<NotaDto> list = getNotaDTOList();
                List<NotaDto> listaFiltrata = list.stream()
//                .filter(byName.and(byTema))
                        .filter(byName)
                        .collect(Collectors.toList());
                modelNote.setAll(listaFiltrata);
//                textFieldStudent.setText(listaFiltrata.get(0).getStudentName());

            } catch (Exception ignore) {
            }
        }
    }

    private List<NotaDto> getNotaDTOList() {
        Iterable<Nota> note = serviceNota.findAllNota();
        List<NotaDto> noteList = StreamSupport.stream(note.spliterator(), false)
                .map(nota->new NotaDto(service.findStudent(nota.getIdStudent()).getNume(),service.findTema(nota.getIdTema()).getDescriere(),nota.getDate(),nota.getNota()))
                .collect(Collectors.toList());
        return noteList;
    }

    private void initModel() {
        Iterable<Student> students = service.findAllStudents();
        List<Student> studentList = StreamSupport.stream(students.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(studentList);

//        int fromIndex = pageIndex * 2;
//        int toIndex = Math.min(fromIndex + 2, studentList.size());
//        model.setAll(FXCollections.observableArrayList(studentList.subList(fromIndex, toIndex)));
    }
    private Node createPage(int pageIndex) {

        pagination.setPageCount(studentList.size() / 2);

        int fromIndex = pageIndex * 2;
        int toIndex = Math.min(fromIndex + 2, studentList.size());
        model.setAll(FXCollections.observableArrayList(studentList.subList(fromIndex, toIndex)));
        return new AnchorPane(tableView);
//        return tableView;
    }

    private void initModelNote() {
        if(this.user.equals("admin") || this.user.equals("profesor") || this.user.equals(null)) {
            Iterable<Nota> note = serviceNota.findAllNota();
            try {
                List<NotaDto> noteList = StreamSupport.stream(note.spliterator(), false)
                        .map(nota -> new NotaDto(service.findStudent(nota.getIdStudent()).getNume(), service.findTema(nota.getIdTema()).getDescriere(), nota.getDate(), nota.getNota()))
                        .collect(Collectors.toList());
                modelNote.setAll(noteList);
            } catch (Exception ignore) {
            }
        }
        else {
            Iterable<Nota> note = serviceNota.findAllNota();
            try {
                List<NotaDto> noteList = StreamSupport.stream(note.spliterator(), false)
                        .filter(x-> this.user.toUpperCase().equals(service.findStudent(x.getIdStudent()).getNume().toUpperCase()))
                        .map(nota -> new NotaDto(service.findStudent(nota.getIdStudent()).getNume(), service.findTema(nota.getIdTema()).getDescriere(), nota.getDate(), nota.getNota()))
//                        .filter(x-> this.user.toUpperCase().contains(x.getStudentName().toUpperCase()))
                        .collect(Collectors.toList());
                modelNote.setAll(noteList);
            } catch (Exception ignore) {
            }
        }

    }
    private void initModelTeme() {
        Iterable<Tema> note = service.findAllTema();
        List<Tema> temeList = StreamSupport.stream(note.spliterator(), false)
                .collect(Collectors.toList());
        modelTeme.setAll(temeList);
    }
    private void initComboBox() {
        Iterable<Tema> teme = service.findAllTema();
        List<Tema> temaList = StreamSupport.stream(teme.spliterator(), false)
                .collect(Collectors.toList());
        modelComboBox.setAll(temaList);
        for (Tema t:
             teme) {
            if(t.getDeadlineWeek()>=AnUniversitar.getCurrentWeek() && t.getStartWeek()<=AnUniversitar.getCurrentWeek())
                comboBox.getSelectionModel().select(t);
        }
    }

    public void handleDeleteStudent(ActionEvent actionEvent) {
        Student selected = (Student) tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Student deleted = service.deleteStudent(selected.getId());
            if (null != deleted)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Studentul a fost sters cu succes!");
        } else MessageAlert.showErrorMessage(null, "Nu ati selectat nici un student!",mainAnchorPane);
//        update();
    }

    public void showStudentEditDialog(Student student) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            String editView = ApplicationContext.getPROPERTIES().getProperty("view.add");
            loader.setLocation(getClass().getResource(editView));

            AnchorPane root = (AnchorPane) loader.load();
//            studentAnchorPane = root;
            if(!mainAnchorPane.getStylesheets().isEmpty()) {
                root.getStylesheets().add(getClass().getResource("/darkmode.css").toExternalForm());
//            studentAnchorPane.getStylesheets().add(getClass().getResource("/darkmode.css").toExternalForm());
            }
            else {
                root.getStylesheets().remove(getClass().getResource("/darkmode.css").toExternalForm());
//            studentAnchorPane.getStylesheets().remove(getClass().getResource("/darkmode.css").toExternalForm());
            }
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Message");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditStudentController editStudentController = loader.getController();
            editStudentController.setService(service, dialogStage, student);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showTemaEditDialog(Tema tema) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            String editView = ApplicationContext.getPROPERTIES().getProperty("view.addTema");
            loader.setLocation(getClass().getResource(editView));

            AnchorPane root = (AnchorPane) loader.load();

            if(!mainAnchorPane.getStylesheets().isEmpty()) {
                root.getStylesheets().add(getClass().getResource("/darkmode.css").toExternalForm());
//            studentAnchorPane.getStylesheets().add(getClass().getResource("/darkmode.css").toExternalForm());
            }
            else {
                root.getStylesheets().remove(getClass().getResource("/darkmode.css").toExternalForm());
//            studentAnchorPane.getStylesheets().remove(getClass().getResource("/darkmode.css").toExternalForm());
            }

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Message");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditTemaController editTemaController = loader.getController();
            editTemaController.setService(service, dialogStage, tema);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAddStudent(ActionEvent ev) {
        showStudentEditDialog(null);
    }
    @FXML
    public void handleUpdateStudent(ActionEvent ev) {
        Student selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showStudentEditDialog(selected);
        }
        else
            MessageAlert.showErrorMessage(null, "NU ati selectat nici un student", mainAnchorPane);
    }

    public void handleAddTema(ActionEvent actionEvent) {
        showTemaEditDialog(null);
    }

    public void handleUpdateTema(ActionEvent actionEvent) {
        Tema selected = tableViewTeme.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showTemaEditDialog(selected);
        }
        else
            MessageAlert.showErrorMessage(null, "NU ati selectat nicio tema", mainAnchorPane);
    }

    @Override
    public void update(ChangeEvent changeEvent) {
        getNumberOfPages();
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                Pageable page = PageRequest.of(pageIndex+1, 9);
                Iterable<Student> allProducts = service.findPage(page);
                studentList = StreamSupport.stream(allProducts.spliterator(), false)
                        .collect(Collectors.toList());

                model.setAll(FXCollections.observableArrayList(studentList));
                pagination.currentPageIndexProperty().setValue(pageIndex);
                return new AnchorPane(tableView);
            }
        });
        pagination.setLayoutX(0);
        pagination.setLayoutY(0);
        initModelTeme();
        initModelNote();
        suggestStudent();
    }

    private void getNumberOfPages() {
        Iterable<Student> st = service.findAllStudents();
        double nr = st.spliterator().getExactSizeIfKnown() / 9.0;
//                System.out.println(nr);
        if(studentList.size() / 9 == nr)
            pagination.setPageCount((int) (st.spliterator().getExactSizeIfKnown() / 9));
        else
            pagination.setPageCount((int) (st.spliterator().getExactSizeIfKnown() / 9+1));

    }

    public void handleAddNota(ActionEvent actionEvent) {
        if(comboBoxMotivare.getValue()!=null && !comboBoxMotivare.getValue().equals(0) && !checkBoxMotivare.isSelected()|| checkBoxMotivare.isSelected() && comboBoxMotivare.getValue()==null)
            MessageAlert.showErrorMessage(null, "Nu ati selectat perioada de motivare sau optiunea de motivare", mainAnchorPane);
        else {
            try {
                showConfirmNotaDialog(new NotaDto(textFieldStudent.getText(), comboBox.getValue().getDescriere(), AnUniversitar.getCurrentWeek(), Float.valueOf(textFieldNota.getText())),
                        textFieldFeedback.getText(),checkBoxMotivare.isSelected(),checkBoxIntarziere.isSelected(), (Integer) comboBoxMotivare.getValue());
            }
            catch (Exception ignore){
                MessageAlert.showErrorMessage(null, "Nu ati introdus nota sau este introdusa gresit", mainAnchorPane);
            }
        }
//        try {
//            Long idStudent = service.findIdStudent(textFieldStudent.getText());
//            Long idTema = service.findIdTema(comboBox.getValue().getDescriere());
//            Float nota1 = Float.valueOf(textFieldNota.getText());
//            int currentWeek = AnUniversitar.getCurrentWeek();
//            String feedback = textFieldFeedback.getText();
//            Nota nota = serviceNota.addNota(idStudent,idTema,nota1,currentWeek,feedback);
//           if (nota!= null)
//               MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Add", "Nota a fost adaugata cu succes!");
//        }catch (Exception e){
//            MessageAlert.showErrorMessage(null, e.getMessage());
//        }
    }

    private void showConfirmNotaDialog(NotaDto nota, String feedback, Boolean motivare, Boolean intarziere, Integer value) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            String confirmView = ApplicationContext.getPROPERTIES().getProperty("view.confirm");

            loader.setLocation(getClass().getResource(confirmView));

            AnchorPane root = (AnchorPane) loader.load();

            if(!mainAnchorPane.getStylesheets().isEmpty()) {
                root.getStylesheets().add(getClass().getResource("/darkmode.css").toExternalForm());
//            studentAnchorPane.getStylesheets().add(getClass().getResource("/darkmode.css").toExternalForm());
            }
            else {
                root.getStylesheets().remove(getClass().getResource("/darkmode.css").toExternalForm());
//            studentAnchorPane.getStylesheets().remove(getClass().getResource("/darkmode.css").toExternalForm());
            }

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Confirm Nota");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            ConfirmNotaController confirmNotaController = loader.getController();
            confirmNotaController.setService(service,serviceNota, dialogStage, nota,feedback,motivare,intarziere,value);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRaport1(ActionEvent actionEvent) throws IOException {
        showRaportTablePage(1);
    }
    public void handleRaport2(ActionEvent actionEvent) throws IOException {
        tableViewTeme.getSelectionModel().select(service.raport2());
    }
    public void handleRaport3(ActionEvent actionEvent) {
        showRaportTablePage(3);
    }
    public void handleRaport4(ActionEvent actionEvent) {
        showRaportTablePage(4);
    }
    private void showRaportTablePage(int raport) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            String confirmView = ApplicationContext.getPROPERTIES().getProperty("view.raportTable");

            loader.setLocation(getClass().getResource(confirmView));

            AnchorPane root = (AnchorPane) loader.load();

            if(!mainAnchorPane.getStylesheets().isEmpty()) {
                root.getStylesheets().add(getClass().getResource("/darkmode.css").toExternalForm());
//            studentAnchorPane.getStylesheets().add(getClass().getResource("/darkmode.css").toExternalForm());
            }
            else {
                root.getStylesheets().remove(getClass().getResource("/darkmode.css").toExternalForm());
//            studentAnchorPane.getStylesheets().remove(getClass().getResource("/darkmode.css").toExternalForm());
            }

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Raport");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            RaportTableViewController raportTableViewController = loader.getController();
            raportTableViewController.setService(service,serviceNota, raport);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRaport1PieChart(ActionEvent actionEvent) {
        showPieChartPage(1);
    }
    public void handleRaport2PieChart(ActionEvent actionEvent) {
        showPieChartPage(2);
    }

    public void handleRaport3PieChart(ActionEvent actionEvent) {
        showPieChartPage(3);
    }

    public void handleRaport4PieChart(ActionEvent actionEvent) {
        showPieChartPage(4);
    }

    private void showPieChartPage(int raport) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            String pieChartView = ApplicationContext.getPROPERTIES().getProperty("view.pieChart");

            loader.setLocation(getClass().getResource(pieChartView));

            AnchorPane root = (AnchorPane) loader.load();

            if(!mainAnchorPane.getStylesheets().isEmpty()) {
                root.getStylesheets().add(getClass().getResource("/darkmode.css").toExternalForm());
//            studentAnchorPane.getStylesheets().add(getClass().getResource("/darkmode.css").toExternalForm());
            }
            else {
                root.getStylesheets().remove(getClass().getResource("/darkmode.css").toExternalForm());
//            studentAnchorPane.getStylesheets().remove(getClass().getResource("/darkmode.css").toExternalForm());
            }

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Pie Chart");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setMaximized(true);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            PieChartViewController pieChartViewController = loader.getController();
            pieChartViewController.setService(service,serviceNota,raport);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLogout(ActionEvent actionEvent) {
        dialogStage.close();
        stage.show();
    }

    public void handleDeleteNota(ActionEvent actionEvent) {
        NotaDto selected = (NotaDto) tableViewNote.getSelectionModel().getSelectedItem();
        if (selected != null) {
//            Student deleted = service.deleteStudent(selected.getId());
            Nota deleted = serviceNota.deleteNota(service.findIdStudent(selected.getStudentName()),service.findIdTema(selected.getTemaName()));
            if (null != deleted)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Nota a fost stearsa cu succes!");
        } else MessageAlert.showErrorMessage(null, "Nu ati selectat nicio nota!", mainAnchorPane);
//        update();
    }

    public void handleDeleteTema(ActionEvent actionEvent) {
        Tema selected = (Tema) tableViewTeme.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Tema deleted = service.deleteTema(selected.getId());
            if (null != deleted)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Delete", "Tema a fost stearsa cu succes!");
        } else MessageAlert.showErrorMessage(null, "Nu ati selectat nicio tema!", mainAnchorPane);
//        update();
    }

    public void handleDarkMode(ActionEvent actionEvent) {
        if(mainAnchorPane.getStylesheets().isEmpty()) {
            mainAnchorPane.getStylesheets().add(getClass().getResource("/darkmode.css").toExternalForm());
        }
        else {
            mainAnchorPane.getStylesheets().remove(getClass().getResource("/darkmode.css").toExternalForm());
        }
    }
}
