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

    private LinkedList<String> dirMemoria = new LinkedList<>();
    private LinkedList<CaracteristicasIdMemoria> caracObjsFinalPila = new LinkedList<>();

    public Ram() {
    }

    // llenado de memoria ram
    public boolean pushRam(CaracteristicasObjPagina objPagina, int nombrePaginaOrigen, boolean stackPointer) {
        //se agreg a la pila donde solo estaran los nombres pertenecientes a la direccion de memoria 
        boolean existe = false;
        if (stackPointer) {

            for (int i = 0; i < getCaracObjsFinalPila().size(); i++) {
                // si en la lista de la ram hay ya un objeto con el mismo nombre y pertenece a la misma pagina y esta declarado como la cabeza(StackPointer)
                if ((getDirMemoria().get(i).equals(objPagina.getIdMemoria()) || caracObjsFinalPila.get(i).getPilaPerteneciente() == nombrePaginaOrigen) && caracObjsFinalPila.get(i).isStackPointer()) {
                    //remplazar solo la cabeza
                    getDirMemoria().set(i, objPagina.getIdMemoria());
                    existe = true;
                }
            }
            // DE NO ENCONTRAR ALGUN STACK POINTER DE LA PAGINA SE PROCEDE a al push
            if (!existe) {
                pushRamInterno(objPagina, nombrePaginaOrigen, stackPointer);

            }

        } else {
            pushRamInterno(objPagina, nombrePaginaOrigen, stackPointer);
        }

        //se agrega a la pila q tendra la informacion de donde viene ese stackpointer y todo lo q necesite
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
    public LinkedList<CaracteristicasIdMemoria> getCaracObjsFinalPila() {
        return caracObjsFinalPila;
    }

    /**
     * @param caracObjsFinalPila the caracObjsFinalPila to set
     */
    public void setCaracObjsFinalPila(LinkedList<CaracteristicasIdMemoria> caracObjsFinalPila) {
        this.caracObjsFinalPila = caracObjsFinalPila;
    }

    private void pushRamInterno(CaracteristicasObjPagina objPagina, int nombrePaginaOrigen, boolean stackPointer) {
        getDirMemoria().addFirst(objPagina.getIdMemoria());
        getCaracObjsFinalPila().addFirst(new CaracteristicasIdMemoria(nombrePaginaOrigen, stackPointer));
    }

    public void pullRam(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            getCaracObjsFinalPila().removeFirst();
            getDirMemoria().removeFirst();
        }

    }
}
