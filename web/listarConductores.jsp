<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="negocio.Conductor" %>

<%
    List<Conductor> conductores = (List<Conductor>) request.getAttribute("conductores");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Lista de Conductores</title>
    <link rel="stylesheet" 
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>

<body class="bg-light">
<jsp:include page="menuAdministrador.jsp" />
<div class="container mt-4">
    <h2 class="mb-3">Lista de Conductores</h2>

    <table class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Correo</th>
                <th>Acciones</th>
            </tr>
        </thead>

        <tbody>
        <% 
            if (conductores != null) { 
                for (Conductor c : conductores) { 
        %>
                <tr>
                    <td><%= c.getIdentificacion() %></td>
                    <td><%= c.getNombre() %></td>
                    <td><%= c.getCorreo() %></td>
                    <td>
                        <a class="btn btn-info btn-sm"
                           href="Controlador?accion=historialConductor&id=<%= c.getIdentificacion() %>">
                            Ver historial
                        </a>
                    </td>
                </tr>
        <% 
                } 
            } 
        %>
        </tbody>
    </table>
</div>

</body>
</html>
