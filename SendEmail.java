
package inventory_system;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/***************************************************
 * This program send e-mail using authentication   * 
 * @author QXF3984                                 *
 * Date: 29-08-2013 11:07am                        *
 ***************************************************/

public class SendEmail
{
   private String sender;
   private String recipient;
   private String cc;
   private String subject;
   private String body;
   
   /**
    * Sets all fields required for an email 
    * @param recipient
    * @param sender
    * @param cc
    * @param body
    * @param subject 
    */ 
   public SendEmail(String recipient, String sender,String cc,String body,String subject)
   {
     this.sender = sender;
     this.recipient = recipient;
     this.cc = cc;
     this.subject = subject;
     this.body = body;
   }
   
   public String sender(){ return sender; }
   public String recipient(){ return recipient; }
   public String cc(){ return cc; }
   public String subject(){ return subject; }
   public String body(){ return body; }
   
   public String Send()
   {    
       
      String host = "smtp.muc";
      Properties properties = System.getProperties();
      properties.setProperty("mail.smtp.host", host); // Setup mail server
      Session session = Session.getDefaultInstance(properties); // Get the default Session object.
      
      //check if sender set a cc
      if(cc == null || cc.equals("")) 
      {
            try{
                // Create a default MimeMessage object.
                 MimeMessage message = new MimeMessage(session);
                 message.setFrom(new InternetAddress(sender));                                  // Set From: header field of the header.
                 message.addRecipient(Message.RecipientType.TO,new InternetAddress(recipient)); // Set To: header field of the header.
                 message.setSubject(subject);                                                    // Set Subject: header field
                 message.setContent(body,"text/html; charset=utf-8");
                 //message.setText(body);

                  // Send message
                 Transport.send(message);
                 System.out.println("Sent message successfully....");
                 return "success";
               }
            catch (MessagingException mex) 
                 {
                     System.out.println(mex);
                     return mex.toString(); //mex.printStackTrace();
                 } 
      } 
      else
      {
             try{
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(sender));                                  // Set From: header field of the header.
                message.addRecipient(Message.RecipientType.CC,new InternetAddress(cc));        //set on copy
                //message.addRecipient(Messages.RecipientType.BCC,new InternetAddress(cc));       // set a blind copy
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(recipient)); // Set To: header field of the header.
                message.setSubject(subject);                                                    // Set Subject: header field
                message.setContent(body,"text/html; charset=utf-8");
                //message.setText(body); 

                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");
                return "success";
                }
            catch (MessagingException mex) 
                 {    
                      System.out.println(mex);
                      return mex.toString(); //mex.printStackTrace();
                 } 

      }
   }
}
