/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.LinkedList;

/**
 *
 * @author TOSHIBA
 */
public class Ram {
    private LinkedList<String> dirMemoria= new LinkedList<>();
    private LinkedList<CaracteristicasStackPointer>  caracObjsFinalPila = new LinkedList<>();

    public Ram() {
    }
    // llenado de memoria ram
     public boolean pushRam(CaracteristicasObjPagina objPagina,int nombrePaginaOrigen){
         //se agreg a la pila donde solo estaran los nombres pertenecientes a la direccion de memoria 
        getDirMemoria().addFirst(objPagina.getIdMemoria());
        //se agrega a la pila q tendra la informacion de donde viene ese stackpointer y todo lo q necesite
        getCaracObjsFinalPila().addFirst(new CaracteristicasStackPointer(nombrePaginaOrigen));

        return true;
    }

    /**
     * @return the dirMemoria
     */
    public LinkedList<String> getDirMemoria() {
        return dirMemoria;
    }

    /**
     * @param dirMemoria the dirMemoria to set
     */
    public void setDirMemoria(LinkedList<String> dirMemoria) {
        this.dirMemoria = dirMemoria;
    }

    /**
     * @return the caracObjsFinalPila
     */
    public LinkedList<CaracteristicasStackPointer> getCaracObjsFinalPila() {
        return caracObjsFinalPila;
    }

    /**
     * @param caracObjsFinalPila the caracObjsFinalPila to set
     */
    public void setCaracObjsFinalPila(LinkedList<CaracteristicasStackPointer> caracObjsFinalPila) {
        this.caracObjsFinalPila = caracObjsFinalPila;
    }
   

    

    
}
