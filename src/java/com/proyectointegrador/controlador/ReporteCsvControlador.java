/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.controlador;

import com.proyectointegrador.dao.IncidenciaDAO;
import com.proyectointegrador.modelo.Incidencia;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ReporteCsvControlador", urlPatterns = {"/api/incidencias/csv"})
public class ReporteCsvControlador extends HttpServlet {

    private final IncidenciaDAO dao = new IncidenciaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Incidencia> lista = dao.listarIncidencias();

        response.setContentType("text/csv;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=incidencias.csv");

        try (PrintWriter out = response.getWriter()) {
            out.println("ID,Categoria,Ubicacion,Prioridad,Estado,FechaRegistro");

            for (Incidencia inc : lista) {
                out.println(
                    inc.getIdIncidencia() + "," +
                    limpiar(inc.getCategoria()) + "," +
                    limpiar(inc.getUbicacion()) + "," +
                    limpiar(inc.getPrioridad()) + "," +
                    limpiar(inc.getEstado()) + "," +
                    limpiar(String.valueOf(inc.getFechaRegistro()))
                );
            }
        }
    }

    private String limpiar(String texto) {
        if (texto == null) return "";
        return "\"" + texto.replace("\"", "\"\"") + "\"";
    }
}