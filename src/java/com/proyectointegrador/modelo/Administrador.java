/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.modelo;

/**
 *
 * @author david
 */
public class Administrador {
    private int idAdmin;
    private int idEntidad;
    private String usuario;
    private String clave;
    private String rol;

    public Administrador() {
    }

    public Administrador(int idAdmin, int idEntidad, String usuario, String clave, String rol) {
        this.idAdmin = idAdmin;
        this.idEntidad = idEntidad;
        this.usuario = usuario;
        this.clave = clave;
        this.rol = rol;
    }

    public int getIdAdmin() { return idAdmin; }
    public void setIdAdmin(int idAdmin) { this.idAdmin = idAdmin; }

    public int getIdEntidad() { return idEntidad; }
    public void setIdEntidad(int idEntidad) { this.idEntidad = idEntidad; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getNombres() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
