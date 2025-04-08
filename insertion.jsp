<%@ page import="hello.*"  %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.List, hello.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% 
    List<Credit> Credit = (List<Credit>) request.getAttribute("valeur");
    if (Credit == null)  { 
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
    <title>Depense</title>
</head>
<body>
    <form name="form1" method="post" action="/ETU003252/form1">
        <h1>Enregistrement de Depense</h1>
        <p>Credit
            <select name="dep">
            <%
                for (Credit e : Credit) {
            %>
                <option value="<%= e.getId() %>"> <%= e.getNom() %> </option>
            <%
                }
            %>
            </select>
        </p>
        <p>Montant:<input type="number" name="montant"/></p>

        <p><input type="submit" value="valider"/></p>
        <a href="/ETU003252/form1">Liste Depense</a>
    </form>
</body>
</html>

   
