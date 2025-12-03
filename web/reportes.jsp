<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="negocio.Conductor" %>

<!DOCTYPE html>
<html>
<head>
    <title>Generar Reportes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Estilo opcional para limitar la altura y tener scroll en las celdas de vehículos si hay muchos */
        .vehiculos-cell {
            max-height: 100px;
            overflow-y: auto;
        }
        .list-unstyled {
            padding-left: 0;
            list-style: none;
        }
        .small {
            font-size: 0.85em;
        }
    </style>
</head>
<body class="bg-light">

    <jsp:include page="menuAdministrador.jsp" />

    <div class="container mt-4">
        <h2>Generación de Reportes</h2>
        <hr>

        <form action="Controlador" method="GET" class="mb-4 p-3 border rounded bg-white">
            <input type="hidden" name="accion" value="generarReportes">
            
            <div class="row g-3 align-items-end">
                <div class="col-md-6">
                    <label for="tipoReporte" class="form-label">Seleccionar Tipo de Reporte</label>
                    <select class="form-select" id="tipoReporte" name="tipoReporte" required>
                        <option value="">Seleccione...</option>
                        <option value="conductoresCompleto" ${param.tipoReporte == 'conductoresCompleto' ? 'selected' : ''}>Listado Completo de Conductores</option>
                    </select>
                </div>
                <div class="col-md-6">
                    <button type="submit" class="btn btn-primary w-100">Generar Reporte</button>
                </div>
            </div>
        </form>

        <hr>

        <c:if test="${not empty listaReporte}">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0">Resultados del Reporte</h5>
                </div>
                <div class="card-body">

                    <c:if test="${reporteTipo == 'conductoresCompleto'}">
                        <h4>Listado de Conductores con Historial de Vehículos</h4>
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered align-middle table-sm">
                                <thead class="table-dark">
                                    <tr>
                                        <th>ID</th>
                                        <th>Nombre</th>
                                        <th>Correo</th>
                                        <th>Nacionalidad</th>
                                        <th>Género</th>
                                        <th style="min-width: 400px;">Vehículos (Placa | Marca | Modelo | Servicio)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="c" items="${listaReporte}">
                                        <tr>
                                            <td>${c.identificacion}</td>
                                            <td>${c.nombre}</td>
                                            <td>${c.correo}</td>
                                            <td>${c.nacionalidad.nacionalidad}</td>
                                            <td>${c.genero.genero}</td>
                                            
                                            <td class="vehiculos-cell">
                                                <c:choose>
                                                    <c:when test="${empty c.vehiculos}">
                                                        <span class="text-muted fst-italic">No tiene vehículos asignados en el historial.</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <ul class="list-unstyled mb-0 small">
                                                            <c:forEach var="v" items="${c.vehiculos}">
                                                                <li>
                                                                    <span class="fw-bold text-primary">${v.placa}</span> | ${v.marca} | ${v.modelo} | ${v.servicio}
                                                                </li>
                                                            </c:forEach>
                                                        </ul>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                    <c:if test="${reporteTipo == 'conductores'}">
                        <h4>Listado de Conductores (Básico)</h4>
                        <div class="table-responsive">
                            <table class="table table-striped table-bordered align-middle">
                                <thead class="table-dark">
                                    <tr>
                                        <th>ID</th>
                                        <th>Nombre</th>
                                        <th>Correo</th>
                                        <th>Nacionalidad</th>
                                        <th>Género</th>
                                        <th>Dirección</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="c" items="${listaReporte}">
                                        <tr>
                                            <td>${c.identificacion}</td>
                                            <td>${c.nombre}</td>
                                            <td>${c.correo}</td>
                                            <td>${c.nacionalidad.nacionalidad}</td>
                                            <td>${c.genero.genero}</td>
                                            <td>${c.direccion}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                    
                </div>
            </div>
        </c:if>
        
        <c:if test="${empty listaReporte and not empty param.tipoReporte}">
            <div class="alert alert-warning">No se encontraron resultados para el reporte seleccionado.</div>
        </c:if>

    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>