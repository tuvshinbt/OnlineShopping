package edu.mum.cs490.project.aop;

import edu.mum.cs490.project.utils.EmailUtil;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Tamir Ganbat 05/05/2018
 */
@Aspect
@Component
public class EmailAOPService {

    @Autowired
    private EmailUtil emailUtil;

    private static Properties propertiesOfMail = new Properties();
    private static int delay = 0;

    static{
        try {
            //load a properties file from class path, inside static method
            propertiesOfMail.load(EmailUtil.class.getClassLoader().getResourceAsStream("mail.properties"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @AfterThrowing("execution(* edu.mum.cs490.project.utils.EmailUtil.sendEmail(..)) && args(toEmail, subject, body)")
    public void aopResendEmail(String toEmail, String subject, String body){
        try {
            if(delay == 60)
            {
                delay = 0;
                return;
            }
            delay = delay == 0 ? 5 : delay == 5 ? 30 : 60;
            Thread.sleep(delay * 60000);
            emailUtil.sendEmail(toEmail, subject, body);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterThrowing("execution(* edu.mum.cs490.project.utils.EmailUtil.sendEmailWithAttachment(..)) && args(toEmail, subject, body, attachmentFile, nameOfAttachment)")
    public void aopResendEmailWithAttachment(String toEmail, String subject, String body, byte[] attachmentFile, String nameOfAttachment){
        try {
            if(delay == 60)
            {
                delay = 0;
                return;
            }
            delay = delay == 0 ? 5 : delay == 5 ? 30 : 60;
            Thread.sleep(delay * 60000);
            emailUtil.sendEmailWithAttachment(toEmail, subject, body, attachmentFile, nameOfAttachment);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
