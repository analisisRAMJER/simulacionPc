/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.util.LinkedList;

/**
 *
 * @author TOSHIBA
 */
public class nodo {
    private LinkedList <nodo> listaHijos= new LinkedList<>();
    private nodo nodoPropio= new nodo();
    private String contenido="";
    private int peso=0;
    private File pagina;
    private String identificador="";

    /**
     * @return the listaHijos
     */
    public LinkedList <nodo> getListaHijos() {
        return listaHijos;
    }

    /**
     * @param listaHijos the listaHijos to set
     */
    public void setListaHijos(LinkedList <nodo> listaHijos) {
        this.listaHijos = listaHijos;
    }

    /**
     * @return the identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * @param identificador the identificador to set
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    
    
    
}
