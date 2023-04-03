package com.study.servlet.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/auth/jw")
public class AuthenticationJW extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");     // username 데이터 요청
		
		
		System.out.println(username);     // 요청 값 확인
		
//		List<User> userList = new ArrayList<>();     // userList List 배열 생성 (.add로 밑에 4개가 userList안의 값 임의 지정함)
//		userList.add(new User("aaa", "1234", "AAA", "aaa@naver.com"));
//		userList.add(new User("bbb", "1234", "BBB", "bbb@naver.com"));
//		userList.add(new User("ccc", "1234", "CCC", "ccc@naver.com"));
//		userList.add(new User("ddd", "1234", "DDD", "ddd@naver.com"));
		
//		User findUser = null;
//		
//		for(User user : userList) {
//			if(user.getUsername().equals(username)) {
//				findUser = user;
//				break;
//			}
//		}
//		
//		Gson gson = new Gson();
//		
//		String userJson = gson.toJson(findUser);
//		
//		response.setContentType("application/json;charset=UTF-8");
//		response.getWriter().println(userJson);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletInputStream inputStream = request.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		String requestBody = bufferedReader.lines().collect(Collectors.joining());
		
		Gson gson = new Gson();
		
		User user = gson.fromJson(requestBody, User.class);
		
		System.out.println(user);
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		JsonObject responseBody = new JsonObject();
		
		if(user == null) {
			responseBody.addProperty("success", false);
		}else {
			responseBody.addProperty("success", true);
		}
		out.println(responseBody.toString());
	}

}
