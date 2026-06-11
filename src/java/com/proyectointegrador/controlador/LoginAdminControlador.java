/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.controlador;
import com.proyectointegrador.dao.AdministradorDAO;
import com.proyectointegrador.modelo.Administrador;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author david
 */

@WebServlet(name = "LoginAdminControlador", urlPatterns = {"/api/loginAdmin"})
public class LoginAdminControlador extends HttpServlet {

    private AdministradorDAO adminDao = new AdministradorDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            String correo = request.getParameter("correo"); // En tu BD esto es el 'usuario'
            String clave = request.getParameter("clave");   // En tu BD esto es la 'clave'
            
            Administrador admin = adminDao.validarLoginAdmin(correo, clave);
            
            try (PrintWriter out = response.getWriter()) {
                if (admin != null) {
                    // ¡CORRECCIÓN MAESTRA AQUÍ! Usamos getUsuario() porque getNombres() no existe en tu BD
                    out.print("{\"status\":\"success\", \"usuario\":{"
                            + "\"idAdmin\":" + admin.getIdAdmin() + ","
                            + "\"usuario\":\"" + admin.getUsuario() + "\","
                            + "\"rol\":\"" + admin.getRol() + "\""
                            + "}}");
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    out.print("{\"status\":\"error\", \"message\":\"Usuario o clave institucional incorrectos.\"}");
                }
            }
        // Usamos Throwable para atrapar hasta los errores de compilación rojos de NetBeans
        } catch (Throwable t) { 
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"status\":\"error\", \"message\":\"Error de código interno. Revisa NetBeans.\"}");
            }
        }
    }
}