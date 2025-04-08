<%@ page import="hello.*"  %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.List, hello.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% 
    List<Departement> departement = (List<Departement>) request.getAttribute("valeur");
    Employer emp =(Employer) request.getAttribute("employer");
    Departement dep =(Departement) request.getAttribute("Departement");
    if (departement == null || emp == null || dep == null)  { 
%>
        <p style="color: red;">Erreur : l'attribut  est null.</p>
<% 
    } 
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
     <link rel="stylesheet" href="style.css">
    <title>Employe</title>
</head>
<body>
    <form name="form1" method="post" action="/departement/form1">
        <h1>Modification d'un employe</h1>
        <p>Nom:<input type="text" name="nom" value ="<%= emp.getName() %>"/></p>
        <input type="hidden" name="id" value="<%= emp.getId() %>"/>
        <p>Departement
            <select name="dep">
               <option value="<%= dep.getId() %>"><%= dep.getName() %></option>
            <%
                for (Departement e : departement) {
                    if(e.getId() != dep.getId()) {
            %>
                <option value="<%= e.getId() %>"> <%= e.getName() %> </option>
            <%
                }}
            %>
            </select>
        </p>
        <p><input type="submit" value="valider"/></p>
        <a href="/departement/form1">Liste employe</a>
    </form>
</body>
</html>

   
