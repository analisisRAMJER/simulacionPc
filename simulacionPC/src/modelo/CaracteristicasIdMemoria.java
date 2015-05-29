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
public class CaracteristicasIdMemoria {
    private int pilaPerteneciente;
    private boolean StackPointer;
    

    public CaracteristicasIdMemoria() {
    }

    public CaracteristicasIdMemoria(int pilaPerteneciente, boolean StackPointer) {
        this.pilaPerteneciente = pilaPerteneciente;
        this.StackPointer = StackPointer;
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

    /**
     * @return the StackPointer
     */
    public boolean isStackPointer() {
        return StackPointer;
    }

    /**
     * @param StackPointer the StackPointer to set
     */
    public void setStackPointer(boolean StackPointer) {
        this.StackPointer = StackPointer;
    }

    
    
}
