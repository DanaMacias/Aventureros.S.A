<%-- 
    Document   : menuAdministrador
    Created on : 30 nov 2025, 19:48:52
    Author     : Dani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuAdmin">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="menuAdmin">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                    <li class="nav-item">
                        <a class="nav-link" href="editarCuentaAdmin.jsp">Administrar cuenta</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="eliminarVehiculoAdmin.jsp">Eliminar vehículo</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="gestionarServicios.jsp">Gestionar servicios</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="historialClienteAdmin.jsp">Historial del cliente</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="historialConductorAdmin.jsp">Historial del conductor</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="reportes.jsp">Generar reportes</a>
                    </li>

                </ul>

                <a class="btn btn-danger" href="CerrarSesion">Cerrar sesión</a>
            </div>

        </div>
    </nav>
