package com.rampit.rask3.dailydiary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

//Updated on 25/01/2022 by RAMPIT
//Used to send email

//Class is extending AsyncTask because this class is going to perform a networking operation
public class SendMail_noprogress extends AsyncTask<Void,Void,Void> {

    //Declaring Variables
    private Context context;
    private Session session;

    //Information to send email
    private String email;
    private String subject;
    private String message;
    private String pdffile;
    private String pdfname;
    private SharedPreferences pref;

    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;

    //Class Constructor
    public SendMail_noprogress(Context context, String email, String subject, String message, String pdfFile , String pdfname){
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.pdffile = pdfFile;
        this.pdfname = pdfname;
        pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context,"Sending Report","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        //Showing a success message
        Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        String ii1 = pref.getString("NAME","");
        Properties props = new Properties();
        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.rampit.in");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                    }
                });
        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);

            //Setting sender address
            mm.setFrom(new InternetAddress(Config.EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject(subject+"~~"+ii1+"~~"+message);
            //Adding message
            mm.setText(message);
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            String file = String.valueOf(pdffile);
            Log.d("pdfffile",file);
            String fileName = pdfname;
//            javax.sql.DataSource
            FileDataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler((FileDataSource) source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
            mm.setContent(multipart);
//            MimeBodyPart pdfAttachment = new MimeBodyPart();
//            pdfAttachment.attachFile(pdffile);

            //Sending email
            Transport.send(mm);

        } catch (MessagingException e) {
            e.printStackTrace();
            Log.d("pdfffile","psdf");
        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
        return null;
    }
}
