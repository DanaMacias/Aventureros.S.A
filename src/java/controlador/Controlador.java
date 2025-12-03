package controlador;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import negocio.Administrador;
import negocio.Cliente;
import negocio.Conductor;
import negocio.Genero;
import negocio.Nacionalidad;
import negocio.TelefonoCliente;
import negocio.TelefonoConductor;
import persistencia.administradorDAO;
import persistencia.clienteDAO;
import persistencia.conductorDAO;
import persistencia.generoDAO;
import persistencia.nacionalidadDAO;
import persistencia.telefonoClienteDAO;
import persistencia.telefonoConductorDAO;
import persistencia.vehiculoDAO;

@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
@MultipartConfig
public class Controlador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String accion = request.getParameter("accion");

        // ----------------------------------------------------------------------
        // LOGIN
        // ----------------------------------------------------------------------
        if (accion.equals("Ingresar")) {

            String correo = request.getParameter("email");
            String clave = request.getParameter("password");

            administradorDAO adminDAO = new administradorDAO();
            Administrador admin = new Administrador();
            int rAdmin = adminDAO.validarLogin(correo, clave, admin);

            if (rAdmin > 0) {
                request.getSession().setAttribute("admin", admin);
                request.getRequestDispatcher("adminPrincipal.jsp").forward(request, response);
            } else {

                conductorDAO conductorDAO = new conductorDAO();
                Conductor conductor = new Conductor();
                int rConductor = conductorDAO.validarLogin(correo, clave, conductor);

                if (rConductor > 0) {
                    request.getSession().setAttribute("conductor", conductor);
                    request.getRequestDispatcher("conductorPrincipal.jsp").forward(request, response);
                } else {

                    clienteDAO clienteDAO = new clienteDAO();
                    Cliente cliente = new Cliente();
                    int rCliente = clienteDAO.validarLogin(correo, clave, cliente);

                    if (rCliente > 0) {
                        request.getSession().setAttribute("cliente", cliente);
                        request.getRequestDispatcher("clientePrincipal.jsp").forward(request, response);
                    } else {
                        request.setAttribute("error", "Credenciales incorrectas");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                }
            }
        }

        // ----------------------------------------------------------------------
        // REGISTRO
        // ----------------------------------------------------------------------
        else if (accion.equals("Registrar")) {

            String usuario = request.getParameter("rol");

            // --------------------- REGISTRAR CLIENTE ----------------------
            if (usuario.equals("cliente")) {

                Cliente c = new Cliente();
                c.setIdentificacion(Integer.parseInt(request.getParameter("identificacion")));
                c.setNombre(request.getParameter("nombre_completo"));
                c.setDireccion(request.getParameter("direccion"));
                c.setCorreo(request.getParameter("correo"));
                c.setClave(request.getParameter("clave"));

                TelefonoCliente t = new TelefonoCliente();
                t.setIdentificacion_cliente(c.getIdentificacion());
                t.setNumero(request.getParameter("tel"));

                // genero
                int idG = Integer.parseInt(request.getParameter("genero"));
                Genero genero = new generoDAO().obtenerPorId(idG);
                c.setGenero(genero);

                // nacionalidad
                int idN = Integer.parseInt(request.getParameter("nacionalidad"));
                Nacionalidad nacionalidad = new nacionalidadDAO().obtenerPorId(idN);
                c.setNacionalidad(nacionalidad);

                clienteDAO cdao = new clienteDAO();

                if (cdao.registrarCliente(c)) {
                    new telefonoClienteDAO().registrarTelefonoCliente(t);
                    request.setAttribute("msg", true);
                } else {
                    request.setAttribute("msg", false);
                }

                request.getRequestDispatcher("Registrar.jsp").forward(request, response);
            }

            // --------------------- REGISTRAR CONDUCTOR ----------------------
            else {

                Conductor c = new Conductor();
                c.setIdentificacion(Integer.parseInt(request.getParameter("identificacion")));
                c.setNombre(request.getParameter("nombre_completo"));
                c.setDireccion(request.getParameter("direccion"));
                c.setCorreo(request.getParameter("correo"));
                c.setClave(request.getParameter("clave"));

                TelefonoConductor t = new TelefonoConductor();
                t.setIdentificacion_conductor(c.getIdentificacion());
                t.setNumero(request.getParameter("tel"));

                // genero
                int idG = Integer.parseInt(request.getParameter("genero"));
                c.setGenero(new generoDAO().obtenerPorId(idG));

                // nacionalidad
                int idN = Integer.parseInt(request.getParameter("nacionalidad"));
                c.setNacionalidad(new nacionalidadDAO().obtenerPorId(idN));

                // FOTO
                Part foto = request.getPart("foto_conductor");

                if (foto != null && foto.getSize() > 0) {

                    String nombreArchivo = Paths.get(foto.getSubmittedFileName()).getFileName().toString();
                    String extension = "";

                    int i = nombreArchivo.lastIndexOf(".");
                    if (i > 0) extension = nombreArchivo.substring(i);

                    String nombreFinal = c.getIdentificacion() + extension;

                    String rutaImg = getServletContext().getRealPath("/img");
                    File carpeta = new File(rutaImg);

                    if (!carpeta.exists()) carpeta.mkdirs();

                    String rutaCompleta = rutaImg + File.separator + nombreFinal;
                    foto.write(rutaCompleta);

                    c.setFoto("img/" + nombreFinal);
                }

                conductorDAO cdao = new conductorDAO();

                if (cdao.registrarConductor(c)) {
                    new telefonoConductorDAO().registrarTelefonoConductor(t);
                    request.setAttribute("msg", true);
                } else {
                    request.setAttribute("msg", false);
                }

                request.getRequestDispatcher("Registrar.jsp").forward(request, response);
            }
        }
        
        // ----------------------------------------------------------------------
        // ABRIR FORMULARIO EDITAR CUENTA ADMIN
        // ----------------------------------------------------------------------
        else if (accion.equals("editarCuentaAdmin")) {

            Administrador admin = (Administrador) request.getSession().getAttribute("admin");

            if (admin == null) {
                response.sendRedirect("index.jsp");
                return;
            }

            request.setAttribute("admin", admin);
            request.getRequestDispatcher("editarCuentaAdmin.jsp").forward(request, response);
        } else if (accion.equals("actualizarAdmin")) {

            Administrador admin = (Administrador) request.getSession().getAttribute("admin");

            if (admin == null) {
                response.sendRedirect("index.jsp");
                return;
            }

            // Recibir datos del formulario
            String nuevoNombre = request.getParameter("nombre");
            String nuevoCorreo = request.getParameter("correo");
            String nuevaClave = request.getParameter("clave");
            String confirmarClave = request.getParameter("clave2");

            // Validaciones básicas
            if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
                request.setAttribute("errorMessage", "El nombre no puede estar vacío.");
                request.getRequestDispatcher("editarCuentaAdmin.jsp").forward(request, response);
                return;
            }

            if (nuevoCorreo == null || nuevoCorreo.trim().isEmpty()) {
                request.setAttribute("errorMessage", "El correo no puede estar vacío.");
                request.getRequestDispatcher("editarCuentaAdmin.jsp").forward(request, response);
                return;
            }

            // Actualizar clave solo si el usuario la escribe
            if (nuevaClave != null && !nuevaClave.trim().isEmpty()) {

                if (!nuevaClave.equals(confirmarClave)) {
                    request.setAttribute("errorMessage", "Las contraseñas no coinciden.");
                    request.getRequestDispatcher("editarCuentaAdmin.jsp").forward(request, response);
                    return;
                }

                admin.setClave(nuevaClave);
            }

            // Actualizar nombre y correo
            admin.setNombre(nuevoNombre);
            admin.setCorreo(nuevoCorreo);

            administradorDAO dao = new administradorDAO();
            boolean actualizado = dao.actualizarAdministrador(admin);

            if (actualizado) {
                request.setAttribute("successMessage", "Datos actualizados correctamente.");
            } else {
                request.setAttribute("errorMessage", "Error al actualizar.");
            }

            // Refrescar sesión
            request.getSession().setAttribute("admin", admin);

            request.getRequestDispatcher("editarCuentaAdmin.jsp").forward(request, response);
        }

        // ----------------------------------------------------------------------
        // ELIMINAR VEHICULO ADMIN
        // ----------------------------------------------------------------------
        
        else if (accion.equals("eliminarVehiculo")) {

            String placa = request.getParameter("placa");

            vehiculoDAO dao = new vehiculoDAO();
            boolean eliminado = dao.eliminarVehiculo(placa);

            request.setAttribute("eliminado", eliminado);
            request.getRequestDispatcher("eliminarVehiculoAdmin.jsp").forward(request, response);
        }



        // ----------------------------------------------------------------------
        // ABRIR FORMULARIO ADMINISTRAR CUENTA
        // ----------------------------------------------------------------------
        else if (accion.equals("editarCuenta")) {

            Conductor conductor = (Conductor) request.getSession().getAttribute("conductor");

            if (conductor == null) {
                response.sendRedirect("index.jsp");
                return;
            }

            request.setAttribute("conductor", conductor);
            request.getRequestDispatcher("administrarCuentaConductor.jsp").forward(request, response);
        }

        // ----------------------------------------------------------------------
        // ACTUALIZAR DATOS DEL CONDUCTOR
        // ----------------------------------------------------------------------
        else if (accion.equals("actualizar")) {
        Conductor c = (Conductor) request.getSession().getAttribute("conductor");

        if (c == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        c.setNombre(request.getParameter("nombre"));
        c.setDireccion(request.getParameter("direccion"));
        c.setCorreo(request.getParameter("correo"));

        // contraseña opcional
        String clave = request.getParameter("clave");
        if (clave != null && !clave.trim().isEmpty()) {
            c.setClave(clave);
        }

        // genero
        int idG = Integer.parseInt(request.getParameter("genero"));
        c.setGenero(new generoDAO().obtenerPorId(idG));

        // nacionalidad
        int idN = Integer.parseInt(request.getParameter("nacionalidad"));
        c.setNacionalidad(new nacionalidadDAO().obtenerPorId(idN));

        // FOTO
        Part foto = request.getPart("foto");

        if (foto != null && foto.getSize() > 0) {

            String nombreArchivo = Paths.get(foto.getSubmittedFileName())
                    .getFileName().toString();

            String extension = "";

            int i = nombreArchivo.lastIndexOf(".");
            if (i > 0) extension = nombreArchivo.substring(i);

            String nombreFinal = c.getIdentificacion() + extension;

            String rutaImg = getServletContext().getRealPath("/img");
            File carpeta = new File(rutaImg);
            if (!carpeta.exists()) carpeta.mkdirs();

            foto.write(rutaImg + File.separator + nombreFinal);

            c.setFoto("img/" + nombreFinal);
        }

        conductorDAO dao = new conductorDAO();
        dao.actualizarConductor(c);

        request.getSession().setAttribute("mensaje", "Cambios guardados correctamente");

        request.getRequestDispatcher("administrarCuentaConductor.jsp")
                .forward(request, response);
    }


        }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { processRequest(request, response); }

}



