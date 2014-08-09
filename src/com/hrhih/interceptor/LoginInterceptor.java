package com.hrhih.interceptor;

import com.hrhih.constant.Constants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		// 取得请求相关的ActionContext实例
		ActionContext ctx = invocation.getInvocationContext();
		String isLogin = (String) ctx.getSession().get(Constants.ISLOGIN);

		System.out.println("LoginInterceptor=isLogin=="+isLogin);
		
		// 如果没有登陆，都返回重新登陆
		if(isLogin==null||!isLogin.equals(Constants.LOGIN_SIGN)){
			ctx.put("tip", "Jobhunter Login First");
			return Action.LOGIN;
		}
		
		return invocation.invoke();
	}
}

