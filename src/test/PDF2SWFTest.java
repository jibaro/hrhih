package test;

import com.hrhih.utils.PDF2SWF;

public class PDF2SWFTest {
	public static void main(String[] args){
		String sourceFile = "D:/test/hrhih/word2007.pdf";
		String destFile =  "D:/test/hrhih/word2007.swf";
		try {
			System.out.println(PDF2SWF.converse(sourceFile, destFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
