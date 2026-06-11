/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author david
 */
public class ConexionDB {
    
    // EL TRUCO: Usar 127.0.0.1 en lugar de localhost para evitar bloqueos del puerto 3307
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/bd_incidencias_santaclara?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASSWORD = "root"; // Déjalo vacío si estás en XAMPP

    // Prueba #!
    
    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Falta el Driver de MySQL: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Error de credenciales o puerto en BD: " + e.getMessage());
            return null;
        }
    }
}