/**
 * 
 */
package com.hrhih.test;

import java.util.HashMap;
import java.util.Map;

import com.hrhih.biz.TestBiz;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author shuqiang
 * 2014-6-19 下午03:04:45
 */
public class TestAction extends ActionSupport {
	
	// 返回结果给客户端
	private Map<String, Object> dataMap;
	
	private String username;
	private Integer age;
	private String email;
	
	private TestBiz testbiz;
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

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
	
	public String list() throws Exception {
		
		System.out.println("TestAction===list() exception test");
		
		int i = 10/0;
	    return "list";
	}
	
	public String jsonlist() {
		
		System.out.println("jsonlist>>>>>username=="+username+"  age=="+age+"   email=="+email);
		dataMap = new HashMap<String, Object>();
		dataMap.put("totalcount", 4);
		dataMap.put("curpage", 1);
		TestUser testuser = new TestUser(1,"zhangsan","111222");
		dataMap.put("tuser", testuser);
		return SUCCESS;
	}
	
}
