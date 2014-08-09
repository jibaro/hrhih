package com.hrhih.biz;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.hrhih.entity.Jobhunter;
import com.hrhih.entity.MyResumer;
import com.hrhih.tools.Office2PDF;
import com.hrhih.tools.PDF2SWF;
import com.hrhih.utils.PropertyUtil;
import com.hrhih.utils.TimeUtil;

public class MyResumerBizImpl implements MyResumerBiz {

	
	
	private final String SWF_ROOT_PATH = PropertyUtil.getValue("/tools.properties", "SWF_ROOT_PATH");
	
	public int insertMyResumer(MyResumer res) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int insertMyResumer(Jobhunter jh ,File file,String srcfilename){
		
		String filepathname=file.getAbsolutePath().substring(0,file.getAbsolutePath().lastIndexOf("."));
		File pdfFile=new File(filepathname+".pdf");
		//把文档转换成PDF
		Office2PDF.office2PDF(file, pdfFile);
		
		//把PDF转换成SWF
		PDF2SWF.converse(pdfFile.getAbsolutePath(), SWF_ROOT_PATH+pdfFile.getName()
													.substring(0, pdfFile.getName().lastIndexOf(".pdf"))+".swf");
		//插入数据库
		//只插入路径，文件内容可以不插入。
		MyResumer resumer=new MyResumer();
		resumer.setResumername(srcfilename);
		resumer.setLangtype(0);
		resumer.setOpentype(0);
		resumer.setCreatetime(TimeUtil.formatDate(System.currentTimeMillis(), "yyyy-MM-dd"));
		resumer.setUpdatetime((int) (System.currentTimeMillis()/1000/60));//精确到分。
		resumer.setModeltype(1);
		try {
			PDDocument pdfdoc = PDDocument.load(pdfFile);
			PDFTextStripper stripper = new PDFTextStripper();
			String content=stripper.getText(pdfdoc);
			resumer.setContent(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		resumer.setKeywrods("");
		resumer.setSrcfilename(srcfilename);
		resumer.setDestfilename(file.getName());
		
		resumer.setJobhunter(jh);
		
		
		//插入索引,需要插入文件内容(包含简历内容)。或者用定时任务，定时跑生成增量索引。
		
		
		return 0;
	}

	public int updateMyResumer(MyResumer res) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateMyResumer(String fieldname, Object value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public MyResumer findMyResumerByid(Integer resid) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<MyResumer> findMyResumersByjhid(Integer jhid) {
		// TODO Auto-generated method stub
		return null;
	}

	public int deleteMyResumerByID(Integer jhid) {
		// TODO Auto-generated method stub
		return 0;
	}
}
