/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.proyectointegrador.controlador;
import com.google.gson.Gson;
import com.proyectointegrador.dao.IncidenciaDAO;
import com.proyectointegrador.modelo.Incidencia;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author david
 */

public class IncidenciaControlador extends HttpServlet {

    private Gson gson = new Gson();
    private IncidenciaDAO dao = new IncidenciaDAO();

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    response.setContentType("application/json;charset=UTF-8");

    try {
        String idIncidencia = request.getParameter("idIncidencia");
        String estado = request.getParameter("estado");
        String categoria = request.getParameter("categoria");
        String prioridad = request.getParameter("prioridad");
        String ubicacion = request.getParameter("ubicacion");

        List<Incidencia> incidencias = dao.listarIncidencias(
                idIncidencia, estado, categoria, prioridad, ubicacion
        );

        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(incidencias));
        }

    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        try (PrintWriter out = response.getWriter()) {
            out.print("{\"status\":\"error\",\"message\":\"Error al cargar incidencias\"}");
        }
        e.printStackTrace();
    }
}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        try {
            String accion = request.getParameter("accion");

            if ("actualizarEstado".equals(accion)) {
                int idIncidencia = Integer.parseInt(request.getParameter("idIncidencia"));
                String nuevoEstado = request.getParameter("nuevoEstado");

                boolean exitoActualizacion = dao.actualizarEstado(idIncidencia, nuevoEstado);

                try (PrintWriter out = response.getWriter()) {
                    if (exitoActualizacion) {
                        out.print("{\"status\":\"success\"}");
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("{\"status\":\"error\", \"message\":\"No se pudo actualizar en la base de datos.\"}");
                    }
                }
                return; 
            }

            // REGISTRAR NUEVA INCIDENCIA DESDE EL MAPA
            Incidencia nueva = new Incidencia();
            nueva.setIdUsuario(Integer.parseInt(request.getParameter("idUsuario")));
            nueva.setIdEntidad(Integer.parseInt(request.getParameter("idEntidad")));
            nueva.setIdZona(Integer.parseInt(request.getParameter("idZona")));
            nueva.setCategoria(request.getParameter("categoria"));
            nueva.setPrioridad(request.getParameter("prioridad"));
            nueva.setUbicacion(request.getParameter("ubicacion"));
            nueva.setDescripcion(request.getParameter("descripcion"));
            nueva.setEstado("Pendiente"); 

            // CAPTURAMOS LAS COORDENADAS ENVIADAS POR EL CLIENTE HTML
            String latParam = request.getParameter("latitud");
            String lonParam = request.getParameter("longitud");
            
            if (latParam != null && !latParam.isEmpty() && lonParam != null && !lonParam.isEmpty()) {
                nueva.setLatitud(Double.parseDouble(latParam));
                nueva.setLongitud(Double.parseDouble(lonParam));
            } else {
                // Coordenadas por defecto (Centro de la Plaza Cívica de Santa Clara) si no se hace clic
                nueva.setLatitud(-12.01955000);
                nueva.setLongitud(-76.88045000);
            }

            boolean exitoRegistro = dao.registrarIncidencia(nueva);

            try (PrintWriter out = response.getWriter()) {
                if (exitoRegistro) {
                    out.print("{\"status\":\"success\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"status\":\"error\", \"message\":\"Rechazado por MySQL al intentar registrar.\"}");
                }
            }

        } catch (Exception e) {
            System.out.println("Error en controlador: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"status\":\"error\", \"message\":\"Error interno en el controlador.\"}");
            }
        }
    }
}