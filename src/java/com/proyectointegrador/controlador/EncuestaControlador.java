/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.controlador;
import com.proyectointegrador.dao.EncuestaDAO;
import com.proyectointegrador.modelo.Encuesta;
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

@WebServlet(name = "EncuestaControlador", urlPatterns = {"/api/encuesta"})
public class EncuestaControlador extends HttpServlet {

    private EncuestaDAO dao = new EncuestaDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            // 1. Instanciamos nuestra nueva "caja de transporte"
            Encuesta nuevaEncuesta = new Encuesta();
            
            // 2. Llenamos la caja usando Setters (A prueba de balas)
            nuevaEncuesta.setIdIncidencia(Integer.parseInt(request.getParameter("idIncidencia")));
            nuevaEncuesta.setPuntuacion(Integer.parseInt(request.getParameter("puntuacion")));
            nuevaEncuesta.setComentario(request.getParameter("comentario"));
            
            // 3. Enviamos la caja entera al DAO en lugar de variables sueltas
            boolean exito = dao.registrarEncuesta(nuevaEncuesta);
            
            try (PrintWriter out = response.getWriter()) {
                if (exito) {
                    out.print("{\"status\":\"success\", \"message\":\"¡Gracias por calificar nuestro servicio!\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"status\":\"error\", \"message\":\"Rechazado por MySQL. Revisa tu tabla encuesta_satisfaccion.\"}");
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"status\":\"error\", \"message\":\"Error al procesar los datos de la encuesta.\"}");
            }
        }
    }
}