package service;

import configuration.ApplicationContext;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginViewController {

    public PasswordField passwordField;
    public TextField textFieldUsername;
    public Button buttonLogin;
    public AnchorPane mainAnchorPane;
    public ComboBox comboBox;
    private Service service;
    private ServiceNota serviceNota;
    Stage primaryStage;
    ObservableList<String> modelComboBox = FXCollections.observableArrayList("Dark","Normal");


    public void setService(Service service, ServiceNota serviceNota, Stage primaryStage) {
        this.service = service;
        this.serviceNota = serviceNota;
        this.primaryStage = primaryStage;
//        System.out.println(textFieldUsername.getText());
        comboBox.setItems(modelComboBox);
        comboBox.getSelectionModel().select(0);
    }


    public void handleLogin(ActionEvent actionEvent) throws Exception {
        if(textFieldUsername.getText().isEmpty() || passwordField.getText().isEmpty())
            MessageAlert.showErrorMessage(null, "Contul sau parola au fost introduse gresit", mainAnchorPane);
        else {
//            System.out.println(passwordField.getText());
            String user = checkItem(textFieldUsername.getText(),passwordField.getText());
            if(!user.equals("error"))
                launchMainView(user);
            else {
                MessageAlert.showErrorMessage(null, "Contul sau parola nu exista", mainAnchorPane);
                passwordField.selectAll();
            }

        }
    }

    private String checkItem(String textUsername, String textPassword) throws Exception{
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(textPassword.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            textPassword = hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        String loginItems = ApplicationContext.getPROPERTIES().getProperty("data.login");
        File fXmlFile = new File(loginItems);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();


        NodeList nList = doc.getElementsByTagName("student");
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);


            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                String username =  eElement.getElementsByTagName("username").item(0).getTextContent();
                String name =  eElement.getElementsByTagName("name").item(0).getTextContent();
                String password =  eElement.getElementsByTagName("password").item(0).getTextContent();

                if(username.equals(textUsername) && password.equals(textPassword))
                    return name;
            }
        }


        nList = doc.getElementsByTagName("admin");
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);


            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                String username =  eElement.getElementsByTagName("username").item(0).getTextContent();
                String password =  eElement.getElementsByTagName("password").item(0).getTextContent();

                if(username.equals(textUsername) && password.equals(textPassword))
                    return "admin";
            }
        }


        nList = doc.getElementsByTagName("profesor");
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);


            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                String username =  eElement.getElementsByTagName("username").item(0).getTextContent();
                String password =  eElement.getElementsByTagName("password").item(0).getTextContent();

                if(username.equals(textUsername) && password.equals(textPassword))
                    return "profesor";
            }
        }
        return "error";
    }

    private void launchMainView(String user) throws IOException {
        primaryStage.hide();
        FXMLLoader loader = new FXMLLoader();
        String pieChartView = ApplicationContext.getPROPERTIES().getProperty("view.main");

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
        dialogStage.initModality(Modality.WINDOW_MODAL);
//        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);

        MainViewController mainViewController = loader.getController();
        mainViewController.setStudentService(service,serviceNota,user,dialogStage,primaryStage);

        dialogStage.setWidth(800);
        dialogStage.show();
//        dialogStage.setResizable(false);

//        primaryStage.close();

//        FXMLLoader mainScene = new FXMLLoader();
//        String mainView = ApplicationContext.getPROPERTIES().getProperty("view.main");
//        mainScene.setLocation(getClass().getResource(mainView));
//
//        AnchorPane studentLayout = mainScene.load();
//        primaryStage.setScene(new Scene(studentLayout));
//
//        MainViewController mainViewController = mainScene.getController();
//        mainViewController.setStudentService(service,serviceNota,user,primaryStage);
//
//        primaryStage.setWidth(800);
//        primaryStage.show();
//        primaryStage.setResizable(false);

    }

    public void handleClear(){
        textFieldUsername.clear();
        passwordField.clear();
//        buttonLogin.setVisible(false);
    }

    public void enterKey(javafx.scene.input.KeyEvent keyEvent) throws Exception {
        if(keyEvent.getCode().toString().equals("ENTER"))
            handleLogin(null);
    }

    public void handleCancel(ActionEvent actionEvent) {
        primaryStage.close();
    }

    public void handleComboBox(ActionEvent actionEvent) {
        if(comboBox.getValue().equals("Dark")) {
            mainAnchorPane.getStylesheets().add(getClass().getResource("/darkmode.css").toExternalForm());
        }
        else {
            mainAnchorPane.getStylesheets().remove(getClass().getResource("/darkmode.css").toExternalForm());
        }
    }
}
