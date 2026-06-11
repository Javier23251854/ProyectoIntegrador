/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.dao;

import com.proyectointegrador.config.ConexionDB;
import com.proyectointegrador.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author david
 */
public class UsuarioDAO {
    
    // Validar el Login buscando por correo y clave
    public Usuario validarLogin(String correo, String clave) {
        Usuario usu = null;
        String sql = "SELECT * FROM USUARIO WHERE correo = ? AND clave = ?";
        
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, correo);
            ps.setString(2, clave);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usu = new Usuario();
                    usu.setIdUsuario(rs.getInt("id_usuario"));
                    usu.setNombres(rs.getString("nombres"));
                    usu.setApellidos(rs.getString("apellidos"));
                    usu.setDni(rs.getString("dni"));
                    usu.setTelefono(rs.getString("telefono"));
                    usu.setCorreo(rs.getString("correo"));
                    usu.setClave(rs.getString("clave"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }
        
        return usu;
    }
    
    // Método para Registrar un Nuevo Usuario
    public boolean registrarUsuario(Usuario usu) {
        String sql = "INSERT INTO USUARIO (nombres, apellidos, dni, telefono, correo, clave) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, usu.getNombres());
            ps.setString(2, usu.getApellidos());
            ps.setString(3, usu.getDni());
            ps.setString(4, usu.getTelefono());
            ps.setString(5, usu.getCorreo());
            ps.setString(6, usu.getClave());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            // Si el correo o DNI ya existen, MySQL lanzará un error
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }
}