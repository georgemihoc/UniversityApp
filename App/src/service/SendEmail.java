package service;

import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail extends Thread
{
    private Thread t;
    private String threadName;
    private String sender;
    private String recipient;
    private String subject;
    private String message;
    private String Bcc;

    public SendEmail(String name,String sender,String recipient, String subject, String message, String Bcc) {
        threadName = name;
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.Bcc = Bcc;
        System.out.println("Creating " +  threadName );
    }

    public void run()
    {
        System.out.println ("Thread " +
                Thread.currentThread().getId() +
                " is running");
        try {

            InternetAddress[] distributionList = InternetAddress.parse(recipient,false);

            Properties props = new Properties();
            props.put("mail.smtp.host", "localhost");
            props.put("mail.smtp.port", "25");
            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(false);

            Message msg = new MimeMessage(session);
            msg.setContent(message, "text/html; charset=utf-8");
            msg.setFrom(new InternetAddress(sender));
            msg.setRecipients(Message.RecipientType.TO, distributionList);
            InternetAddress[] myBccList;
            if(Bcc!=null) {
                myBccList = InternetAddress.parse(Bcc);
                msg.setRecipients(Message.RecipientType.BCC, myBccList);
            }
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            Transport.send(msg);

//            t.interrupt();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}

// Main Class
//class Multithread
//{
//    public static void main(String[] args)
//    {
//        int n = 8; // Number of threads
//        for (int i=0; i<8; i++)
//        {
//            MultithreadingDemo object = new MultithreadingDemo();
//            object.start();
//        }
//    }
//}
//
//public class SendEmail {
//    public static   void sendEmailToGmailAccount() {
//        try {
//
//            InternetAddress[] distributionList = InternetAddress.parse("javahonk@gmail.com",false);
//            String from = "javahonk@gmail.com";
//            String subject = "Test email";
//
//            Properties props = new Properties();
//            props.put("mail.smtp.host", "localhost");
//            props.put("mail.smtp.port", "25");
//            Session session = Session.getDefaultInstance(props, null);
//            session.setDebug(false);
//
//            Message msg = new MimeMessage(session);
//            String message = "bosssss";
//            msg.setContent(message, "text/html; charset=utf-8");
//            msg.setFrom(new InternetAddress(from));
//            msg.setRecipients(Message.RecipientType.TO, distributionList);
//            msg.setSubject(subject);
//            msg.setSentDate(new Date());
//            Transport.send(msg);
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
