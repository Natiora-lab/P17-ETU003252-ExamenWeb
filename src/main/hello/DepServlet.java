package hello;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import hello.BaseModele;
import jakarta.servlet.*;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class DepServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)  throws ServletException, IOException {
       res.setContentType("text/html");   
       PrintWriter out=res.getWriter();
       double montant = Double.parseDouble(req.getParameter("montant"));
       int id_credit = Integer.parseInt((req.getParameter("dep")));
       
        try{
            depenseDAO fonction=new depenseDAO();
            BaseModele Dep = new Depense(1,id_credit,"",montant,0);
            fonction.save(Dep);
            out.println("Depense enregistre");
        }catch(Exception e){
                throw new ServletException(e.getMessage());
        }
    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        res.setContentType("text/html");   
        PrintWriter out=res.getWriter();
        try{
                String idParam = req.getParameter("id");
                depenseDAO fonction=new depenseDAO();
                List<BaseModele> liste= fonction.findAll();
                req.setAttribute("liste", liste);
                req.getRequestDispatcher("listeDepense.jsp").forward(req,res);
               
        }catch(Exception e){
                throw new ServletException(e.getMessage());
        }
   }
}   
