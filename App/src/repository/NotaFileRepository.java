package repository;


import com.company.ReadDatabase;
import configuration.Config;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import validators.*;
import validators.Nota;

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
import java.time.LocalDateTime;

public class NotaFileRepository extends InMemoryRepository<Pair, Nota> {

    private String xmlFilePath;

    private void loadDataXML(){
        try {
//            String xmlFilePath = "/Users/george/Documents/UBB/Anul 2/MAP/seminar4/src/note.xml";
            File fXmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("nota");


            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    long idStudent = Long.parseLong(eElement.getElementsByTagName("idStudent").item(0).getTextContent());
                    long idTema = Long.parseLong(eElement.getElementsByTagName("idTema").item(0).getTextContent());
                    float nota = Float.parseFloat(eElement.getElementsByTagName("nota1").item(0).getTextContent());
                    int week = Integer.parseInt(eElement.getElementsByTagName("week").item(0).getTextContent());
                    String profesor =  eElement.getElementsByTagName("profesor").item(0).getTextContent();
                    String feedback =  eElement.getElementsByTagName("feedback").item(0).getTextContent();
                    Nota nota1= new Nota(idStudent,idTema,week,nota,profesor,feedback);
                    nota1.setId(new Pair(idStudent,idTema));
                    super.save(nota1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void loadDataDatabase() {
        ReadDatabase pd = new ReadDatabase();
        for (Nota n:
                pd.getNote()) {
            n.setId(new Pair(n.getIdStudent(),n.getIdTema()));
            super.save(n);
        }
    }

    private void writeDatabase(){
        ReadDatabase pd = new ReadDatabase();
        for (Nota n: findAll()
        ) {
            pd.addNote(n);
//            System.out.println("Tun");
        }
    }

    private void rewriteFileXML(){
//        String xmlFilePath = "/Users/george/Documents/UBB/Anul 2/MAP/seminar4/src/note.xml";

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("class");
            document.appendChild(root);
            for (Nota n:findAll()) {

                // employee element
                Element employee = document.createElement("nota");

                root.appendChild(employee);

                // firstname element
                Element idStudent = document.createElement("idStudent");
                idStudent.appendChild(document.createTextNode(String.valueOf(n.getIdStudent())));
                employee.appendChild(idStudent);

                // lastname element
                Element idTema = document.createElement("idTema");
                idTema.appendChild(document.createTextNode(String.valueOf(n.getIdTema())));
                employee.appendChild(idTema);

                // email element
                Element nota = document.createElement("nota1");
                nota.appendChild(document.createTextNode(String.valueOf(n.getNota())));
                employee.appendChild(nota);

                // email element
                Element week = document.createElement("week");
                week.appendChild(document.createTextNode(String.valueOf(n.getDate())));
                employee.appendChild(week);

                // email element
                Element profesor = document.createElement("profesor");
                profesor.appendChild(document.createTextNode(n.getProfesor()));
                employee.appendChild(profesor);

                // email element
                Element feedback = document.createElement("feedback");
                feedback.appendChild(document.createTextNode(n.getFeedback()));
                employee.appendChild(feedback);
            }

            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    @Override
    public Nota save(Nota entity) throws ValidationException {

        ReadDatabase pd = new ReadDatabase();

        Nota s = super.save(entity);

        if(s==null) {
            rewriteFileXML();
//            pd.emptyTable("note");
//            writeDatabase();
        }

        return entity;
    }

    @Override
    public Nota delete(Pair pair) {
        ReadDatabase pd = new ReadDatabase();
        Nota s =super.delete(pair);
        rewriteFileXML();
//        pd.emptyTable("note");
//        writeDatabase();
        return s;
    }

    @Override
    public Nota update(Nota entity) {
        ReadDatabase pd = new ReadDatabase();
        Nota s=  super.update(entity);
        rewriteFileXML();
        pd.emptyTable("note");
        writeDatabase();
        return s;
    }

    public NotaFileRepository(Validator<Nota> validator,String fileNote){//, String fileN) {
        super(validator);
        this.xmlFilePath = fileNote;
        loadDataXML();
        //loadDataDatabase();
    }
}