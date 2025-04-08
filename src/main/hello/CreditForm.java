package hello;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import hello.*;
import jakarta.servlet.*;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class CreditForm extends HttpServlet{
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        res.setContentType("text/html");   
        PrintWriter out=res.getWriter();
        try{
            req.getRequestDispatcher("insertion.jsp").forward(req,res);
        }catch(Exception e){
                throw new ServletException(e.getMessage());
        }
   }
    
}

