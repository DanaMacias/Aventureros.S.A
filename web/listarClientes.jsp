<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="negocio.Cliente" %>
<%@ page import="negocio.Conductor" %>
<%-- 
    El controlador debe haber puesto la lista de clientes en el atributo "clientes".
    List<Cliente> lista = dao.listarClientes();
    request.setAttribute("clientes", lista);
--%>

<%
    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Lista de Clientes</title>
    <link rel="stylesheet" 
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>

<body class="bg-light">
<jsp:include page="menuAdministrador.jsp" />
<div class="container mt-4">
    <h2 class="mb-3">Lista de Clientes</h2>

    <table class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>Identificación</th>
                <th>Nombre</th>
                <th>Correo</th>
                <th>Acciones</th>
            </tr>
        </thead>

        <tbody>
        <% 
            if (clientes != null) { 
                for (Cliente c : clientes) { 
        %>
                <tr>
                    <td><%= c.getIdentificacion() %></td>
                    <td><%= c.getNombre() %></td>
                    <td><%= c.getCorreo() %></td>
                    <td>
                        <%-- El ID de cliente es su Identificación --%>
                        <a class="btn btn-info btn-sm"
                           href="Controlador?accion=historialCliente&id=<%= c.getIdentificacion() %>">
                             Ver historial
                        </a>
                    </td>
                </tr>
        <% 
                } 
            } else {
        %>
                <tr>
                    <td colspan="4" class="text-center">No hay clientes registrados.</td>
                </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>

</body>
</html>