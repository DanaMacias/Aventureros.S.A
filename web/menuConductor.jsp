<%-- 
    Document   : menuConductor
    Created on : 30 nov 2025, 19:37:03
    Author     : Dani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuConductor">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="menuConductor">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                <li class="nav-item">
                    <a class="nav-link" href="editarCuentaConductor.jsp">Administrar cuenta</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="registrarVehiculo.jsp">Registrar vehículo</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="modificarVehiculo.jsp">Modificar vehículo</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="eliminarVehiculo.jsp">Eliminar vehículo</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="aceptarServicio.jsp">Aceptar servicio</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="descripcionServicio.jsp">Ver descripción del servicio</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="historialConductor.jsp">Historial del conductor</a>
                </li>

            </ul>

            <a class="btn btn-danger" href="CerrarSesion">Cerrar sesión</a>
        </div>

    </div>
</nav>
