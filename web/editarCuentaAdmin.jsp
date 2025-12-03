<%-- 
    Document   : editarCuentaAdmin
    Created on : 30 nov 2025, 21:42:00
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // Validar que el admin está en sesión
    negocio.Administrador admin = (negocio.Administrador) session.getAttribute("admin");
    if (admin == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    String errorMessage = (String) request.getAttribute("errorMessage");
    String successMessage = (String) request.getAttribute("successMessage");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Editar Cuenta Administrador</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <jsp:include page="menuAdministrador.jsp" />

        <div class="container mt-4" style="max-width: 500px;">
            <h3>Editar Cuenta</h3>

            <% if (errorMessage != null) { %>
                <div class="alert alert-danger mt-3">
                    <%= errorMessage %>
                </div>
            <% } %>

            <% if (successMessage != null) { %>
                <div class="alert alert-success mt-3">
                    <%= successMessage %>
                </div>
            <% } %>

            <form action="Controlador?accion=actualizarAdmin" method="POST">

                <div class="mb-3">
                    <label class="form-label">Nombre</label>
                    <input type="text" name="nombre" class="form-control" 
                           value="<%= admin.getNombre() %>">
                </div>

                <div class="mb-3">
                    <label class="form-label">Correo</label>
                    <input type="email" name="correo" class="form-control"
                           value="<%= admin.getCorreo() %>">
                </div>

                <div class="mb-3">
                    <label class="form-label">Nueva contraseña</label>
                    <input type="password" name="clave" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Confirmar contraseña</label>
                    <input type="password" name="clave2" class="form-control">
                </div>

                <button type="submit" name="accion" value="actualizarAdmin" class="btn btn-primary">
                    Guardar cambios
                </button>


            </form>
        </div>

    </body>
</html>
