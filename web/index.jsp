<%-- 
    Document   : index
    Created on : 28/11/2025, 1:40:43 p. m.
    Author     : VivoBook
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <title>JSP Page</title>
    <style>
        .gradient-custom {
            background: #5d4037;
            background: -webkit-linear-gradient(to right, #d7ccc8, #5d4037);
            background: linear-gradient(to right, #d7ccc8, #5d4037);
        }
        .form-white .form-control {
            color: #fff;
            background-color: transparent; 
            border: 1px solid #fff;
        }
        
        .form-white .form-control:focus {
            color: #fff;
            background-color: transparent;
            border-color: #d7ccc8; 
            box-shadow: 0 0 0 0.25rem rgba(215, 204, 200, 0.25);
        }

        .form-white .form-label {
            color: #fff;
            margin-top: 0.5rem;
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
                    <div class="col-12 col-md-8 col-lg-5 col-xl-4">
                        <div class="card bg-dark text-white" style="border-radius: 1rem;">
                            <div class="card-body p-5 text-center">

                                <div class="mb-md-6 mt-md-4 pb-3">

                                    <h2 class="fw-bold mb-2 text-uppercase">Iniciar Sesión</h2>
                                    <p class="text-white-50 mb-4">¡Por favor ingresa tu usuario y contraseña!</p>
                                    <form action="Controlador" method="POST">

                                        <div class="form-outline form-white mb-4">
                                            <input type="email" name="email" id="typeEmailX" class="form-control form-control-lg" placeholder="nombre@ejemplo.com" required />
                                            <label class="form-label" for="typeEmailX">Correo Electrónico</label>
                                        </div>

                                        <div class="form-outline form-white mb-4">
                                            <input type="password" name="password" id="typePasswordX" class="form-control form-control-lg" placeholder="******" required />
                                            <label class="form-label" for="typePasswordX">Contraseña</label>
                                        </div>

                                        <button class="btn btn-outline-light btn-lg px-5 mt-3" type="submit" name="accion" value="Ingresar">Ingresar</button>

                                    </form>
                                </div>
                                
                                <% 
                                    String error = (String) request.getAttribute("error");
                                    if (error != null) { 
                                %>
                                    <div class="alert alert-danger text-center" role="alert">
                                        <%= error %>
                                    </div>
                                <% 
                                    } 
                                %>
                                
                                <div>
                                    <p class="mb-0">¿No tienes una cuenta? <a href="Registrar.jsp" class="text-white-50 fw-bold">Regístrate</a></p>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.min.js" integrity="sha384-G/EV+4j2dNv+tEPo3++6LCgdCROaejBqfUeNjuKAiuXbjrxilcCdDz6ZAVfHWe1Y" crossorigin="anonymous"></script>
    </body>
    
</html>
