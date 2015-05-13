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
public class discoDuro {
    LinkedList<String[]>listaPaginas= new LinkedList<>();
    Lectura leer=new Lectura();
    public boolean agregarAdisco(){
        String ruta = null;
        String[] pagina = leer.Leer(ruta);
        listaPaginas.add(pagina);
        return true;
    }
    
    
    
    
}
