<%-- 
    Document   : conductorPrincipal
    Created on : 28/11/2025, 7:52:42 p. m.
    Author     : VivoBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("conductor") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Panel de Conductor</title>
    </head>
    <body>

        <h1>Bienvenido al Panel de Conductor</h1>

        <p><strong>Identificación:</strong> ${conductor.identificacion}</p>

    </body>
</html>
