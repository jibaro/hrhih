package com.hrhih.bigpipe.dealer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hrhih.bigpipe.test.PageAndModel;
import com.opensymphony.xwork2.util.ValueStack;

public interface IPageLetDealer {

	public PageAndModel<String, Object> deal(ValueStack vs, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
