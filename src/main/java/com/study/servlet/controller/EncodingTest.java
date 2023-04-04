package com.study.servlet.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/encoding/test")
public class EncodingTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// filter를 해주지 않고 getParameter만 요청하게 되면 한글처리가 되지 않고 깨지지만 filter처리를 해주면 별다른 코드 없이 한글처리 해줌
		System.out.println(request.getParameter("data"));
	}

}
