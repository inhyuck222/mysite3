package com.cafe24.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor{

	// Controller의 Handler로 가기 전에 실행되는 메서드, 조건에 맞지 않을 경우에 false를 return하고 Hnadler로 이동하지 않는다.
	// false를 return하면서 
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
		System.out.println("MyInterceptor.preHandle()");
		
		return true;
	}


	// Controller의 Handler에대한 요청이 끝나면 실행되는 메서드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		
		System.out.println("MyInterceptor.postHandle()");
		
	}
	
	
	// view에대한...
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {

		System.out.println("MyInterceptor.afterCompletion()");
		
	}

}
