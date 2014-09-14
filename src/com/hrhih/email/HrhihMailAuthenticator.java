package com.hrhih.email;

import javax.mail.*;   
public class HrhihMailAuthenticator extends Authenticator{   
  String userName=null;   
  String password=null;   
      
  public HrhihMailAuthenticator(){   
  }   
  public HrhihMailAuthenticator(String username, String password) {    
      this.userName = username;    
      this.password = password;    
  }    
  protected PasswordAuthentication getPasswordAuthentication(){   
      return new PasswordAuthentication(userName, password);   
  }   
}   
