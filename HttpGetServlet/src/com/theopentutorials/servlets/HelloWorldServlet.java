package com.theopentutorials.servlets;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fvms.team.ejb.Person;
import fvms.team.ejb.PersonBO;

@WebServlet(name="helloServlet", urlPatterns={"/hello"})
 
public class HelloWorldServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @EJB
    PersonBO personBO;
 
    public HelloWorldServlet() {
        super();
    }
 
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
//        out.println("Hello Android !!!!");
    	
    	List<Person> persons = personBO.listAll();
    	Person person = persons.get(0);
    	person.setTeam(null);
    	
    	Gson gson = new GsonBuilder().serializeNulls().create();
    	String gsonString = gson.toJson(person);
    	out.print(gsonString);
    }
}
