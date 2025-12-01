<%-- 
    Document   : menuCliente
    Created on : 30 nov 2025, 18:53:01
    Author     : Dani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#menuCliente">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="menuCliente">

            <ul class="navbar-nav me-auto mb-2 mb-lg-0">

                <li class="nav-item">
                    <a class="nav-link" href="editarCuentaCliente.jsp">Administrar cuenta</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="solicitarServicio.jsp">Solicitar servicio</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="cancelarServicio.jsp">Cancelar servicio</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="historialCliente.jsp">Historial de servicios</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="registrarPago.jsp">Registrar forma de pago</a>
                </li>

            </ul>
            
            <a class="btn btn-danger" href="CerrarSesion">Cerrar sesión</a>

        </div>

    </div>
</nav>



