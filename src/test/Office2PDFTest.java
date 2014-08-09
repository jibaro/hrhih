package test;

import com.hrhih.tools.Office2PDF;

public class Office2PDFTest {
	public static void main(String[] args){
		//运行之前需要在F:\office下新建MVC.docx,当然也可以换成是其他的路径
		String sourceFile = "D:/test/hrhih/word2010.docx";
		String destFile = "D:/test/hrhih/word2010.pdf";
		System.out.println(Office2PDF.office2PDF(sourceFile, destFile));
	}
}
