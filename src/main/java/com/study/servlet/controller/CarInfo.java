package com.study.servlet.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/car")
public class CarInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("GET 요청");
		Map<String, String> carMap = new HashMap<>();
		carMap.put("1", "쏘나타");
		carMap.put("2", "K5");
		carMap.put("3", "SM6");
		
		String id = request.getParameter("id");
		String findModel = carMap.get(id);
		
		JsonObject responseData = new JsonObject();
		
		if(findModel == null) {
			responseData.addProperty("statusCode", 400);
			responseData.addProperty("ErrorMessage", "Not Found!!");
		}else {
			responseData.addProperty("id", id);
			responseData.addProperty("model", findModel);			
		}
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(responseData.toString());
	}

//	API(애플리케이션 프로그램 인터페이스) :-하나의 기능을 정리한것을 API라고 한다.- doPost와 doGet 각각이 하나의 API이다.
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println("POST 요청");
		request.setCharacterEncoding("UTF-8");
		
		ServletInputStream inputStream = request.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		String requestJson = bufferedReader.lines().collect(Collectors.joining());
		
		System.out.println(requestJson);
		
		Gson gson = new Gson();
		List<Map<String, String>> requestData = gson.fromJson(requestJson, List.class);
		System.out.println(requestData);
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		requestData.forEach(obj -> {
			System.out.println("id(" + obj.get("id") + "): " + obj.get("model"));
			out.println("id(" + obj.get("id") + "): " + obj.get("model"));
		});
	}

}
