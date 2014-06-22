package com.hrhih.biz;

import java.io.File;
import java.util.List;

import com.hrhih.entity.Jobhunter;
import com.hrhih.entity.MyResumer;

/**
 * 个人简历业务类
 * @author Yesq
 *
 */
public interface MyResumerBiz {
	/**
	 * 插入 MyResumer
	 * @param res
	 * @return
	 */
	public int insertMyResumer(MyResumer res);
	
	
	/**
	 * 插入 MyResumer
	 * @param jh 求职者
	 * @param file 上传的文件
	 * @return
	 */
	public int insertMyResumer(Jobhunter jh ,File file);
	
	/**
	 * 更新MyResumer
	 * @param res MyResumer 对象
	 * @return
	 */
	public int updateMyResumer(MyResumer res);
	/**
	 * 通过一个属性更新MyResumer
	 * @param fieldname
	 * @param value
	 * @return
	 */
	public int updateMyResumer(String fieldname ,Object value);
	/**
	 * 通过简历ID查找简历
	 * @param resid 简历ID
	 * @return
	 */
	public MyResumer findMyResumerByid(Integer resid);
	/**
	 * 通过求职者ID查找简历
	 * @param jhid 求职者的ID
	 * @return
	 */
	public List<MyResumer> findMyResumersByjhid(Integer jhid);
	/**
	 * 通过ID删除MyResumer
	 * @param jhid
	 * @return
	 */
	public int deleteMyResumerByID(Integer jhid);
	
}
