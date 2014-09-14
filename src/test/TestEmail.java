package test;

import com.hrhih.email.SimpleMailSender;
import com.hrhih.email.entity.MailSenderInfo;

public class TestEmail {

	public static void main(String[] args) {
      MailSenderInfo mailInfo = new MailSenderInfo();    
        //mailInfo.setSSL(true);
      
      mailInfo.setMailServerHost("smtp.163.com");
//      mailInfo.setMailServerPort("25");
      mailInfo.setValidate(true);
//      mailInfo.setSSL(true);
      mailInfo.setUserName("枫叶战狼");
      mailInfo.setFromAddress("yeshuqiang@163.com");
      mailInfo.setPassword("fyzl1103");//您的邮箱密码    
      String[] toaddrs={"369536955@qq.com"};
      mailInfo.setToAddress(toaddrs);
      mailInfo.setSubject("测试JAVA发送邮件 ");
      mailInfo.setContent("1111<h2>YeSQ</h2>11测试JAVA<font color='red'>发送邮件111</font>11111111111111111");    
//      mailInfo.addAttachFileNames("d:\\克里斯丁.xls");//增加附件
         //这个类主要来发送邮件   
      SimpleMailSender sms = new SimpleMailSender();   
      sms.sendTextMail(mailInfo);//发送文体格式    
      sms.sendHtmlMail(mailInfo);//发送html格式   
    }

}
