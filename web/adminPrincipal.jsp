<%-- 
    Document   : Principal
    Created on : 28/11/2025, 3:43:16 p. m.
    Author     : VivoBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Validar que existe un administrador en sesión
    if (session.getAttribute("admin") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
    
    // Si necesitas el objeto Administrador en scriptlets o para casting:
    negocio.Administrador admin = (negocio.Administrador) session.getAttribute("admin");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <title>Panel de Administrador</title>
    </head>
    <body>
        
        <%@ include file="menuAdministrador.jsp" %>
        
        <div class="container mt-4">
            <h2>Bienvenido, Administrador</h2>
            <hr>
            
            <div class="card p-3">
                <h5 class="card-title">Información de Perfil</h5>
                
                <%-- 🔑 Accediendo a los atributos del objeto 'admin' en la sesión (EL) --%>
                <p><strong>Identificación:</strong> ${admin.identificacion}</p>
                <p><strong>Nombre:</strong> ${admin.nombre}</p>
                <p><strong>Correo Electrónico:</strong> ${admin.correo}</p>
                <p><strong>Clave (Hash/Demo):</strong> ${admin.clave}</p>
                
                <%-- Opcional: Puedes acceder con scriptlets también si lo prefieres,
                pero EL (${}) es lo recomendado en JSP:
                <p><strong>Nombre (Scriptlet):</strong> <%= admin.getNombre() %></p> 
                --%>
            </div>
            
        </div>

    </body>
</html>