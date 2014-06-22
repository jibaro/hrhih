package com.hrhih.action.entity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.hrhih.utils.PropertyUtil;

public class FileUploadTools {
    private String username;
    private File uploadFile[];// 上传的文件是数组类型
    private String uploadFileFileName[];// 文件名是数组类型
    private String uploadFileContentType[];

    private String targetDirectory = PropertyUtil.getValue("/tools.properties", "TARGET_ROOT_PATH");
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    // 上传的ContentType文件类型也是数组类型
    // 必须要加上对ContentType的声明，否则会出现异常
    public String[] getUploadFileContentType() {
        return uploadFileContentType;
    }
    public void setUploadFileContentType(String[] uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
    }
    public File[] getUploadFile() {
        return uploadFile;
    }
    public void setUploadFile(File[] uploadFile) {
        this.uploadFile = uploadFile;
    }
    public String[] getUploadFileFileName() {
        return uploadFileFileName;
    }
    public void setUploadFileFileName(String[] uploadFileFileName) {
        this.uploadFileFileName = uploadFileFileName;
    }

    public String beginUpload() throws IOException {
        System.out.println("用户名：" + username);

        for (int i = 0; i < uploadFile.length; i++) {
            File target = new File(targetDirectory, new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
                    .format(new Date()).toString()+"_"+username+"_" + System.nanoTime() + uploadFileFileName[i]);
            System.out.println("uploadFile=="+uploadFile[i].getAbsolutePath());
            System.out.println("target=="+target.getAbsolutePath());
            FileUtils.copyFile(uploadFile[i], target);
        }
        return "success";
    }
}
