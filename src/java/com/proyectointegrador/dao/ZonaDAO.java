/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.dao;

import com.proyectointegrador.config.ConexionDB;
import com.proyectointegrador.modelo.Zona;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author david
 */
public class ZonaDAO {

    // 1. Método para AGREGAR (Insertar)
    public boolean registrarZona(Zona zona) {
        String sql = "INSERT INTO ZONA_SANTA_CLARA (nombre_zona, tipo_sector) VALUES (?, ?)";
        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, zona.getNombreZona());
            ps.setString(2, zona.getTipoSector());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar zona: " + e.getMessage());
            return false;
        }
    }

    // 2. Método para VER (Listar)
    public List<Zona> obtenerZonas() {
        List<Zona> lista = new ArrayList<>();
        String sql = "SELECT * FROM ZONA_SANTA_CLARA";
        try (Connection con = ConexionDB.conectar(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Zona z = new Zona();
                z.setIdZona(rs.getInt("id_zona"));
                z.setNombreZona(rs.getString("nombre_zona"));
                z.setTipoSector(rs.getString("tipo_sector"));
                lista.add(z);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar zonas: " + e.getMessage());
        }
        return lista;
    }

    // 3. Método para EDITAR (Actualizar)
    public boolean actualizarZona(Zona zona) {
        String sql = "UPDATE ZONA_SANTA_CLARA SET nombre_zona = ?, tipo_sector = ? WHERE id_zona = ?";
        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, zona.getNombreZona());
            ps.setString(2, zona.getTipoSector());
            ps.setInt(3, zona.getIdZona());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar zona: " + e.getMessage());
            return false;
        }
    }

    public boolean existeZona(String nombreZona, Integer excluirId) {
        String sql = "SELECT id_zona FROM ZONA_SANTA_CLARA WHERE nombre_zona = ?";
        if (excluirId != null) {
            sql += " AND id_zona <> ?";
        }

        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                return false;
            }

            ps.setString(1, nombreZona);
            if (excluirId != null) {
                ps.setInt(2, excluirId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error al validar zona duplicada: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarZona(int idZona) {
        String sql = "DELETE FROM ZONA_SANTA_CLARA WHERE id_zona = ?";
        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            if (con == null) {
                return false;
            }

            ps.setInt(1, idZona);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar zona: " + e.getMessage());
            return false;
        }
    }

    public int contarZonas() {
        String sql = "SELECT COUNT(*) FROM ZONA_SANTA_CLARA";
        try (Connection con = ConexionDB.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (con == null) {
                return 0;
            }
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            return 0;
        }
    }

}
