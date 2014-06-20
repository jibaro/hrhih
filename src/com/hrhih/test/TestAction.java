/**
 * 
 */
package com.hrhih.test;

import com.hrhih.biz.TestBiz;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author shuqiang
 * 2014-6-19 下午03:04:45
 */
public class TestAction extends ActionSupport {
	
	private String username;
	private Integer age;
	private String email;
	
	private TestBiz testbiz;
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}


	/**
	 * @param age the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}


	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param testbiz the testbiz to set
	 */
	public void setTestbiz(TestBiz testbiz) {
		this.testbiz = testbiz;
	}


	@Override
	public String execute() throws Exception {
		
		System.out.println("username=="+username+"  age=="+age+"   email=="+email);
		
		if(username==null){
			System.out.println("username=null");
			return ERROR;
		}
		
		if(age<18){
			System.out.println("age<18	("+age+")");
			return ERROR;
		}
		
		testbiz.insertData(username,age,email);
		return SUCCESS;
	}
}
