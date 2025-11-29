/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controlador;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 *
 * @author VivoBook
 */
@WebServlet(name = "Controlador", urlPatterns = {"/Controlador"})
@MultipartConfig
public class Controlador extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");
        //Inicio de sesión
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
                    System.out.println("Conductor encontrado, ID: " + conductor.getIdentificacion());
                    request.getSession().setAttribute("conductor", conductor);
                    request.getRequestDispatcher("conductorPrincipal.jsp").forward(request, response);
                } else {
                    clienteDAO clienteDAO = new clienteDAO();
                    Cliente cliente = new Cliente();
                    int rCliente = clienteDAO.validarLogin(correo, clave, cliente);

                    if (rCliente > 0) {
                        System.out.println("Cliente encontrado, ID: " + cliente.getIdentificacion());
                        request.getSession().setAttribute("cliente", cliente);
                        request.getRequestDispatcher("clientePrincipal.jsp").forward(request, response);
                    } else {
                        System.out.println("No se encontró ninguna cuenta con esas credenciales.");
                        request.setAttribute("error", "Credenciales incorrectas");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                }
            }
        } 
        //Registro de clientes y conductores
        else if(accion.equals("Registrar")){
            String usuario = request.getParameter("rol");
            if(usuario.equals("cliente")){
                Cliente c = new Cliente();
                c.setIdentificacion(Integer.parseInt(request.getParameter("identificacion")));
                c.setNombre(request.getParameter("nombre_completo"));
                c.setDireccion(request.getParameter("direccion"));
                
                TelefonoCliente t = new TelefonoCliente();
                t.setIdentificacion_cliente(Integer.parseInt(request.getParameter("identificacion")));
                t.setNumero(request.getParameter("tel"));
                telefonoClienteDAO tele = new telefonoClienteDAO();
                
                
                c.setCorreo(request.getParameter("correo"));
                c.setClave(request.getParameter("clave"));
                
                int idG = Integer.parseInt(request.getParameter("genero"));
                generoDAO g = new generoDAO();
                Genero genero = g.obtenerPorId(idG);
                c.setGenero(genero);
                
                int idN = Integer.parseInt(request.getParameter("nacionalidad"));
                nacionalidadDAO n = new nacionalidadDAO();
                Nacionalidad nacionalidad = n.obtenerPorId(idN);
                c.setNacionalidad(nacionalidad);

                clienteDAO cdao = new clienteDAO();

                if (cdao.registrarCliente(c)) {
                    tele.registrarTelefonoCliente(t);
                    request.setAttribute("msg", true);
                    request.getRequestDispatcher("Registrar.jsp").forward(request, response);
                } else {
                    request.setAttribute("msg", false);
                    request.getRequestDispatcher("Registrar.jsp").forward(request, response);
                }
            }else{
                Conductor c = new Conductor();
                c.setIdentificacion(Integer.parseInt(request.getParameter("identificacion")));
                c.setNombre(request.getParameter("nombre_completo"));
                c.setDireccion(request.getParameter("direccion"));
                c.setCorreo(request.getParameter("correo"));
                c.setClave(request.getParameter("clave"));
                
                TelefonoConductor t = new TelefonoConductor();
                t.setIdentificacion_conductor(Integer.parseInt(request.getParameter("identificacion")));
                t.setNumero(request.getParameter("tel"));
                telefonoConductorDAO tele = new telefonoConductorDAO();

                int idG = Integer.parseInt(request.getParameter("genero"));
                generoDAO gdao = new generoDAO();
                Genero genero = gdao.obtenerPorId(idG);
                c.setGenero(genero);

                int idN = Integer.parseInt(request.getParameter("nacionalidad"));
                nacionalidadDAO ndao = new nacionalidadDAO();
                Nacionalidad nacionalidad = ndao.obtenerPorId(idN);
                c.setNacionalidad(nacionalidad);

                Part foto = request.getPart("foto_conductor");

                if (foto != null && foto.getSize() > 0) {
                    
                    String nombreArchivo = Paths.get(foto.getSubmittedFileName()).getFileName().toString();
                    
                    String extension = "";
                    int i = nombreArchivo.lastIndexOf('.');
                    if (i > 0) {
                        extension = nombreArchivo.substring(i);
                    }

                    String idUsuario = request.getParameter("identificacion"); 
                    String nombreFinal = idUsuario + extension;
                    
                    String rutaImg = getServletContext().getRealPath("/img");
                    File carpeta = new File(rutaImg);

                    if (!carpeta.exists()) {
                        carpeta.mkdirs();
                    }

                    String rutaCompleta = rutaImg + File.separator + nombreFinal;
                    //System.out.println("RUTA REAL DE GUARDADO: " + rutaCompleta);
                    foto.write(rutaCompleta);
                    
                    String rutaBD = "img/" + nombreFinal; 
                    c.setFoto(rutaBD);
                    
                } else {
                    //c.setFoto("img/default.png");
                }

                conductorDAO cdao = new conductorDAO();

                if (cdao.registrarConductor(c)) {
                    tele.registrarTelefonoConductor(t);
                    request.setAttribute("msg", true);
                    request.getRequestDispatcher("Registrar.jsp").forward(request, response);
                } else {
                    request.setAttribute("msg", false);
                    request.getRequestDispatcher("Registrar.jsp").forward(request, response);
                }

            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}