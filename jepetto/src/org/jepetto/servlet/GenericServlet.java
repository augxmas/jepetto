package org.jepetto.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jepetto.util.PropertyReader;

/**
 * Servlet implementation class GenericServlet
 */
public class GenericServlet extends HttpServlet {
	
	protected PropertyReader reader = PropertyReader.getInstance();
	
	public void init(ServletConfig config){
		
	}
	

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
