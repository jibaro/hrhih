package com.hrhih.email.entity;

import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Vector;

import com.sun.mail.util.MailSSLSocketFactory;

public class MailSenderInfo {    
    // 发送邮件的服务器的IP和端口    
    private String mailServerHost;    
    private String mailServerPort = null;    
    // 邮件发送者的地址    
    private String fromAddress;    
    // 邮件接收者的地址    
    private String[] toAddress;    
    // 登陆邮件发送服务器的用户名和密码    
    private String userName;    
    private String password;    
    // 是否需要身份验证    
    private boolean validate = false;    
    //是否需要SSL加密
    private boolean ssl = false;
    // 邮件主题    
    private String subject;    
    // 邮件的文本内容    
    private String content;    
    // 邮件附件的文件名    
    private Vector<String>  attachFileNames = new Vector<String>();;      
    
       
    public Properties getProperties(){    
      Properties p = new Properties();    
      p.put("mail.smtp.host", this.mailServerHost);
      if(this.mailServerPort!=null){
    	  p.put("mail.smtp.port", this.mailServerPort);
      }
      p.put("mail.smtp.ssl.enable",ssl ? "true" : "false");
      p.put("mail.smtp.starttls.enable", "true");
      p.put("mail.smtp.auth", validate ? "true" : "false");
//      p.put("mail.debug", "true");
      p.put("mail.transport.protocol", "smtp");
//      p.setProperty("mail.imap.port", "993");
//      p.setProperty("mail.imap.socketFactory.port", "993");
      if(ssl){
            MailSSLSocketFactory sf;  
              try {
                  sf = new MailSSLSocketFactory();
                  sf.setTrustAllHosts(true);
                  p.put("mail.smtp.ssl.socketFactory", sf); 
//                  p.put("mail.smtp.socketFactory.port", this.mailServerPort);
              } catch (GeneralSecurityException ex) {
                  ex.printStackTrace();
              }
      }
      return p;    
    }    
   
    
    
    public String getMailServerHost() {    
      return mailServerHost;    
    }    
    public void setMailServerHost(String mailServerHost) {    
      this.mailServerHost = mailServerHost;    
    }   
    public String getMailServerPort() {    
      return mailServerPort;    
    }   
    public void setMailServerPort(String mailServerPort) {    
      this.mailServerPort = mailServerPort;    
    }   
    public boolean isValidate() {    
      return validate;    
    }   
    public boolean isSSL(){
        return ssl;
    }
    public void setValidate(boolean validate) {    
      this.validate = validate;    
    }   
    public void setSSL(boolean ssl){
        this.ssl = ssl;
    }
    public Vector<String>  getAttachFileNames() {    
      return attachFileNames;    
    }   
    public void setAttachFileNames(Vector<String> fileNames) {    
      this.attachFileNames = fileNames;    
    }   
    public void addAttachFileNames(String fileName) { 
        this.attachFileNames.add(fileName);
    }
    public String getFromAddress() {    
      return fromAddress;    
    }    
    public void setFromAddress(String fromAddress) {    
      this.fromAddress = fromAddress;    
    }   
    public String getPassword() {    
      return password;    
    }   
    public void setPassword(String password) {    
      this.password = password;    
    }   
    public String[] getToAddress() {    
      return toAddress;    
    }    
    public void setToAddress(String[] toAddress) {    
      this.toAddress = toAddress;    
    }    
    public String getUserName() {    
      return userName;    
    }   
    public void setUserName(String userName) {    
      this.userName = userName;    
    }   
    public String getSubject() {    
      return subject;    
    }   
    public void setSubject(String subject) {    
      this.subject = subject;    
    }   
    public String getContent() {    
      return content;    
    }   
    public void setContent(String textContent) {    
      this.content = textContent;    
    }    
}   
