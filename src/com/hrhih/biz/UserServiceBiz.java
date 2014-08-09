package com.hrhih.biz;

import com.hrhih.entity.Jobhunter;

/**
 * 招聘者和求职者业务类
 * @author Yesq
 *
 */
public interface UserServiceBiz {
	
	/**
	 * 求职都登陆
	 * @param longname 登陆名,
	 * @param passwd 登陆密码,需要做MD5转换
	 * @param logintype 登陆类型，如username,email,mobile,relatecounter
	 * @return
	 */
	public Jobhunter login(String loginstr,String passwd ,String logintype);
	
	/**
	 * 增加求职者
	 * @param jh 求职者对象
	 * @return
	 */
	public int insertJobhunter(Jobhunter jh);
	
	/**
	 * 更改求职者
	 * @param jh 求职者对象
	 * @return
	 */
	public int updateJobhunter(Jobhunter jh);
}
