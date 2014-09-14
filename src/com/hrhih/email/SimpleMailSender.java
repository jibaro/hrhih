package com.hrhih.email;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.hrhih.email.entity.MailSenderInfo;
  
   
public class SimpleMailSender  {    
   
    public boolean sendTextMail(MailSenderInfo mailInfo) {    
      // 判断是否需要身份认证    
    	HrhihMailAuthenticator authenticator = null;    
      Properties pro = mailInfo.getProperties();   
      if (mailInfo.isValidate()) {    
      // 如果需要身份认证，则创建一个密码验证器    
        authenticator = new HrhihMailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());    
      }
      // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
      Session sendMailSession = Session.getInstance(pro,authenticator);    
      try {
      // 根据session创建一个邮件消息
      Message mailMessage = new MimeMessage(sendMailSession);
      // 创建邮件发送者地址
//      Address from = new InternetAddress(mailInfo.getFromAddress());
      Address from = new InternetAddress(mailInfo.getFromAddress(),mailInfo.getUserName(),"UTF-8");
      // 设置邮件消息的发送者
      mailMessage.setFrom(from);
      
      // 创建邮件的接收者地址，并设置到邮件消息中
      //Address to = new InternetAddress(mailInfo.getToAddress());
      Address [] to  = new Address[mailInfo.getToAddress().length];
      for(int i = 0;i < to.length; i++){
          to[i] = new InternetAddress(mailInfo.getToAddress()[i]);
      }
      mailMessage.setRecipients(Message.RecipientType.TO,to);
      // 设置邮件消息的主题
      mailMessage.setSubject(mailInfo.getSubject());
      // 设置邮件消息发送的时间
      mailMessage.setSentDate(new Date());
      // 设置邮件消息的主要内容
      String mailContent = mailInfo.getContent();
      mailMessage.setText(mailContent);
      //设置附件
      
      // 发送邮件
      Transport tp=sendMailSession.getTransport();
      tp.connect(pro.getProperty("mail.stmp.host"),mailInfo.getFromAddress(),mailInfo.getPassword());
      tp.sendMessage(mailMessage,mailMessage.getRecipients(Message.RecipientType.TO));
      
      return true;
      } catch (MessagingException ex) {
          ex.printStackTrace();
      } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      return false;
    }

    public boolean sendHtmlMail(MailSenderInfo mailInfo){
      // 判断是否需要身份认证
      HrhihMailAuthenticator authenticator = null;
      Properties pro = mailInfo.getProperties();
      //如果需要身份认证，则创建一个密码验证器
      if (mailInfo.isValidate()) {
        authenticator = new HrhihMailAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());   
      }    
      // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
      Session sendMailSession = Session.getDefaultInstance(pro,authenticator);    
      try {    
      // 根据session创建一个邮件消息    
      Message mailMessage = new MimeMessage(sendMailSession);    
      // 创建邮件发送者地址    
//      Address from = new InternetAddress(mailInfo.getFromAddress()); 
      Address from = new InternetAddress(mailInfo.getFromAddress(),mailInfo.getUserName(),"UTF-8");    
      // 设置邮件消息的发送者    
      mailMessage.setFrom(from);    
      // 创建邮件的接收者地址，并设置到邮件消息中    
      Address [] to  = new Address[mailInfo.getToAddress().length];
      for(int i = 0;i < to.length; i++){
          to[i] = new InternetAddress(mailInfo.getToAddress()[i]);
      }
        //Address to = new InternetAddress(mailInfo.getToAddress());    
      // Message.RecipientType.TO属性表示接收者的类型为TO    
      mailMessage.setRecipients(Message.RecipientType.TO,to);    
      // 设置邮件消息的主题    
      mailMessage.setSubject(mailInfo.getSubject());    
      // 设置邮件消息发送的时间    
      mailMessage.setSentDate(new Date());    
      // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象    
      Multipart mainPart = new MimeMultipart();    
      // 创建一个包含HTML内容的MimeBodyPart    
      BodyPart html = new MimeBodyPart();    
      // 设置HTML内容    
      html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");    
      mainPart.addBodyPart(html);    
      
      if(!mailInfo.getAttachFileNames().isEmpty()){//有附件  
                Enumeration<String> efile=mailInfo.getAttachFileNames().elements();  
                while(efile.hasMoreElements()){
                    
                    //html.setFileName("=?GBK?B?"+enc.encode(affixName.getBytes())+"?=");
                    html=new MimeBodyPart();  
                    String filename=efile.nextElement().toString(); //选择出每一个附件名  
                    FileDataSource fds=new FileDataSource(filename); //得到数据源  
                    html.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart  
                    //这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
                    //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
                    
                    html.setFileName(MimeUtility.encodeText(fds.getName(),"UTF-8","B"));  //解决乱码,得到文件名同样至入BodyPart 
                    //String filenamex = html.getFileName();
                   
                    //"=?GBK?B?"+enc.encode(affixName.getBytes())+"?="
                    //html.setFileName(filenamex);  //得到文件名同样至入BodyPart  
                    mainPart.addBodyPart(html);  
                }    
                mailInfo.getAttachFileNames().removeAllElements();
                // 将MiniMultipart对象设置为邮件内容    
                
            }   
        mailMessage.setContent(mainPart);  
      // 发送邮件    
      Transport tp=sendMailSession.getTransport();
      tp.connect(pro.getProperty("mail.stmp.host"),mailInfo.getFromAddress(),mailInfo.getPassword());
      tp.sendMessage(mailMessage,mailMessage.getRecipients(Message.RecipientType.TO));
      
      return true;    
      } catch (MessagingException ex) {    
          ex.printStackTrace();    
      } catch (UnsupportedEncodingException ex) {    
            Logger.getLogger(SimpleMailSender.class.getName()).log(Level.SEVERE, null, ex);
        }    
      return false;    
    }    
}   
