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
            if (i < zonas.size() - 1) {
                json.append(",");
            }
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
        String accion = request.getParameter("accion");

        try {
            if ("eliminar".equals(accion)) {
                int idZona = Integer.parseInt(request.getParameter("idZona"));
                boolean exito = dao.eliminarZona(idZona);

                try (PrintWriter out = response.getWriter()) {
                    if (exito) {
                        out.print("{\"status\":\"success\", \"message\":\"Zona eliminada correctamente\"}");
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        out.print("{\"status\":\"error\", \"message\":\"No se pudo eliminar la zona\"}");
                    }
                }
                return;
            }

            String idZonaStr = request.getParameter("idZona");
            String nombreZona = request.getParameter("nombreZona");
            String tipoSector = request.getParameter("tipoSector");

            if (nombreZona == null || nombreZona.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                try (PrintWriter out = response.getWriter()) {
                    out.print("{\"status\":\"error\", \"message\":\"El nombre de la zona es obligatorio\"}");
                }
                return;
            }

            boolean exito;
            Zona zona = new Zona(nombreZona.trim(), tipoSector);

            if (idZonaStr != null && !idZonaStr.trim().isEmpty()) {
                zona.setIdZona(Integer.parseInt(idZonaStr));
                if (dao.existeZona(zona.getNombreZona(), zona.getIdZona())) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    try (PrintWriter out = response.getWriter()) {
                        out.print("{\"status\":\"error\", \"message\":\"Ya existe una zona con ese nombre\"}");
                    }
                    return;
                }
                exito = dao.actualizarZona(zona);
            } else {
                if (dao.existeZona(zona.getNombreZona(), null)) {
                    response.setStatus(HttpServletResponse.SC_CONFLICT);
                    try (PrintWriter out = response.getWriter()) {
                        out.print("{\"status\":\"error\", \"message\":\"Ya existe una zona con ese nombre\"}");
                    }
                    return;
                }
                exito = dao.registrarZona(zona);
            }

            try (PrintWriter out = response.getWriter()) {
                if (exito) {
                    out.print("{\"status\":\"success\", \"message\":\"Operación realizada con éxito\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"status\":\"error\", \"message\":\"Error al guardar la zona\"}");
                }
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
