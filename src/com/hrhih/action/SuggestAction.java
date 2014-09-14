package com.hrhih.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.hrhih.index.suggest.AutoSuggester;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 索引处理类，包含：动态提示、索引查询、多条件复杂查询。
 * @author Yesq
 *
 */
public class SuggestAction extends ActionSupport {

	private AutoSuggester autosuggest;
	private AutoSuggester regionsuggest;
	private String suggest;
	private String regionvalue;
	
    /** 智能提示列表，以JSON数据格式返回 */
    private List<String> suggests = new ArrayList<String>();
	
	public AutoSuggester getAutosuggest() {
		return autosuggest;
	}

	public void setAutosuggest(AutoSuggester autosuggest) {
		this.autosuggest = autosuggest;
	}
	
    public AutoSuggester getRegionsuggest() {
		return regionsuggest;
	}

	public void setRegionsuggest(AutoSuggester regionsuggest) {
		this.regionsuggest = regionsuggest;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getRegionvalue() {
		return regionvalue;
	}

	public void setRegionvalue(String regionvalue) {
		this.regionvalue = regionvalue;
	}

	//智能提示列表作为JSON数据返回，设置serialize=true
    @JSON(serialize=true)
	public List<String> getSuggests() {
		return suggests;
	}

	public void setSuggests(List<String> suggests) {
		this.suggests = suggests;
	}

	/**
	 * 搜索工作，动态提示,(求职者搜索)
	 * @return
	 * @throws IOException
	 */
	public String jobhunter() throws IOException {
		
		System.out.println("jobhunter str==="+suggest);
		suggests=autosuggest.suggestString(suggest.toLowerCase());
		System.out.println("regests==="+suggests.size()+"===="+suggests);
		
		return SUCCESS;
	}

	/**
	 * 搜索简历，动态提示,(招聘人员搜索)
	 * @return
	 * @throws IOException
	 */
	public String recruiter() throws IOException {
		
		
		return SUCCESS;
	}
	
	/**
	 * 搜索地区，动态提示。
	 * @return
	 * @throws IOException
	 */
	public String region() throws IOException {
		
		System.out.println("region str==="+suggest+"==="+regionvalue);
		
//		for(int i=0;i<5;i++){
//			List<Object> s1 = new ArrayList<Object>();
//			s1.add(10+i);
//			s1.add("\"Baby"+i+" luigi\"");
//			s1.add(null);
//			s1.add("\"Baby"+i+" luigi\"");
//			suggests.add(s1.toString());
//		}
		List<Long> regionvalues=new ArrayList<Long>();
		if(regionvalue!=null&&regionvalue.trim().length()>0){
			for(String rv:regionvalue.split(",")){
				regionvalues.add(Long.parseLong(rv));
			}
		}
		suggests=regionsuggest.suggestRegion(suggest.toLowerCase(),regionvalues);
		System.out.println("region==="+suggests.size()+"===="+suggests);
		
		return SUCCESS;
	}
}
