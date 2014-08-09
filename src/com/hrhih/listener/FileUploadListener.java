package com.hrhih.listener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import com.hrhih.constant.Constants;

public class FileUploadListener implements ProgressListener {
	
    // 声明一个HttpSession，目的是把State对象放到这个HttpSession中
    private HttpSession session;

    // 此构造函数由MyJakartaMultiPartRequest.java类parseRequest()方法调用
    public FileUploadListener(HttpServletRequest request) {
        super();
        session = request.getSession();
    }

    public void update(long uploadByte, long fileSizeByte, int fileIndex) {
//    	System.out.println("fileSizeByte===="+fileSizeByte);
        if (fileSizeByte == -1) {
            // 如果上传的大小为-1则上传已经完成
            System.out.println("上传文件结束！");
        } else {
            if (session.getAttribute(Constants.UPLOAD_STATE) == null) {
                // 如果为空就new一个State对象并设置里面的文本内容
                State state = new State();
                state.setState(uploadByte, fileSizeByte, (fileIndex - 1));
                session.setAttribute(Constants.UPLOAD_STATE, state);
            } else {
                // 如果session中有Constants.UPLOAD_STATE对象就取出来，然后设置里面文本内容
                State state = (State) session.getAttribute(Constants.UPLOAD_STATE);
                state.setState(uploadByte, fileSizeByte, (fileIndex - 1));
            }
        }
    }
}