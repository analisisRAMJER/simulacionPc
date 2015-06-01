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
public class Procesador {
    private LinkedList<Integer>nombresDiscos=new LinkedList<>();
   
    private b arbol = new b();
    private Ram ram= new Ram();
    
    /*se realizara buscada de paginas,
    se ingresara a una nueva pila 
    adicionara a el arbol el cual sera la simulacion del disco duro */
    public void procesar(){
        int i=1;
        Lectura auxLee=  new Lectura(new String("C:\\Users\\TOSHIBA\\Documents\\ANALISIS\\PROYECTO\\simulacionPC\\simulacionPc\\simulacionPC\\src\\b\\documento"+i+".doc"));
        
        while (auxLee.isEncontro()) { 
            //obtiene las palabras o objetos del documento
            String[] AuxObjetos=auxLee.Leer();
             //crea una nueva pila para adicionar los elemento y esta pila es agregada al arbol
            guardarPagina(apilar(AuxObjetos));
            //cargar a ram stakPointer
            
            
            i++;
            //se intenta procesar una nueva pagina
            auxLee=  new Lectura(new String("C:\\Users\\TOSHIBA\\Documents\\ANALISIS\\PROYECTO\\simulacionPC\\simulacionPc\\simulacionPC\\src\\b\\documento"+i+".doc"));
        }
        System.out.println("bien estos son los nombres de las pilas: "+getNombresDiscos());
}
    //crea la nueva pila e ingresa sus objetos
    public Pagina apilar(Object A[]){
        Pagina pag= new Pagina();
        for (Object obj : A) {
            
            pag.pushPila(obj);
        }
        return pag;
    }
//se guarda la pila en el arbol
    private void guardarPagina(Pagina pagina) {
        getNombresDiscos().add(pagina.getCodigo());
        getArbol().add(pagina.getCodigo(), pagina);
       cargarEnRam(pagina.obtnerStackPointer(),pagina.getCodigo(),true);
    }
    // agregar mas elementos a una pila
    public void nuevoObjPila(int IdPila){
        Object pila = getArbol().search(IdPila);
        
    }
// se envia el stack pointer a la ram
   
    public void cargarEnRam(CaracteristicasObjPagina objPagina, int codigo, boolean stackPointer) {
        getRam().pushRam(objPagina, codigo,stackPointer);  }

    /**
     * @return the nombresDiscos
     */
    public LinkedList<Integer> getNombresDiscos() {
        return nombresDiscos;
    }

    /**
     * @param nombresDiscos the nombresDiscos to set
     */
    public void setNombresDiscos(LinkedList<Integer> nombresDiscos) {
        this.nombresDiscos = nombresDiscos;
    }

   
    /**
     * @return the ram
     */
    public Ram getRam() {
        return ram;
    }

    /**
     * @param ram the ram to set
     */
    public void setRam(Ram ram) {
        this.ram = ram;
    }

    /**
     * @return the arbol
     */
    public b getArbol() {
        return arbol;
    }

    /**
     * @param arbol the arbol to set
     */
    public void setArbol(b arbol) {
        this.arbol = arbol;
    }

}
