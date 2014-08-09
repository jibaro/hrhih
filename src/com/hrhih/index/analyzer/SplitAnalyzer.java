package com.hrhih.index.analyzer;

import java.io.Reader;
import org.apache.lucene.analysis.Analyzer;

import com.hrhih.constant.SpecialConstant;

/**
 * 自定义单个char字符分词器
 * @author Yesq
 * **/
public class SplitAnalyzer extends Analyzer {
	private char c;// 按特定符号进行拆分

	public SplitAnalyzer(char c) {
		this.c = c;
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}

	@Override
	protected TokenStreamComponents createComponents(String arg0, Reader arg1) {
		return new TokenStreamComponents(new SpiltTokenizer(SpecialConstant.LUCENE_VERSION,arg1, c));
	}
}