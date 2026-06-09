/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.controlador;

import com.google.gson.Gson;
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
public class LoginControlador extends HttpServlet {

    private Gson gson = new Gson();
    private UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        String correo = request.getParameter("correo");
        String clave = request.getParameter("clave");
        
        Usuario usuario = dao.validarLogin(correo, clave);
        
        try (PrintWriter out = response.getWriter()) {
            if (usuario != null) {
                // Login exitoso: Devolvemos los datos del usuario (sin la clave por seguridad)
                usuario.setClave(null); 
                String jsonUsuario = gson.toJson(usuario);
                out.print("{\"status\":\"success\", \"message\":\"Bienvenido\", \"usuario\":" + jsonUsuario + "}");
            } else {
                // Login fallido
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Error 401
                out.print("{\"status\":\"error\", \"message\":\"Correo o contraseña incorrectos\"}");
            }
            out.flush();
        }
    }
}
