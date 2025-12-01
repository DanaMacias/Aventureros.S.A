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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <title>Panel de Administrador</title>
    </head>
    <body>

        <h1>Administrador</h1>
        <%@ include file="menuAdministrador.jsp" %>
        <p><strong>Identificación:</strong> ${admin.identificacion}</p>

    </body>
</html>

