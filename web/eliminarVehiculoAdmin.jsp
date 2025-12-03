<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="persistencia.vehiculoDAO"%>
<%@page import="java.util.List"%>
<%@page import="negocio.Vehiculo"%>

<%
    if (session.getAttribute("admin") == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    vehiculoDAO dao = new vehiculoDAO();
    List<Vehiculo> lista = dao.listarVehiculos();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Eliminar Vehículo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<h2 class="text-center mt-3">Eliminar Vehículo</h2>

<%@ include file="menuAdministrador.jsp" %>

<div class="container mt-4">

    <% if (request.getAttribute("eliminado") != null) { %>
        <% if ((boolean) request.getAttribute("eliminado")) { %>
            <div class="alert alert-success">Vehículo eliminado correctamente.</div>
        <% } else { %>
            <div class="alert alert-danger">Error al eliminar el vehículo.</div>
        <% } %>
    <% } %>

    <table class="table table-bordered">
        <thead class="table-dark">
            <tr>
                <th>Placa</th>
                <th>Identificación Conductor</th>
                <th>Acción</th>
            </tr>
        </thead>

        <tbody>
            <% for (Vehiculo v : lista) { %>
            <tr>
                <td><%= v.getPlaca() %></td>
                <td><%= v.getIdentificacion_conductor() %></td>
                <td>
                    <a class="btn btn-danger"
                       href="Controlador?accion=eliminarVehiculo&placa=<%= v.getPlaca() %>">
                       Eliminar
                    </a>
                </td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

</body>
</html>
