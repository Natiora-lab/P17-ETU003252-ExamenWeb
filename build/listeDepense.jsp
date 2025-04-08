<%@ page import="hello.*"  %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.List, hello.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
    List<Depense> Depense = (List<Depense>) request.getAttribute("liste");
    if (Depense == null) { 
%>
        <p style="color: red;">Erreur : l'attribut "valeur" est null.</p>
<% 
    } 
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Depeense</title>
     <link rel="stylesheet" href="style1.css">
</head>
<body>
    <div class="container">
        <h1>Liste des Depeense</h1>
                <table class="employee-table">
                    <thead>
                        <tr>
                            <th>Libelle</th>
                            <th>somme</th>
                            <th>reste</th>
                        </tr>
                    </thead>
                    <tbody>
                    <%
                        for (Depense e : Depense) {
                    %>
                       <tr>
                                <td><%= e.getName() %></td>
                                <td><%= e.getMontant() %></td>
                                <td><%= e.getReste() %></td>
                               
                       </tr>
                    <%
                        }
                    %>
                           
                    </tbody>
                </table>
    </div>
</body>
</html>