<%-- 
    Document   : editarCuentaAdmin
    Created on : 30 nov 2025, 21:42:00
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Editar Cuenta Administrador</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="styles/estilos.css">
    </head>
    <body>

        <jsp:include page="menuAdministrador.jsp" />

        <div class="container mt-4" style="max-width: 500px;">
            <h3>Editar Cuenta</h3>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger mt-3">
                    ${errorMessage}
                </div>
            </c:if>

            <c:if test="${not empty successMessage}">
                <div class="alert alert-success mt-3">
                    ${successMessage}
                </div>
            </c:if>

            
            <form action="EditarCuentaAdmin" method="POST">

                <div class="mb-3">
                    <label class="form-label">Nombre</label>
                    <input type="text" name="nombre" class="form-control" 
                           value="${admin.nombre}">
                </div>

                <div class="mb-3">
                    <label class="form-label">Correo</label>
                    <input type="email" name="correo" class="form-control"
                           value="${admin.correo}" >
                </div>

                <div class="mb-3">
                    <label class="form-label">Nueva contraseña</label>
                    <input type="password" name="clave" class="form-control">
                </div>

                <div class="mb-3">
                    <label class="form-label">Confirmar contraseña</label>
                    <input type="password" name="clave2" class="form-control">
                </div>

                <button type="submit" class="btn btn-primary w-100">Guardar cambios</button>

            </form>
        </div>

    </body>
</html>
