package com.hrhih.action;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.hrhih.constant.Constants;
import com.hrhih.security.SecurityCode;
import com.hrhih.security.SecurityCode.SecurityCodeLevel;
import com.hrhih.security.SecurityImage;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 生成验证码
 * @author Yesq
 *
 */
public class SecurityCodeImageAction  extends ActionSupport  implements SessionAware{
	private static final long serialVersionUID = 1496691731440581303L;
	//图片流
    private ByteArrayInputStream imageStream;
    //session域
    private Map<String, Object> session ;
    
    public ByteArrayInputStream getImageStream() {
        return imageStream;
    }
    
    public void setImageStream(ByteArrayInputStream imageStream) {
        this.imageStream = imageStream;
    }
    
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
    
    public String execute() throws Exception {
        //如果开启Hard模式，可以不区分大小写
        String securityCode = SecurityCode.getSecurityCode(4,SecurityCodeLevel.Hard, false);
        
        //获取默认难度和长度的验证码
//        String securityCode = SecurityCode.getSecurityCode();
        imageStream = SecurityImage.getImageAsInputStream(securityCode);
        //放入session中
        session.put(Constants.SECURITY_CODE, securityCode);
        return SUCCESS;
    }
}

