package com.hrhih.index.analyzer;

import java.io.Reader;
import org.apache.lucene.analysis.util.CharTokenizer;
import org.apache.lucene.util.Version;

/***
 * 拆分char Tokenizer
 * @author Yesq 
 * 
 * */
public class SpiltTokenizer extends CharTokenizer {
	char c;
	public SpiltTokenizer(Version matchVersion, Reader input, char c) {
		super(matchVersion, input);
		// TODO Auto-generated constructor stub
		this.c = c;
	}

	@Override
	protected boolean isTokenChar(int arg0) {
		return arg0 == c ? false : true;
	}
}