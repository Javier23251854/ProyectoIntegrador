/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.controlador;

import com.proyectointegrador.dao.UsuarioDAO;
import com.proyectointegrador.modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author david
 */
public class RegistroControlador extends HttpServlet {

    private UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        // 1. Recibimos los datos del formulario web
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombres(request.getParameter("nombres"));
        nuevoUsuario.setApellidos(request.getParameter("apellidos"));
        nuevoUsuario.setDni(request.getParameter("dni"));
        nuevoUsuario.setTelefono(request.getParameter("telefono"));
        nuevoUsuario.setCorreo(request.getParameter("correo"));
        nuevoUsuario.setClave(request.getParameter("clave")); // En un proyecto real esto se encriptaría
        
        // 2. Mandamos a guardar a la base de datos
        boolean exito = dao.registrarUsuario(nuevoUsuario);
        
        // 3. Respondemos a la web
        try (PrintWriter out = response.getWriter()) {
            if (exito) {
                out.print("{\"status\":\"success\", \"message\":\"Cuenta creada exitosamente. ¡Ya puedes iniciar sesión!\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"status\":\"error\", \"message\":\"El DNI o Correo ya se encuentran registrados.\"}");
            }
            out.flush();
        }
    }
}