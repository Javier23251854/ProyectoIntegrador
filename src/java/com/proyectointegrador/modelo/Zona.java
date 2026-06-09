/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectointegrador.modelo;

/**
 *
 * @author david
 */
public class Zona {
    private int idZona;
    private String nombreZona;
    private String tipoSector;

    public Zona() {}

    public Zona(String nombreZona, String tipoSector) {
        this.nombreZona = nombreZona;
        this.tipoSector = tipoSector;
    }

    public int getIdZona() { return idZona; }
    public void setIdZona(int idZona) { this.idZona = idZona; }
    
    public String getNombreZona() { return nombreZona; }
    public void setNombreZona(String nombreZona) { this.nombreZona = nombreZona; }
    
    public String getTipoSector() { return tipoSector; }
    public void setTipoSector(String tipoSector) { this.tipoSector = tipoSector; }
}