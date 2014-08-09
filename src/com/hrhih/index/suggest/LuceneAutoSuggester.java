package com.hrhih.index.suggest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

import com.hrhih.index.analyzer.SplitAnalyzer;

/**
 * this is sinlgeTon class which contains suggestorMap.
 * @author ivy4127
 */
public class LuceneAutoSuggester implements AutoSuggester{
	
	public static final char separator='@';
	
	private int suggest_max;//最大推荐数
	
	private String suggest_data_dir;	//推荐原始数据存放的目录
	
	public int getSuggest_max() {
		return suggest_max;
	}

	public void setSuggest_max(int suggest_max) {
		this.suggest_max = suggest_max;
	}

	public String getSuggest_data_dir() {
		return suggest_data_dir;
	}

	public void setSuggest_data_dir(String suggest_data_dir) {
		this.suggest_data_dir = suggest_data_dir;
	}

	private List<Input> lookUpList;
	
	private AnalyzingInfixSuggester analyzingSuggester ;
	
	public void init(){
		lookUpList=new ArrayList<Input>();
		Analyzer a = new SplitAnalyzer(separator);
		try {
			//把自动提示的索引放在内存中。
			analyzingSuggester = new AnalyzingInfixSuggester(Version.LUCENE_4_9, new RAMDirectory(), a, a, 3);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		prepareLookupMap( new File(suggest_data_dir));
		prepareAutoSuggestor();
	}

	/**
	 * this function read all the files in specified directory and prepare the map
	 * @param dataDir
	 */
	private void prepareLookupMap(File dataDir){
		File[] files = dataDir.listFiles();
		for(int i=0;i<files.length;i++){
			if(files[i].isDirectory()){
				prepareLookupMap(files[i]);
			}else{
				prepareLookupList(files[i]);
			}
		}
	}
	
    /**
     * this function is used to prepare loop map with the given file
     * @param f
     */
	private void prepareLookupList(File f ) {
		
		FileInputStream fileStream =null;
		InputStreamReader br = null;
		BufferedReader reader=null;
		String line=null;
		try {
			fileStream = new FileInputStream(f);
			br = new InputStreamReader(fileStream,"GBK"); 
			reader = new BufferedReader(br);
			while((line=reader.readLine())!=null){
				if(line.length()!=0){
					String[] splits=line.split(",");
					String query=splits[0];
					String Number=splits[1];
					lookUpList.add(new Input(query, Long.valueOf(Number),new BytesRef(query)));
				}
			}
			System.out.println("looup list size::" + lookUpList.size());	
		}catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("line==="+line);
			e.printStackTrace();
		} 
	}

	private void prepareAutoSuggestor(){
		try {
			analyzingSuggester.build(new InputArrayIterator(lookUpList.iterator()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> suggestString(String query){
		 List<String> retList=new ArrayList<String>();
		try {
//			results = analyzingSuggester.lookup(query, false, 100);
			List<LookupResult> results = analyzingSuggester.lookup(query,suggest_max, true, true);;
			
			for(LookupResult lr:results){
				retList.add(lr.key.toString().split(String.valueOf(separator))[0].replaceAll("<b>|</b>", ""));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retList;
	}
}