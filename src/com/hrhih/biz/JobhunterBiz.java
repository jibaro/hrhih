package com.hrhih.biz;

import com.hrhih.entity.Jobhunter;

/**
 *求职者业务类
 */
public interface JobhunterBiz {
	/**
	 * 插入 Jobhunter
	 * @param jh
	 * @return
	 */
	public int insertJobhunter(Jobhunter jh);
	/**
	 * 更新Jobhunter
	 * @param jh Jobhunter 对象
	 * @return
	 */
	public int updateJobhunter(Jobhunter jh);
	/**
	 * 通过一个属性更新Jobhunter
	 * @param fieldname
	 * @param value
	 * @return
	 */
	public int updateJobhunter(String fieldname ,Object value);
	/**
	 * 求职者登陆
	 * @param loginField 登陆的字段名，如：mobile,email
	 * @param loginValue 登陆的值。
	 * @param loginPasswd 登陆密码。
	 * @return
	 */
	public Jobhunter findJobhunter(String loginField,String loginValue,String loginPasswd);
	/**
	 * 通过ID删除Jobhunter
	 * @param jhid
	 * @return
	 */
	public int deleteJobhunterByID(Integer jhid);
	
}
