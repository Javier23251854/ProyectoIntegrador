/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.controlador;
import com.proyectointegrador.dao.ZonaDAO;
import com.proyectointegrador.modelo.Zona;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author david
 */

@WebServlet(name = "ZonaControlador", urlPatterns = {"/api/zonas"})
public class ZonaControlador extends HttpServlet {

    private ZonaDAO dao = new ZonaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        List<Zona> zonas = dao.obtenerZonas();
        
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < zonas.size(); i++) {
            Zona z = zonas.get(i);
            json.append("{\"idZona\":").append(z.getIdZona())
                .append(", \"nombreZona\":\"").append(z.getNombreZona())
                .append("\", \"tipoSector\":\"").append(z.getTipoSector())
                .append("\"}");
            if (i < zonas.size() - 1) json.append(",");
        }
        json.append("]");
        
        try (PrintWriter out = response.getWriter()) {
            out.print(json.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String idZonaStr = request.getParameter("idZona");
            String nombreZona = request.getParameter("nombreZona");
            String tipoSector = request.getParameter("tipoSector");
            
            boolean exito = false;
            
            if (idZonaStr != null && !idZonaStr.trim().isEmpty()) {
                // Si hay ID, estamos EDITANDO
                Zona zona = new Zona(nombreZona, tipoSector);
                zona.setIdZona(Integer.parseInt(idZonaStr));
                exito = dao.actualizarZona(zona);
            } else {
                // Si NO hay ID, estamos AGREGANDO nuevo
                Zona zona = new Zona(nombreZona, tipoSector);
                exito = dao.registrarZona(zona);
            }
            
            try (PrintWriter out = response.getWriter()) {
                if (exito) {
                    out.print("{\"status\":\"success\", \"message\":\"Operación realizada con éxito\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"status\":\"error\", \"message\":\"Error interno en BD\"}");
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}