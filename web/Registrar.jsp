<%-- 
    Document   : Registrar
    Created on : 28/11/2025, 3:46:01 p. m.
    Author     : VivoBook
--%>

<%@page import="negocio.Genero"%>
<%@page import="persistencia.generoDAO"%>
<%@page import="persistencia.nacionalidadDAO"%>
<%@ page import="java.util.List" %>
<%@page import="negocio.Nacionalidad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <title>JSP Page</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    
    <style>
        .gradient-custom {
            background: #5d4037;
            background: -webkit-linear-gradient(to right, #d7ccc8, #5d4037);
            background: linear-gradient(to right, #d7ccc8, #5d4037);
        }
       
        .form-white .form-control,
        .form-white .form-select {
            color: #fff;
            background-color: transparent; 
            border: 1px solid #fff;
        }
        .form-white .form-select {
            color: #000 !important; 
        }

        .form-white .form-control:focus,
        .form-white .form-select:focus {
            color: #fff;
            background-color: transparent;
            border-color: #d7ccc8;
            box-shadow: 0 0 0 0.25rem rgba(215, 204, 200, 0.25);
        }

        .form-white .form-label {
            color: #fff;
            margin-top: 0;
            margin-bottom: 0.5rem; 
            padding-left: 0.25rem;
        }
        ::placeholder { 
            color: rgba(255, 255, 255, 0.7) !important;
            opacity: 1; 
        }
    </style>
</head>
<body class="gradient-custom">
    <section class="vh-100">
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                    <div class="card bg-dark text-white" style="border-radius: 1rem;">
                        <div class="card-body p-4 text-center">

                            <div class="mb-md-4 mt-md-3 pb-3">

                                <h2 class="fw-bold mb-2 text-uppercase">Crear Cuenta</h2>
                                <p class="text-white-50 mb-4">Selecciona tu rol y completa el formulario.</p>
                                <form action="Controlador" method="post" enctype="multipart/form-data">
                                    <div class="form-white mb-4">
                                        <label class="form-label text-start d-block" for="roleSelector">Rol de Usuario</label>
                                        <select class="form-select form-white" id="roleSelector" name="rol" required>
                                            <option value="" disabled selected>-- Selecciona tu Rol --</option>
                                            <option value="cliente">Cliente</option>
                                            <option value="conductor">Conductor</option>
                                        </select>
                                    </div>
                                   
                                    <div class="form-white mb-4">
                                        <label class="form-label text-start d-block" for="idNumber">Número de Identificación</label>
                                        <input type="text" id="idNumber" class="form-control" placeholder="12345678" required name="identificacion"/>
                                    </div>
                                    <div class="form-white mb-4">
                                        <label class="form-label text-start d-block" for="fullName">Nombre Completo</label>
                                        <input type="text" id="fullName" class="form-control" placeholder="Juan Pérez" required name="nombre_completo"/>
                                    </div>
                                    
                                    <div class="form-white mb-4">
                                        <label class="form-label text-start d-block" for="email">Correo Electrónico</label>
                                        <input type="email" id="email" class="form-control" placeholder="correo@ejemplo.com" required name="correo"/>
                                    </div>
                                    <div class="form-white mb-4">
                                        <label class="form-label text-start d-block" for="password">Clave</label>
                                        <input type="password" id="password" class="form-control" placeholder="******" required name="clave"/>
                                    </div>
                                    
                                    <div class="form-white mb-4">
                                        <label class="form-label text-start d-block" for="address">Dirección</label>
                                        <input type="text" id="address" class="form-control" placeholder="Calle 1 # 2-34" required name="direccion"/>
                                    </div>
                                    
                                    <div class="form-white mb-4">
                                        <label class="form-label text-start d-block" for="tel">Telefono</label>
                                        <input type="text" id="tel" class="form-control" placeholder="3103123456" required name="tel"/>
                                    </div>

                                    <div class="form-white mb-4">
                                        <label class="form-label text-start d-block" for="gender">Género</label>
                                        <select class="form-select form-white" id="genero" name="genero" required>
                                            <%
                                                generoDAO gen = new generoDAO();
                                                List<Genero> listaG = gen.listarGeneros();
                                            %>
                                            <option value="" disabled selected>-- Selecciona Género --</option>
                                            <%
                                                for(Genero g : listaG){
                                            %>
                                            <option value="<%= g.getId()%>"><%= g.getGenero()%></option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                    
                                    <div class="form-white mb-4">
                                        <label class="form-label text-start d-block" for="nationality">Nacionalidad</label>
                                        <select class="form-select form-white" id="nacionalidad" name="nacionalidad" required>
                                        <% nacionalidadDAO nac = new nacionalidadDAO();
                                            List<Nacionalidad> listaN = nac.listarNacionalidades();
                                        %>
    
                                            <option value="" disabled selected>-- Selecciona Nacionalidad --</option>
                                        <%
                                            for(Nacionalidad n : listaN) {
                                        %>
                                            <option value="<%= n.getId()%>"><%= n.getNacionalidad()%></option>
                                        <% 
                                            }
                                        %>
                                        </select>
                                    </div>

                                    <div id="driver-fields" style="display: none;">
                                        <div class="form-white mb-4">
                                            <label class="form-label text-start d-block" for="driverPhoto">Foto (Solo Conductor)</label>
                                            <input type="file" id="driverPhoto" class="form-control" name="foto_conductor"/>
                                        </div>
                                    </div>

                                        <button class="btn btn-outline-light btn-lg px-5 mt-3" name="accion" value="Registrar" type="submit">Registrarse</button>
                                
                                </form>

                            </div>

                            <div>
                                <p class="mb-0">¿Ya tienes una cuenta? <a href="index.jsp" class="text-white-50 fw-bold">Iniciar Sesión</a></p>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const roleSelector = document.getElementById('roleSelector');
            const driverFields = document.getElementById('driver-fields');
            const driverPhotoInput = document.getElementById('driverPhoto');

            roleSelector.addEventListener('change', (event) => {
                if (event.target.value === 'conductor') {
                    driverFields.style.display = 'block';
                    driverPhotoInput.setAttribute('required', 'required');
                } else {
                    driverFields.style.display = 'none';
                    driverPhotoInput.removeAttribute('required');
                    driverPhotoInput.value = ''; // Limpiar el input si se oculta
                }
            });
        });
    </script>

    <% 
        Boolean mensaje = (Boolean) request.getAttribute("msg"); 
        if (mensaje != null) { 
    %>
    
        <div class="modal fade" id="modalExito" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content border-0 shadow">
                    <div class="modal-header bg-success text-white">
                        <h5 class="modal-title">Registro exitoso</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <img src="img/2000008.jpg">
                        <p>El usuario ha sido registrado correctamente.</p>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-success" data-bs-dismiss="modal">Aceptar</button>
                    </div>
                </div>
            </div>
        </div>
            
        <div class="modal fade" id="modalError" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content border-0 shadow">
                    <div class="modal-header bg-danger text-white">
                        <h5 class="modal-title">Error al registrar</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <p>Ocurrió un error al registrar el usuario. Intente nuevamente.</p>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-danger" data-bs-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function() {
                var modal = new bootstrap.Modal(document.getElementById("<%= mensaje ? "modalExito" : "modalError" %>"));
                modal.show();
            });
        </script>
    <% } %>

</body>
</html>
