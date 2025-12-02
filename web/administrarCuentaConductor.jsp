<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="negocio.Conductor" %>
<%@ page import="negocio.Genero" %>
<%@ page import="negocio.Nacionalidad" %>
<%@ page import="persistencia.generoDAO" %>
<%@ page import="persistencia.nacionalidadDAO" %>

<%
    Conductor conductor = (Conductor) session.getAttribute("conductor");
    if (conductor == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    java.util.List<Genero> generos = new generoDAO().listarGeneros();
    java.util.List<Nacionalidad> nacionalidades = new nacionalidadDAO().listarNacionalidades();
%>
 
<!DOCTYPE html>
<html>
<head>
    <title>Administrar Cuenta</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>

<body class="bg-light">

<div class="container mt-4">
    <h2 class="mb-3">Administrar Cuenta</h2>
    <% if (request.getAttribute("mensaje") != null) { %>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <%= request.getAttribute("mensaje") %>
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
<% } %>
    <form action="Controlador" method="post" enctype="multipart/form-data">
        <input type="hidden" name="accion" value="actualizar">

        <!-- NOMBRE -->
        <div class="mb-3">
            <label class="form-label">Nombre completo</label>
            <input type="text" class="form-control"
                   name="nombre" value="<%= conductor.getNombre() %>" required>
        </div>

        <!-- EMAIL -->
        <div class="mb-3">
            <label class="form-label">Correo</label>
            <input type="email" class="form-control"
                   name="correo" value="<%= conductor.getCorreo() %>" required>
        </div>

        <!-- DIRECCIÓN -->
        <div class="mb-3">
            <label class="form-label">Dirección</label>
            <input type="text" class="form-control"
                   name="direccion" value="<%= conductor.getDireccion() %>" required>
        </div>

        <!-- FOTO -->
        <div class="mb-3">
            <label class="form-label">Cambiar foto</label><br>
            <img src="<%= conductor.getFoto() %>" width="120" class="mb-2">
            <input type="file" class="form-control" name="foto">
        </div>

        <!-- GÉNERO -->
        <div class="mb-3">
            <label class="form-label">Género</label>
            <select name="genero" class="form-select">
                <% for (Genero g : generos) { %>
                    <option value="<%= g.getId() %>"
                        <%= (conductor.getGenero() != null &&
                             conductor.getGenero().getId() == g.getId()) ? "selected" : "" %>>
                        <%= g.getGenero() %>
                    </option>
                <% } %>
            </select>
        </div>

        <!-- NACIONALIDAD -->
        <div class="mb-3">
            <label class="form-label">Nacionalidad</label>
            <select name="nacionalidad" class="form-select">
                <% for (Nacionalidad n : nacionalidades) { %>
                    <option value="<%= n.getId() %>"
                        <%= (conductor.getNacionalidad() != null &&
                             conductor.getNacionalidad().getId() == n.getId()) ? "selected" : "" %>>
                        <%= n.getNacionalidad() %>
                    </option>
                <% } %>
            </select>
        </div>

        <!-- CONTRASEÑA -->
        <div class="mb-3">
            <label class="form-label">Cambiar contraseña (opcional)</label>
            <input type="password" class="form-control" name="clave">
        </div>

        <button type="submit" class="btn btn-primary">Guardar cambios</button>
    </form>
</div>

</body>
</html>
