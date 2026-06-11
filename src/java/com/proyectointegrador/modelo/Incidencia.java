/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.modelo;

import java.sql.Timestamp;
/**
 *
 * @author david
 */

public class Incidencia {
    
    private int idIncidencia;
    private int idUsuario;
    private int idEntidad;
    private int idZona; 
    private String descripcion;
    private String categoria;
    private String prioridad;
    private String ubicacion;
    private Timestamp fechaRegistro;
    private String estado;
    // NUEVOS CAMPOS GEOGRÁFICOS
    private double latitud;
    private double longitud;

    public Incidencia() {
    }

    public Incidencia(int idUsuario, int idEntidad, int idZona, String descripcion, String categoria, String prioridad, String ubicacion, String estado, double latitud, double longitud) {
        this.idUsuario = idUsuario;
        this.idEntidad = idEntidad;
        this.idZona = idZona; 
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.prioridad = prioridad;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // --- GETTERS Y SETTERS ---
    public int getIdIncidencia() { return idIncidencia; }
    public void setIdIncidencia(int idIncidencia) { this.idIncidencia = idIncidencia; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdEntidad() { return idEntidad; }
    public void setIdEntidad(int idEntidad) { this.idEntidad = idEntidad; }

    public int getIdZona() { return idZona; }
    public void setIdZona(int idZona) { this.idZona = idZona; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Timestamp fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }
}