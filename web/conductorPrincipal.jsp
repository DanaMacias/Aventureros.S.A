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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <title>Panel de Conductor</title>
    </head>
    <body>

        <h1>Conductor</h1>
        <%@ include file="menuConductor.jsp" %>
        <p><strong>Identificación:</strong> ${conductor.identificacion}</p>

    </body>
</html>
