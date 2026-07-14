package com.proyectointegrador.controlador;

import com.proyectointegrador.config.ConexionDB;
import com.proyectointegrador.dao.IncidenciaDAO;
import com.proyectointegrador.modelo.Incidencia;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@WebServlet(name = "ReporteJasperControlador", urlPatterns = {"/api/reportes/incidencias"})
public class ReporteJasperControlador extends HttpServlet {

    private final IncidenciaDAO dao = new IncidenciaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipo = request.getParameter("tipo");

        if ("consulta".equalsIgnoreCase(tipo)) {
            generarPdfConsulta(request, response);
            return;
        }

        String archivoJasper;
        String nombrePdf;

        if ("estado".equalsIgnoreCase(tipo)) {
            archivoJasper = "ReporteIncidenciasPorEstados.jasper";
            nombrePdf = "Reporte_Incidencias_Por_Estado.pdf";
        } else if ("zona".equalsIgnoreCase(tipo)) {
            archivoJasper = "ReporteIncidenciasPorZonas.jasper";
            nombrePdf = "Reporte_Incidencias_Por_Zona.pdf";
        } else {
            archivoJasper = "ReporteIncidenciasDetalles.jasper";
            nombrePdf = "Reporte_Incidencias_Detallado.pdf";
        }

        Connection con = null;

        try (InputStream jasperStream = cargarReporte(archivoJasper)) {

            if (jasperStream == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "No se encontró el archivo del reporte: reportes/" + archivoJasper);
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
            response.setHeader("Content-Disposition", "inline; filename=\"" + nombrePdf + "\"");
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

    private InputStream cargarReporte(String nombreArchivo) {
        return getClass().getClassLoader().getResourceAsStream("reportes/" + nombreArchivo);
    }

    private void generarPdfConsulta(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String idIncidencia = request.getParameter("idIncidencia");
        String idMin = request.getParameter("idMin");
        String idMax = request.getParameter("idMax");
        String estado = request.getParameter("estado");
        String categoria = request.getParameter("categoria");
        String prioridad = request.getParameter("prioridad");
        String ubicacion = request.getParameter("ubicacion");

        List<Incidencia> lista = dao.listarIncidencias(
                idIncidencia, idMin, idMax, estado, categoria, prioridad, ubicacion
        );

        String archivoJasper = "/reportes/ReporteIncidenciasConsulta.jasper";

        try (InputStream jasperStream = getClass().getClassLoader().getResourceAsStream("reportes/ReporteIncidenciasConsulta.jasper")) {

            if (jasperStream == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "No se encontró el archivo del reporte: " + archivoJasper);
                return;
            }

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("TITULO", "Consulta filtrada de incidencias");
            parametros.put("SUBTITULO", construirSubtitulo(
                    idIncidencia, idMin, idMax, estado, categoria, prioridad, ubicacion
            ));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);
            JasperPrint print = JasperFillManager.fillReport(jasperStream, parametros, dataSource);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"Consulta_Incidencias.pdf\"");

            JasperExportManager.exportReportToPdfStream(print, response.getOutputStream());

        } catch (JRException e) {
            throw new IOException("Error al generar el PDF de la consulta", e);
        }
    }

    private String construirSubtitulo(String idIncidencia, String idMin, String idMax,
            String estado, String categoria, String prioridad,
            String ubicacion) {

        StringBuilder sb = new StringBuilder();

        if (idIncidencia != null && !idIncidencia.isBlank()) {
            sb.append("ID exacto: ").append(idIncidencia).append(" | ");
        } else if (idMin != null && !idMin.isBlank() && idMax != null && !idMax.isBlank()) {
            sb.append("Rango ID: ").append(idMin).append("-").append(idMax).append(" | ");
        }

        if (estado != null && !estado.isBlank()) {
            sb.append("Estado: ").append(estado).append(" | ");
        }
        if (categoria != null && !categoria.isBlank()) {
            sb.append("Categoría: ").append(categoria).append(" | ");
        }
        if (prioridad != null && !prioridad.isBlank()) {
            sb.append("Prioridad: ").append(prioridad).append(" | ");
        }
        if (ubicacion != null && !ubicacion.isBlank()) {
            sb.append("Ubicación: ").append(ubicacion).append(" | ");
        }

        String texto = sb.toString().trim();
        if (texto.endsWith("|")) {
            texto = texto.substring(0, texto.length() - 1).trim();
        }

        return texto.isEmpty() ? "Sin filtros aplicados" : texto;
    }
}
