<%-- 
    Document   : Principal
    Created on : 28/11/2025, 3:43:16 p. m.
    Author     : VivoBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Validar que existe un administrador en sesión
    if (session.getAttribute("admin") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Panel de Administrador</title>
    </head>
    <body>

        <h1>Bienvenido al Panel de Administrador</h1>

        <p><strong>Identificación:</strong> ${admin.identificacion}</p>

    </body>
</html>

