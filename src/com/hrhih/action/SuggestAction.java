package com.hrhih.action;

import java.io.IOException;
import java.util.ArrayList;
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
	private String suggest;
	
    /** 智能提示列表，以JSON数据格式返回 */
    private List<String> suggests = new ArrayList<String>();
	
	public AutoSuggester getAutosuggest() {
		return autosuggest;
	}

	public void setAutosuggest(AutoSuggester autosuggest) {
		this.autosuggest = autosuggest;
	}

    public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
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
	 * 动态提示
	 * @return
	 * @throws IOException
	 */
	public String suggest() throws IOException {
		
		System.out.println("suggest str==="+suggest);
		suggests=autosuggest.suggestString(suggest);
		System.out.println("regests==="+suggests.size()+"===="+suggests);
		
		return SUCCESS;
	}

}
