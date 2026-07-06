package com.proyectointegrador.controlador;

import com.proyectointegrador.config.ConexionDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@WebServlet(name = "ReporteJasperControlador", urlPatterns = {"/api/reportes/incidencias"})
public class ReporteJasperControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipo = request.getParameter("tipo");
        String archivoJasper;
        String nombrePdf;

        if ("estado".equalsIgnoreCase(tipo)) {
            archivoJasper = "/reportes/ReporteIncidenciasPorEstados.jasper";
            nombrePdf = "Reporte_Incidencias_Por_Estado.pdf";
        } else if ("zona".equalsIgnoreCase(tipo)) {
            archivoJasper = "/reportes/ReporteIncidenciasPorZonas.jasper";
            nombrePdf = "Reporte_Incidencias_Por_Zona.pdf";
        } else {
            archivoJasper = "/reportes/ReporteIncidenciasDetalles.jasper";
            nombrePdf = "Reporte_Incidencias_Detallado.pdf";
        }

        Connection con = null;

        try (InputStream jasperStream = getClass().getResourceAsStream(archivoJasper)) {

            if (jasperStream == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "No se encontró el archivo del reporte: " + archivoJasper);
                return;
            }

            con = ConexionDB.conectar();
            if (con == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "No se pudo conectar a la base de datos.");
                return;
            }

            Map<String, Object> parametros = new HashMap<>();

            JasperPrint print = JasperFillManager.fillReport(jasperStream, parametros, con);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                    "inline; filename=\"" + nombrePdf + "\"");

            JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());

        } catch (JRException e) {
            throw new ServletException("Error al generar el reporte PDF", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception ignored) {
                }
            }
        }
    }
}
