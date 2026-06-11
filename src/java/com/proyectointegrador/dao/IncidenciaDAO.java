/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.dao;

import com.proyectointegrador.config.ConexionDB;
import com.proyectointegrador.modelo.Incidencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author david
 */
public class IncidenciaDAO {

    public boolean registrarIncidencia(Incidencia inc) {
        // SQL actualizado con 10 columnas incluyendo latitud y longitud
        String sql = "INSERT INTO INCIDENCIA (id_usuario, id_entidad, id_zona, descripcion, categoria, prioridad, ubicacion, estado, latitud, longitud) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

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

    public List<Incidencia> listarIncidencias(String estado, String categoria) {
        List<Incidencia> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM INCIDENCIA WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (estado != null && !estado.trim().isEmpty()) {
            sql.append(" AND estado = ?");
            params.add(estado.trim());
        }

        if (categoria != null && !categoria.trim().isEmpty()) {
            sql.append(" AND categoria LIKE ?");
            params.add("%" + categoria.trim() + "%");
        }

        sql.append(" ORDER BY fecha_registro DESC");

        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql.toString())) {

            if (con == null) {
                return lista;
            }

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
                    inc.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                    inc.setEstado(rs.getString("estado"));
                    inc.setLatitud(rs.getDouble("latitud"));
                    inc.setLongitud(rs.getDouble("longitud"));
                    lista.add(inc);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar incidencias filtradas: " + e.getMessage());
        }

        return lista;
    }

    public List<Incidencia> listarIncidencias() {
        List<Incidencia> lista = new ArrayList<>();
        String sql = "SELECT * FROM INCIDENCIA ORDER BY fecha_registro DESC";

        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (con == null) {
                return lista;
            }

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
        } catch (SQLException e) {
            System.out.println("Error al listar incidencias: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizarEstado(int idIncidencia, String nuevoEstado) {
        String sql = "UPDATE incidencia SET estado = ? WHERE id_incidencia = ?";

        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idIncidencia);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error SQL al actualizar estado de incidencia: " + e.getMessage());
            return false;
        }
    }
}
