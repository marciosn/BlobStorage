package com.marciosn.cloud.storage.blobs.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
//@WebFilter("/*")
public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest)request).getRequestURI();
		System.out.println(path);
		
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		if(path.toLowerCase().contains("login") || path.toLowerCase().contains("javax.faces.resource")){
			chain.doFilter(request, response);
		}
		else
			if(session != null && session.getAttribute("username") != null){
				chain.doFilter(request, response);
			}
			else{
				String contextPath = ((HttpServletRequest)request).getContextPath();
				((HttpServletResponse)response).sendRedirect(contextPath+"/login.xhtml");
			}
				
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
