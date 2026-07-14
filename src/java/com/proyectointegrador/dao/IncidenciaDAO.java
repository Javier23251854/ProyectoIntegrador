package com.proyectointegrador.dao;

import com.proyectointegrador.config.ConexionDB;
import com.proyectointegrador.modelo.Incidencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncidenciaDAO {

    public boolean registrarIncidencia(Incidencia inc) {
        String sql = "INSERT INTO INCIDENCIA "
                + "(id_usuario, id_entidad, id_zona, descripcion, categoria, prioridad, ubicacion, estado, latitud, longitud) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                return false;
            }

            ps.setInt(1, inc.getIdUsuario());
            ps.setInt(2, inc.getIdEntidad());
            ps.setInt(3, inc.getIdZona());
            ps.setString(4, inc.getDescripcion());
            ps.setString(5, inc.getCategoria());
            ps.setString(6, inc.getPrioridad());
            ps.setString(7, inc.getUbicacion());
            ps.setString(8, inc.getEstado());
            ps.setDouble(9, inc.getLatitud());
            ps.setDouble(10, inc.getLongitud());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al registrar incidencia: " + e.getMessage());
            return false;
        }
    }

    // Método viejo conservado por compatibilidad
    public List<Incidencia> listarIncidencias(String idIncidencia, String estado, String categoria,
            String prioridad, String ubicacion) {
        return listarIncidencias(idIncidencia, null, null, estado, categoria, prioridad, ubicacion);
    }

    // Método nuevo con rango
    public List<Incidencia> listarIncidencias(String idIncidencia, String idMin, String idMax,
            String estado, String categoria, String prioridad,
            String ubicacion) {

        List<Incidencia> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT id_incidencia, id_usuario, id_entidad, id_zona, descripcion, categoria, prioridad, ubicacion, estado, latitud, longitud, fecha_registro "
                + "FROM INCIDENCIA WHERE 1=1"
        );

        List<Object> params = new ArrayList<>();

        String idInc = limpiarTexto(idIncidencia);
        String min = limpiarTexto(idMin);
        String max = limpiarTexto(idMax);
        String est = limpiarTexto(estado);
        String cat = limpiarTexto(categoria);
        String pri = limpiarTexto(prioridad);
        String ubi = limpiarTexto(ubicacion);

        if (!idInc.isEmpty()) {
            sql.append(" AND id_incidencia = ?");
            params.add(Integer.parseInt(idInc));
        } else if (!min.isEmpty() && !max.isEmpty()) {
            sql.append(" AND id_incidencia BETWEEN ? AND ?");
            int inicio = Integer.parseInt(min);
            int fin = Integer.parseInt(max);

            if (inicio <= fin) {
                params.add(inicio);
                params.add(fin);
            } else {
                params.add(fin);
                params.add(inicio);
            }
        }

        if (!est.isEmpty()) {
            sql.append(" AND estado = ?");
            params.add(est);
        }

        if (!cat.isEmpty()) {
            sql.append(" AND categoria LIKE ?");
            params.add("%" + cat + "%");
        }

        if (!pri.isEmpty()) {
            sql.append(" AND prioridad = ?");
            params.add(pri);
        }

        if (!ubi.isEmpty()) {
            sql.append(" AND ubicacion = ?");
            params.add(ubi);
        }

        sql.append(" ORDER BY id_incidencia DESC");

        try (Connection con = ConexionDB.conectar()) {
            if (con == null) {
                System.out.println("No se pudo conectar a la base de datos.");
                return lista;
            }

            try (PreparedStatement ps = con.prepareStatement(sql.toString())) {

                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Incidencia inc = new Incidencia();
                        inc.setIdIncidencia(rs.getInt("id_incidencia"));
                        inc.setIdUsuario(rs.getInt("id_usuario"));
                        inc.setIdEntidad(rs.getInt("id_entidad"));
                        inc.setIdZona(rs.getInt("id_zona"));
                        inc.setDescripcion(rs.getString("descripcion"));
                        inc.setCategoria(rs.getString("categoria"));
                        inc.setPrioridad(rs.getString("prioridad"));
                        inc.setUbicacion(rs.getString("ubicacion"));
                        inc.setEstado(rs.getString("estado"));
                        inc.setLatitud(rs.getDouble("latitud"));
                        inc.setLongitud(rs.getDouble("longitud"));
                        inc.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                        lista.add(inc);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error al filtrar incidencias: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    public List<Incidencia> listarIncidencias() {
        List<Incidencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM INCIDENCIA ORDER BY id_incidencia DESC";

        try (Connection con = ConexionDB.conectar()) {
            if (con == null) {
                return lista;
            }

            try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Incidencia inc = new Incidencia();
                    inc.setIdIncidencia(rs.getInt("id_incidencia"));
                    inc.setIdUsuario(rs.getInt("id_usuario"));
                    inc.setIdEntidad(rs.getInt("id_entidad"));
                    inc.setIdZona(rs.getInt("id_zona"));
                    inc.setDescripcion(rs.getString("descripcion"));
                    inc.setCategoria(rs.getString("categoria"));
                    inc.setPrioridad(rs.getString("prioridad"));
                    inc.setUbicacion(rs.getString("ubicacion"));
                    inc.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                    inc.setEstado(rs.getString("estado"));
                    inc.setLatitud(rs.getDouble("latitud"));
                    inc.setLongitud(rs.getDouble("longitud"));
                    lista.add(inc);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar incidencias: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizarEstado(int idIncidencia, String nuevoEstado) {
        String sql = "UPDATE INCIDENCIA SET estado = ? WHERE id_incidencia = ?";

        try (Connection con = ConexionDB.conectar()) {
            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nuevoEstado);
                ps.setInt(2, idIncidencia);
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar estado de incidencia: " + e.getMessage());
            return false;
        }
    }

    public int contarIncidencias() {
        return contar("SELECT COUNT(*) FROM INCIDENCIA");
    }

    public int contarPorEstado(String estado) {
        String sql = "SELECT COUNT(*) FROM INCIDENCIA WHERE estado = ?";

        try (Connection con = ConexionDB.conectar()) {
            if (con == null) {
                return 0;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, estado);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() ? rs.getInt(1) : 0;
                }
            }

        } catch (SQLException e) {
            return 0;
        }
    }

    public List<Incidencia> ultimasIncidencias(int limite) {
        List<Incidencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM INCIDENCIA ORDER BY id_incidencia DESC LIMIT ?";

        try (Connection con = ConexionDB.conectar()) {
            if (con == null) {
                return lista;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, limite);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Incidencia inc = new Incidencia();
                        inc.setIdIncidencia(rs.getInt("id_incidencia"));
                        inc.setCategoria(rs.getString("categoria"));
                        inc.setUbicacion(rs.getString("ubicacion"));
                        inc.setEstado(rs.getString("estado"));
                        inc.setLatitud(rs.getDouble("latitud"));
                        inc.setLongitud(rs.getDouble("longitud"));
                        lista.add(inc);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener últimas incidencias: " + e.getMessage());
        }

        return lista;
    }

    private int contar(String sql) {
        try (Connection con = ConexionDB.conectar()) {
            if (con == null) {
                return 0;
            }

            try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (SQLException e) {
            return 0;
        }
    }

    private String limpiarTexto(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
