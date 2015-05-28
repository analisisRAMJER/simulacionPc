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

// se crea esta clase para facilidad de extencion o cambios
public class CaracteristicasStackPointer {
    private int pilaPerteneciente;
    

    public CaracteristicasStackPointer() {
    }

    public CaracteristicasStackPointer(int pilaPerteneciente) {
        this.pilaPerteneciente = pilaPerteneciente;
        
    }

    
    

    /**
     * @return the pilaPerteneciente
     */
    public int getPilaPerteneciente() {
        return pilaPerteneciente;
    }

    /**
     * @param pilaPerteneciente the pilaPerteneciente to set
     */
    public void setPilaPerteneciente(int pilaPerteneciente) {
        this.pilaPerteneciente = pilaPerteneciente;
    }

    
    
}
