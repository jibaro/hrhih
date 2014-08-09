package com.hrhih.entity;

import java.io.Serializable;
import java.util.Set;

 /**
  * 求职者
 * @author Yesq
 */
public class Jobhunter implements Serializable {
	
	private Integer jhid;//唯一ID
	private String mobile;//电话号码
	private String username;//用户真实名
	private String email;//邮箱
	private int passporttype;//0=身份证、1=军官证、2=户照
	private String passport;//身份证、军官证、户照等号码。
	private String passwd;//登陆密码。
	private String relatecounter;//关联的帐号
	private int regtype;//0=本网注册，1=qq帐号注册，2=新浪微博，3=腾讯微博。其它方式可以以后再添加。
	private String birthday;//出生日期1984-11-03
	private String photourl;//照片URL
	private String curraddr;//当前地址
	private String currpost;//当前地址的邮编号码.
	private String addr;//户籍地址
	private int degree;//0=无学历,1=初中,2=高中,3=中专,4=专科,5=本科/学士,6=硕士,7=博士,8=教授
	private String edudistrict;//毕业地区或国家
	private String edu;//毕业学校.
	private String edudate;//毕业时间2014-06-22
	private int sex;//性别,0=女,1=男,2=保密
	private int age;//年纪
	private int marry;//是否结婚.0=否,1=是,2=保密
	private int stature;//身高,CM
//	private int workyear;//开始工作时间，如：20140622
	
	private Set<MyResumer> resumers;//该用户的多个简历。
	
	public Jobhunter() {}

	public Integer getJhid() {
		return jhid;
	}

	public void setJhid(Integer jhid) {
		this.jhid = jhid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPassporttype() {
		return passporttype;
	}

	public void setPassporttype(int passporttype) {
		this.passporttype = passporttype;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getCurraddr() {
		return curraddr;
	}

	public void setCurraddr(String curraddr) {
		this.curraddr = curraddr;
	}

	public String getCurrpost() {
		return currpost;
	}

	public void setCurrpost(String currpost) {
		this.currpost = currpost;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getMarry() {
		return marry;
	}

	public void setMarry(int marry) {
		this.marry = marry;
	}

	public int getStature() {
		return stature;
	}

	public void setStature(int stature) {
		this.stature = stature;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEdudistrict() {
		return edudistrict;
	}

	public void setEdudistrict(String edudistrict) {
		this.edudistrict = edudistrict;
	}

	public int getRegtype() {
		return regtype;
	}

	public void setRegtype(int regtype) {
		this.regtype = regtype;
	}

	public Set<MyResumer> getResumers() {
		return resumers;
	}

	public void setResumers(Set<MyResumer> resumers) {
		this.resumers = resumers;
	}

	public String getEdudate() {
		return edudate;
	}

	public void setEdudate(String edudate) {
		this.edudate = edudate;
	}

	public String getRelatecounter() {
		return relatecounter;
	}

	public void setRelatecounter(String relatecounter) {
		this.relatecounter = relatecounter;
	}

	@Override
	public String toString() {
		return "Jobhunter [jhid=" + jhid + ", mobile=" + mobile + ", username="
				+ username + ", email=" + email + "]";
	}
	
}
