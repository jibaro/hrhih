package com.hrhih.entity;

import java.io.Serializable;

/**
 * 我的简历
 * @author Yesq
 */
public class MyResumer implements Serializable {

	private Integer resumerid;
	private String resumername;//简历名称，如果modeltype为上传（=1），即文件名。
	private int langtype;//简历类型，0=中文，1=英文
	private int opentype;//公开程度，0=公开，1=对携手网公开，2=完全保密。
	private String createtime;//简历创建时间 格式：2014-06-22。
	private int updatetime;//简历更新时间，精确到分。
	//0=网页填写,1=上传.	如果为0的话,需要网页填写的内容(如: 教育经历、学生信息、工作经验、
	//	培训经历、语言能力、附加信息等等)作为该简历的一部分,暂时未实现.
	private int modeltype;
	private String content;//简历内容，如果modeltype=1有效.modeltype=0,该值为空.
	private String keywrods;//描述简历的关键字。最好跟自己的专业和职位相关，如行业、特长、业绩等
	private String srcfilename;//上传的源文件名称。
	private String destfilename;//上传的目标文件名称。上传文件改名后的文件名。
	private int salary;//当前年薪,RMB.
	private int worklong;//从事该工作年限
	
	//求职意向。
	private int expsalary;//期望年薪,RMB.
	private int worktype;//工作类型，0=全职，1=兼职，2=实习
	private String workaddr;//期望的工作地址。
	private String industry;//行业
	private String job;//工作职能
	private int duty_date;//到岗时间,1=两周内，2=一个月内，3=三个月内。
	
	private Jobhunter jobhunter;//简历所有者

	public MyResumer() {}

	public Integer getResumerid() {
		return resumerid;
	}

	public void setResumerid(Integer resumerid) {
		this.resumerid = resumerid;
	}

	public String getResumername() {
		return resumername;
	}

	public void setResumername(String resumername) {
		this.resumername = resumername;
	}

	public int getLangtype() {
		return langtype;
	}

	public void setLangtype(int langtype) {
		this.langtype = langtype;
	}

	public int getOpentype() {
		return opentype;
	}

	public void setOpentype(int opentype) {
		this.opentype = opentype;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public int getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(int updatetime) {
		this.updatetime = updatetime;
	}

	public int getModeltype() {
		return modeltype;
	}

	public void setModeltype(int modeltype) {
		this.modeltype = modeltype;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeywrods() {
		return keywrods;
	}

	public void setKeywrods(String keywrods) {
		this.keywrods = keywrods;
	}

	public String getSrcfilename() {
		return srcfilename;
	}

	public void setSrcfilename(String srcfilename) {
		this.srcfilename = srcfilename;
	}

	public String getDestfilename() {
		return destfilename;
	}

	public void setDestfilename(String destfilename) {
		this.destfilename = destfilename;
	}

	public String getWorkaddr() {
		return workaddr;
	}

	public void setWorkaddr(String workaddr) {
		this.workaddr = workaddr;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getExpsalary() {
		return expsalary;
	}

	public void setExpsalary(int expsalary) {
		this.expsalary = expsalary;
	}

	public int getWorktype() {
		return worktype;
	}

	public void setWorktype(int worktype) {
		this.worktype = worktype;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Jobhunter getJobhunter() {
		return jobhunter;
	}

	public void setJobhunter(Jobhunter jobhunter) {
		this.jobhunter = jobhunter;
	}

	public int getWorklong() {
		return worklong;
	}

	public void setWorklong(int worklong) {
		this.worklong = worklong;
	}

	public int getDuty_date() {
		return duty_date;
	}

	public void setDuty_date(int duty_date) {
		this.duty_date = duty_date;
	}

}
