/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author TOSHIBA
 */
public class CaracteristicasObjPagina {
    private String IdMemoria;
    private double peso;

    public CaracteristicasObjPagina() {
    }
    public CaracteristicasObjPagina(String IdMemoria){
        this.IdMemoria = IdMemoria;
    }

    public CaracteristicasObjPagina(String IdMemoria, double peso) {
        this.IdMemoria = IdMemoria;
        this.peso = peso;
    }

    /**
     * @return the IdMemoria
     */
    public String getIdMemoria() {
        return IdMemoria;
    }

    /**
     * @param IdMemoria the IdMemoria to set
     */
    public void setIdMemoria(String IdMemoria) {
        this.IdMemoria = IdMemoria;
    }

    /**
     * @return the peso
     */
    public double getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    
    
}
