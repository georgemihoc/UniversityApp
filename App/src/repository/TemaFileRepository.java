package repository;


import com.company.ReadDatabase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import validators.*;
import validators.Tema;

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

public class TemaFileRepository extends InMemoryRepository<Long, Tema> {

    private String xmlFilePath;

    private void loadDataXML(){
        try {

            File fXmlFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("tema");


            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    String descriere =  eElement.getElementsByTagName("descriere").item(0).getTextContent();
                    int startweek = Integer.parseInt(eElement.getElementsByTagName("startweek").item(0).getTextContent());
                    int deadlineweek = Integer.parseInt(eElement.getElementsByTagName("deadlineweek").item(0).getTextContent());
                    Tema tema= new Tema(descriere,startweek,deadlineweek);
                    tema.setId(findID());
                    super.save(tema);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataDatabase() {
        ReadDatabase pd = new ReadDatabase();
        for (Tema t:
                pd.getTeme()) {
            t.setId(findID());
            super.save(t);
        }
    }

    private void writeDatabase(){
        ReadDatabase pd = new ReadDatabase();
        for (Tema t: findAll()
        ) {
            pd.addTema(t);
//            System.out.println("Tun");
        }
    }

    private void rewriteFileXML(){
//        String xmlFilePath = "/Users/george/Documents/UBB/Anul 2/MAP/seminar4/src/tema.xml";

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("class");
            document.appendChild(root);
            for (Tema t:findAll()) {

                // employee element
                Element employee = document.createElement("tema");

                root.appendChild(employee);

                // firstname element
                Element descriere = document.createElement("descriere");
                descriere.appendChild(document.createTextNode(t.getDescriere()));
                employee.appendChild(descriere);

                // lastname element
                Element startweek = document.createElement("startweek");
                startweek.appendChild(document.createTextNode(String.valueOf(t.getStartWeek())));
                employee.appendChild(startweek);

                // email element
                Element deadlineweek = document.createElement("deadlineweek");
                deadlineweek.appendChild(document.createTextNode(String.valueOf(t.getDeadlineWeek())));
                employee.appendChild(deadlineweek);
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
    public Tema save(Tema entity) throws ValidationException {
//        ReadDatabase pd = new ReadDatabase();
        Tema s = super.save(entity);
        if(s==null) {
            rewriteFileXML();
//            pd.emptyTable("teme");
//            writeDatabase();
        }
        return s;
    }

    @Override
    public Tema delete(Long id) {
//        ReadDatabase pd = new ReadDatabase();
        Tema s =super.delete(id);
        rewriteFileXML();
//        pd.emptyTable("teme");
//        writeDatabase();
        return s;
    }

    @Override
    public Tema update(Tema entity) {
//        ReadDatabase pd = new ReadDatabase();
        Tema s=  super.update(entity);
        rewriteFileXML();
//        pd.emptyTable("teme");
//        writeDatabase();
        return s;
    }

    public TemaFileRepository(Validator<Tema> validator,String fileTeme){//, String fileN) {
        super(validator);
        this.xmlFilePath = fileTeme;
        loadDataXML();
//        loadDataDatabase();
    }
}