package test;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class MyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str="<b>中国</b>人民";
		
		
		System.out.println(str.replaceAll("<b>|</b>", ""));
		
	}

}
