package com.hrhih.index.suggest;

import java.util.List;

public interface AutoSuggester {
	public void init();
	public List<String> suggestString(String query);
	public List<String> suggestRegion(String query,List<Long> regionvalues);
}
