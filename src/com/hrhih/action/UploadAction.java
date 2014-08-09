package com.hrhih.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.hrhih.action.entity.FileUploadTools;
import com.hrhih.biz.MyResumerBiz;
import com.hrhih.constant.Constants;
import com.hrhih.entity.Jobhunter;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 文件上传
 * @author Yesq
 *
 */
public class UploadAction extends ActionSupport {
    public static final long serialVersionUID = 1;

    private MyResumerBiz resumerbiz;
    
    public void setResumerbiz(MyResumerBiz resumerbiz) {
		this.resumerbiz = resumerbiz;
	}

	// 声明封装了File上传的FileUploadTools类的实例
    // FileUploadTools类也封装了上传的属性及get和set方法
    private FileUploadTools fileUploadTools = new FileUploadTools();

    public FileUploadTools getFileUploadTools() {
        return fileUploadTools;
    }
    public void setFileUploadTools(FileUploadTools fileUploadTools) {
        this.fileUploadTools = fileUploadTools;
    }

    public String resume() throws IOException {
        fileUploadTools.beginUpload();
        
        //清空进度缓存数据
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute(Constants.UPLOAD_STATE);
        
        Jobhunter jh=(Jobhunter)session.getAttribute(Constants.JOBHUNTER);
        
        File targetFile=fileUploadTools.getTargetFiles().get(0);
        System.out.println("File=="+targetFile.getAbsolutePath());
        resumerbiz.insertMyResumer(jh,targetFile,fileUploadTools.getUploadFileFileName()[0]);
        
        return SUCCESS;
    }
}
