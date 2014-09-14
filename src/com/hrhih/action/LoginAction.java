package com.hrhih.action;

import com.hrhih.biz.UserServiceBiz;
import com.hrhih.constant.Constants;
import com.hrhih.entity.Jobhunter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 用户（求职者和公司用户）登陆
 * @author Yesq
 *
 */
public class LoginAction extends ActionSupport {

	private String loginstr;
	private String passwd;
	private String securitycode;

	public String getLoginstr() {
		return loginstr;
	}

	public void setLoginstr(String loginstr) {
		this.loginstr = loginstr;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public String getSecuritycode() {
		return securitycode;
	}

	public void setSecuritycode(String securitycode) {
		this.securitycode = securitycode;
	}


	private UserServiceBiz userbiz;
	
	public void setUserbiz(UserServiceBiz userbiz) {
		this.userbiz = userbiz;
	}

	/**
	 * 求职者登陆
	 * @return
	 */
	public String jobhunter() {
		System.out.println(loginstr);
		System.out.println(passwd);
		System.out.println(securitycode);
		String secucode=ActionContext.getContext().getSession().get(Constants.SECURITY_CODE).toString();
		System.out.println(secucode);
		if (passwd==null||loginstr==null||"".equals(passwd)||"".equals(loginstr))
			return LOGIN;

		if(!secucode.toLowerCase().equals(securitycode.toLowerCase())){
			return LOGIN;
		}
		
		//判断logintype的类型。如username,email,mobile,relatecounter
		//如loginstr包含@，则logintype=“email”，以下假设logintype为email
		String logintype="email";
//		Jobhunter jh=userbiz.login(loginstr, passwd, logintype);
		Jobhunter jh=new Jobhunter();
		jh.setUsername(loginstr);
		jh.setPasswd(passwd);
		
		
		if (jh!=null) {
			ActionContext.getContext().getSession().put(Constants.JOBHUNTER, jh);
			ActionContext.getContext().getSession().put(Constants.ISLOGIN, Constants.LOGIN_SIGN);
			return SUCCESS;
		}
		return ERROR;
	}
}
