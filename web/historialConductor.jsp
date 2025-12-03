<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Historial del Conductor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

    <jsp:include page="menuAdministrador.jsp" />

    <div class="container mt-4">

        <h2>Historial del Conductor</h2>
        <hr>

        <!-- DATOS DEL CONDUCTOR -->
        <h4>Información General</h4>
        <p><b>Nombre:</b> ${conductor.nombre}</p>
        <p><b>Correo:</b> ${conductor.correo}</p>
        <p><b>Dirección:</b> ${conductor.direccion}</p>

        <hr>

        <!-- TELÉFONOS -->
        <h4>Teléfonos</h4>
        <ul>
            <c:forEach var="t" items="${telefonos}">
                <li>${t}</li>
            </c:forEach>
        </ul>

        <hr>

        <!-- VEHÍCULOS -->
        <h4>Vehículos Asociados</h4>

        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Placa</th>
                    <th>Marca</th>
                    <th>Modelo</th>
                    <th>Servicio</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="v" items="${vehiculos}">
                    <tr>
                        <td>${v.placa}</td>
                        <td>${v.marca}</td>
                        <td>${v.modelo}</td>
                        <td>${v.servicio}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>

</body>
</html>
