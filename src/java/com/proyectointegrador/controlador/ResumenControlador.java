package com.proyectointegrador.controlador;

import com.google.gson.Gson;
import com.proyectointegrador.dao.EncuestaDAO;
import com.proyectointegrador.dao.IncidenciaDAO;
import com.proyectointegrador.dao.ZonaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ResumenControlador", urlPatterns = {"/api/resumen"})
public class ResumenControlador extends HttpServlet {

    private final Gson gson = new Gson();
    private final IncidenciaDAO incidenciaDAO = new IncidenciaDAO();
    private final ZonaDAO zonaDAO = new ZonaDAO();
    private final EncuestaDAO encuestaDAO = new EncuestaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> data = new HashMap<>();
        data.put("totalIncidencias", incidenciaDAO.contarIncidencias());
        data.put("pendientes", incidenciaDAO.contarPorEstado("Pendiente"));
        data.put("enProceso", incidenciaDAO.contarPorEstado("En proceso"));
        data.put("atendidos", incidenciaDAO.contarPorEstado("Atendido"));
        data.put("zonasActivas", zonaDAO.contarZonas());
        data.put("ultimasIncidencias", incidenciaDAO.ultimasIncidencias(3));

        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(data));
        }
    }
}