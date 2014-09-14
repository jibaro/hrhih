package test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.google.gson.Gson;

public class MyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<List<Object>> suggests = new ArrayList<List<Object>>();
		
		for(int i=0;i<5;i++){
			List<Object> s1 = new ArrayList<Object>();
			s1.add(10+i);
			s1.add("Baby"+i+" luigi");
			s1.add(null);
			s1.add("Baby"+i+" luigi");
			suggests.add(s1);
		}
		
		
		String teststr="[[10,\"Baby Luigi\",null,\"Baby Luigi\"],[59,\"Lex Luthor\",null,\"Lex Luthor\"],[61,\"Lulu\",null,\"Lulu\"]]";
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(suggests));
		
	}

}
