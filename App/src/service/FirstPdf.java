package service;

//import java.awt.*;
//import java.io.File;
//import java.io.FileNotFoundException;
//
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class FirstPdf {

    public static void main(String[] args) throws IOException {
//        File file = new File("/Users/george/Desktop/1.pdf");
//
//        PdfWriter writer = null;
//        try {
//            writer = new PdfWriter(file);
//            System.out.println(file.getName());
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        PdfDocument pdf = new PdfDocument(writer);
//        Document document = new Document(pdf);
//        document.add(new Paragraph("sal"));
//        document.close();
        //Creating PDF document object
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(25, 750);
        contentStream.showText("Studenti");
        contentStream.newLine();
        contentStream.showText("Studentttti");
        contentStream.newLine();


        contentStream.endText();
        contentStream.close();
        //Saving the document
        document.save("/Users/george/Desktop/my_doc.pdf");

        System.out.println("PDF created");

        //Closing the document
        document.close();
        }

    }
