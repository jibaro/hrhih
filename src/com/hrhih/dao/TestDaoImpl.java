/**
 * 
 */
package com.hrhih.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hrhih.entity.TestUserInfo;

/**
 * @author shuqiang
 * 2014-6-19 下午04:50:17
 */
@Repository("userDao")
public class TestDaoImpl {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}

	public int insertUser(TestUserInfo entity){
		System.out.println("dao=========================");
//		this.getHibernateTemplate().saveOrUpdate(entity);
		this.getCurrentSession().save(entity);
		return 0;
	}
	

	
}