/**
 * 
 */
package com.hrhih.biz;

import com.hrhih.dao.TestDaoImpl;
import com.hrhih.entity.TestUserInfo;

/**
 * @author shuqiang
 * 2014-6-19 下午04:39:35
 */
public class TestBizImpl implements TestBiz{
	
	private TestDaoImpl testdao;
	
	/**
	 * @param testdao the testdao to set
	 */
	public void setTestdao(TestDaoImpl testdao) {
		this.testdao = testdao;
	}

	public int insertData(String username,int age,String email){
		TestUserInfo entity=new TestUserInfo();
		entity.setUsername(username);
		entity.setAge(age);
		entity.setEmail(email);
		int retvalue=testdao.insertUser(entity);
		return retvalue;
	}
}
