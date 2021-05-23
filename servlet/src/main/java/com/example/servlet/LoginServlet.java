package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    private boolean makeQueryToDB(String username, String password) {
        String databaseUrl = "jdbc:mysql://localhost:3306/hungdb?user=" + "project_java" + "&password=" + "160817";
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(databaseUrl);
            System.out.println("Connected to database");
            PreparedStatement st = conn.prepareStatement("SELECT * FROM login WHERE username= ? AND password = ?");
            st.setString(1, username);
            st.setString(2, password);
            ResultSet res = st.executeQuery();
            if (res.next())
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(request.getServletPath());
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println(username);
        System.out.println(password);

        if(makeQueryToDB(username, password)){
            response.sendRedirect(request.getContextPath() + "/Calculator.jsp");
        } else{
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
        }

    }

}