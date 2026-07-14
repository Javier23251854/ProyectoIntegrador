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

public class IncidenciaControlador extends HttpServlet {

    private final Gson gson = new Gson();
    private final IncidenciaDAO dao = new IncidenciaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            String idIncidencia = request.getParameter("idIncidencia");
            String idMin = request.getParameter("idMin");
            String idMax = request.getParameter("idMax");
            String estado = request.getParameter("estado");
            String categoria = request.getParameter("categoria");
            String prioridad = request.getParameter("prioridad");
            String ubicacion = request.getParameter("ubicacion");

            List<Incidencia> incidencias = dao.listarIncidencias(
                    idIncidencia, idMin, idMax, estado, categoria, prioridad, ubicacion
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
        response.setCharacterEncoding("UTF-8");

        try {
            String accion = request.getParameter("accion");

            if ("actualizarEstado".equals(accion)) {
                String idParam = request.getParameter("idIncidencia");
                String nuevoEstado = request.getParameter("nuevoEstado");

                if (idParam == null || idParam.isBlank() || nuevoEstado == null || nuevoEstado.isBlank()) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    try (PrintWriter out = response.getWriter()) {
                        out.print("{\"status\":\"error\",\"message\":\"Faltan datos para actualizar el estado.\"}");
                    }
                    return;
                }

                int idIncidencia = Integer.parseInt(idParam.trim());
                boolean exitoActualizacion = dao.actualizarEstado(idIncidencia, nuevoEstado.trim());

                try (PrintWriter out = response.getWriter()) {
                    if (exitoActualizacion) {
                        out.print("{\"status\":\"success\"}");
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("{\"status\":\"error\",\"message\":\"No se pudo actualizar en la base de datos.\"}");
                    }
                }
                return;
            }

            // Registrar nueva incidencia desde el mapa
            Incidencia nueva = new Incidencia();

            String idUsuario = request.getParameter("idUsuario");
            String idEntidad = request.getParameter("idEntidad");
            String idZona = request.getParameter("idZona");
            String categoria = request.getParameter("categoria");
            String prioridad = request.getParameter("prioridad");
            String ubicacion = request.getParameter("ubicacion");
            String descripcion = request.getParameter("descripcion");

            nueva.setIdUsuario(Integer.parseInt(idUsuario));
            nueva.setIdEntidad(Integer.parseInt(idEntidad));
            nueva.setIdZona(Integer.parseInt(idZona));
            nueva.setCategoria(categoria);
            nueva.setPrioridad(prioridad);
            nueva.setUbicacion(ubicacion);
            nueva.setDescripcion(descripcion);
            nueva.setEstado("Pendiente");

            String latParam = request.getParameter("latitud");
            String lonParam = request.getParameter("longitud");

            if (latParam != null && !latParam.isBlank() && lonParam != null && !lonParam.isBlank()) {
                nueva.setLatitud(Double.parseDouble(latParam));
                nueva.setLongitud(Double.parseDouble(lonParam));
            } else {
                nueva.setLatitud(-12.01955000);
                nueva.setLongitud(-76.88045000);
            }

            boolean exitoRegistro = dao.registrarIncidencia(nueva);

            try (PrintWriter out = response.getWriter()) {
                if (exitoRegistro) {
                    out.print("{\"status\":\"success\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"status\":\"error\",\"message\":\"Rechazado por MySQL al intentar registrar.\"}");
                }
            }

        } catch (Exception e) {
            System.out.println("Error en controlador: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"status\":\"error\",\"message\":\"Error interno en el controlador.\"}");
            }
            e.printStackTrace();
        }
    }
}