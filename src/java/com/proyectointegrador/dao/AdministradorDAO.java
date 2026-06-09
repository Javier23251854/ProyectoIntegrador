/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.dao;

import com.proyectointegrador.config.ConexionDB;
import com.proyectointegrador.modelo.Administrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author david
 */
public class AdministradorDAO {
    
    public Administrador validarLoginAdmin(String usuario, String clave) {
        Administrador admin = null;
        String sql = "SELECT * FROM ADMINISTRADOR WHERE usuario = ? AND clave = ?";
        
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            ps.setString(2, clave);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    admin = new Administrador();
                    admin.setIdAdmin(rs.getInt("id_admin"));
                    admin.setIdEntidad(rs.getInt("id_entidad"));
                    admin.setUsuario(rs.getString("usuario"));
                    admin.setClave(rs.getString("clave"));
                    admin.setRol(rs.getString("rol"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en login de administrador: " + e.getMessage());
        }
        
        return admin;
    }
}