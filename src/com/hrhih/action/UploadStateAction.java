package com.hrhih.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;

import com.hrhih.constant.Constants;
import com.hrhih.listener.State;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 文件上传状态
 * @author Yesq
 *
 */
public class UploadStateAction extends ActionSupport {

    public String resume() throws IOException {
        // 从session中取得名称为Constants.UPLOAD_STATE的State对象
        State tempState = (State) ServletActionContext.getRequest().getSession().getAttribute(Constants.UPLOAD_STATE);
        // 设置编码为utf-8
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        // 设置响应的格式为XML
        ServletActionContext.getResponse().setContentType("text/xml");
        // 用out对象输出xml代码头
        ServletActionContext.getResponse().getWriter()
                .print("<?xml version='1.0' encoding='" + "utf-8" + "' ?>");
        // 用out对象输出xml代码体
        ServletActionContext.getResponse().getWriter().print(tempState.getStateString());
        
        return null;
    }

}
