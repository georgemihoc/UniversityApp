package repository;


import com.company.ReadDatabase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import validators.Student;
import validators.ValidationException;
import validators.Validator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class StudentFileRepository extends InMemoryRepository<Long, Student> {
    private String xmlFilePath;
//    public List<Student> findAll(Pageable pageable);

    private void loadDataXML(){
        try {
//            String xmlFilePath = "/Users/george/Documents/UBB/Anul 2/MAP/seminar4/src/students.xml";
            File fXmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("student");


            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    String nume =  eElement.getElementsByTagName("nume").item(0).getTextContent();
                    int grupa = Integer.parseInt(eElement.getElementsByTagName("grupa").item(0).getTextContent());
                    String email =  eElement.getElementsByTagName("email").item(0).getTextContent();
                    String cadruDidactic =  eElement.getElementsByTagName("cadru_didactic").item(0).getTextContent();
                    Student student= new Student(nume,grupa,email,cadruDidactic);
                    student.setId(findID());
                    super.save(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataDatabase() {
        ReadDatabase pd = new ReadDatabase();
        for (Student s:
        pd.getStudents()) {
            s.setId(findID());
            super.save(s);
        }
    }

    private void writeDatabase(){
        ReadDatabase pd = new ReadDatabase();
        for (Student s: findAll()
             ) {
            pd.addStudents(s);
        }
    }

    private void rewriteFileXML(){
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("class");
            document.appendChild(root);
            for (Student s:findAll()) {

                // employee element
                Element employee = document.createElement("student");

                root.appendChild(employee);

                // firstname element
                Element firstName = document.createElement("nume");
                firstName.appendChild(document.createTextNode(s.getNume()));
                employee.appendChild(firstName);

                // lastname element
                Element lastname = document.createElement("grupa");
                lastname.appendChild(document.createTextNode(String.valueOf(s.getGrupa())));
                employee.appendChild(lastname);

                // email element
                Element email = document.createElement("email");
                email.appendChild(document.createTextNode(s.getEmail()));
                employee.appendChild(email);

                // department elements
                Element department = document.createElement("cadru_didactic");
                department.appendChild(document.createTextNode(s.getCadruDidactic()));
                employee.appendChild(department);
            }

            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
//            String xmlFilePath = "/Users/george/Documents/UBB/Anul 2/MAP/seminar4/src/students.xml";
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);


        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }
    @Override
    public Student save(Student entity) throws ValidationException {
        ReadDatabase pd = new ReadDatabase();
        Student s = super.save(entity);
        if(s==null) {
            rewriteFileXML();
            pd.emptyTable("studenti");
//            writeDatabase();
        }
            //writeToFile(entity);
        return s;
    }

    @Override
    public Student delete(Long id) {
        ReadDatabase pd = new ReadDatabase();
        Student s =super.delete(id);
        rewriteFileXML();
        pd.emptyTable("studenti");
//        writeDatabase();
        return s;
    }

    @Override
    public Student update(Student entity) {
        ReadDatabase pd = new ReadDatabase();
        Student s=  super.update(entity);
        rewriteFileXML();
        pd.emptyTable("studenti");
//        writeDatabase();
        return s;
    }

    public StudentFileRepository(Validator<Student> validator,String fileStudents){//, String fileN) {
        super(validator);
        this.xmlFilePath = fileStudents;
        loadDataXML();
//        loadDataDatabase();
    }

}