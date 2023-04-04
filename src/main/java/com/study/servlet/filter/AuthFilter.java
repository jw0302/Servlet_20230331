package com.study.servlet.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*") // /* 로 해놓으면 모든 요소들을 거칠수 있다.
public class AuthFilter extends HttpFilter implements Filter {
       
    public AuthFilter() {
    }

	public void destroy() {
		// 객체가 소멸 될 때 딱 한번 실행
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 전처리
		System.out.println("전처리");
		/*
		 * 1. url: role요청이 들어왔을 때 로그인이 되어 있는지 확인
		 * 2. 로그인이 되어 있으면 doFilter를 통해 서블릿에 접근 허용
		 * 3. 로그인이 되어 있지 않으면 response를 통해 로그인 페이지로 이동
		 */
		
		// request,response 다운캐스팅
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		// URL에서 포트번호 뒤에 있는 주소가 URI이다.
		String uri = httpRequest.getRequestURI();
		
		// 시간 세팅 안하면 30분 자동 등록 되어 있다.
		httpRequest.getSession().setMaxInactiveInterval(10);
		
		List<String> antMatchers = new ArrayList<>();
		antMatchers.add("/user");
		antMatchers.add("/mypage");
		
		for(String antMatcher : antMatchers) {
			// antMatcher에 등록된 URI로 시작하면 인증을 거친다.
			if(uri.startsWith(antMatcher)) {
				HttpSession session = httpRequest.getSession();
				if(session.getAttribute("AuthenticationPrincipal") == null) {
					httpResponse.sendRedirect("/login.html");
					return;
				}
			}
		}
		
		// filter 클래스에서는 request가 업캐스팅 되어서 들어온거기 때문에 다운 캐스팅을 해줘야 원래 우리가 알고 있던 request, response의 기능을 사용할 수 있다.
//		System.out.println(request);
		chain.doFilter(request, response); // 다음으로 실행될 필터나 서블릿
		System.out.println("후처리");
		// 후처리
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// 객체가 생성 될 때 딱 한번 실행
	}

}
