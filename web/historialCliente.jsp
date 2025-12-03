<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- Si no deseas el formato de moneda, puedes remover la línea anterior y el prefijo 'fmt' --%>

<!DOCTYPE html>
<html>
<head>
    <title>Historial del Cliente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

    <jsp:include page="menuAdministrador.jsp" />

    <div class="container mt-4">

        <h2>Historial del Cliente</h2>
        <hr>

        <h4>Información General</h4>
        <%-- La variable 'cliente' debe venir del controlador: request.setAttribute("cliente", cliente); --%>
        <p><b>Identificación:</b> ${cliente.identificacion}</p>
        <p><b>Nombre:</b> ${cliente.nombre}</p>
        <p><b>Correo:</b> ${cliente.correo}</p>
        <p><b>Dirección:</b> ${cliente.direccion}</p>
        <hr>

        <h4>Teléfonos</h4>
        <%-- La variable 'telefonos' debe venir del controlador: request.setAttribute("telefonos", listaTelefonos); --%>
        <c:choose>
            <c:when test="${not empty telefonos}">
                <ul class="list-group list-group-flush">
                    <c:forEach var="t" items="${telefonos}">
                        <li class="list-group-item">${t.numero}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <p>No se encontraron teléfonos asociados.</p>
            </c:otherwise>
        </c:choose>

        <hr>

        <h4>Servicios Realizados</h4>
        <%-- La variable 'servicios' debe venir del controlador: request.setAttribute("servicios", listaServicios); --%>
        <c:choose>
            <c:when test="${not empty servicios}">
                <div class="table-responsive">
                    <table class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>ID Servicio</th>
                                <th>Origen</th>
                                <th>Destino</th>
                                <th>Conductor</th>
                                <th>Tipo Servicio</th> 
                                <th>Valor Pago</th> 
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${servicios}">
                                <tr>
                                    <td>${s.idServicio}</td>
                                    <td>${s.direccionOrigen}</td>
                                    <td>${s.direccionDestino}</td>
                                    <td>${s.nombreConductor}</td> 
                                    <td>${s.tipoServicioNombre}</td> 
                                    <td>
                                        <fmt:formatNumber value="${s.valorPago}" type="currency" currencySymbol="$" maxFractionDigits="2"/>
                                    </td>
                                    <td>${s.estado}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>
            <c:otherwise>
                <p>No se encontraron servicios realizados por este cliente.</p>
            </c:otherwise>
        </c:choose>

    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>