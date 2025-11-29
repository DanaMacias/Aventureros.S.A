<%-- 
    Document   : Principal
    Created on : 28/11/2025, 3:43:16 p. m.
    Author     : VivoBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("cliente") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Panel de Cliente</title>
    </head>
    <body>

        <h1>Bienvenido al Panel de Cliente</h1>

        <p><strong>Identificación:</strong> ${cliente.identificacion}</p>

    </body>
</html>

