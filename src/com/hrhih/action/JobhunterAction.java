package com.hrhih.action;

import com.hrhih.biz.UserServiceBiz;
import com.hrhih.entity.Jobhunter;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 求职者Action
 * @author Yesq
 *
 */
public class JobhunterAction extends ActionSupport {

	private Jobhunter jobhunter;
	
	public Jobhunter getJobhunter() {
		return jobhunter;
	}
	
	public void setJobhunter(Jobhunter jobhunter) {
		this.jobhunter = jobhunter;
	}
	
	private UserServiceBiz userbiz;
	
	public void setUserbiz(UserServiceBiz userbiz) {
		this.userbiz = userbiz;
	}
	
	public String register() {
		
		userbiz.insertJobhunter(jobhunter);
		
		return SUCCESS;
	}

}
