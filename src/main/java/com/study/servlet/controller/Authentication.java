package com.study.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.study.servlet.dto.RequestDto;
import com.study.servlet.dto.ResponseDto;
import com.study.servlet.entity.User;
import com.study.servlet.service.UserService;
import com.study.servlet.service.UserServiceImpl;

@WebServlet("/auth")
public class Authentication extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	private Gson gson;
	
	public Authentication() {
		userService = UserServiceImpl.getInstance();
		gson = new Gson();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = RequestDto.<User>convertRequestBody(request, User.class);
		
		boolean duplicatedFlag = userService.duplicatedUsername(user.getUsername());
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		if(duplicatedFlag) {
//			true == 중복, false == 가입가능
			ResponseDto<Boolean> responseDto =
					// 400은 bad request라는 의미로 실패라는 뜻을 가지고 있다.
					new ResponseDto<Boolean>(400, "duplicated username", duplicatedFlag);
			out.println(gson.toJson(responseDto));
			return;
		}
		
		ResponseDto<Integer> responseDto =
				new ResponseDto<Integer>(201, "signup", userService.addUser(user));
		out.println(gson.toJson(responseDto));
		
//		userService.addUser(user);
//		Map<String, String> map = RequestDto.<Map<String, String>>convertRequestBody(request, Map.class);
//		System.out.println(user);
//		System.out.println(map);
		
	}

}
