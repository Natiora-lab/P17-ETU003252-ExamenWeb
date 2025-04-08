package hello;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import hello.BaseModele;
import java.sql.Connection;
import jakarta.servlet.*;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class CreditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)  throws ServletException, IOException {
       res.setContentType("text/html");   
       PrintWriter out=res.getWriter();
       String Libelle = req.getParameter("nom");
       double Montant = Double.parseDouble((req.getParameter("montant")));
        try{
                creditDAO fonction=new creditDAO();
                BaseModele Credit = new Credit(1,Libelle,Montant,0);
                fonction.save(Credit);
                out.println("Credit enregistre");
        }catch(Exception e){
                throw new ServletException(e.getMessage());
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        res.setContentType("text/html");   
        PrintWriter out=res.getWriter();
        try{
                String idParam = req.getParameter("id");
                creditDAO fonction=new creditDAO();
                List<BaseModele> liste= fonction.findAll();
                req.setAttribute("liste", liste);
                req.getRequestDispatcher("listeCredit.jsp").forward(req,res);
                   
        }catch(Exception e){
                throw new ServletException(e.getMessage());
        }
   }
}   
