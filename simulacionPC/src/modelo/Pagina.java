/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author TOSHIBA
 */

//cada pagina sera lo que se agregara a cada uno de los nodos del arbol
public class Pagina {
    private int codigo;
    //utilizar cada linkedlist para emular la pila
    //objetosPila tendra cada tipo de objeto
    private LinkedList<Object> objetosPila=new LinkedList<>();
    /* caracPila tendra las especificaciones  como son :
    el id o referencia a la memoria : h00x0014 
    peso: 30 kbs */
    
    private LinkedList<CaracteristicasObjPagina>caracPila= new LinkedList<>();

    public Pagina() {
        this.codigo=asignarNombre();
    }

    public Pagina(int codigo) {
        this.codigo = codigo;
    }
    // apilar 
    public boolean pushPila(Object obj){
        objetosPila.addFirst(obj);
       //CREAR METODO PARA CALCULAR PESO ------------------------------OJO-------------FALTA
        //double auxPeso=calcularPeso();
       //se  agregaran las caracteristicas dentro de  una pila  esto con el fin de facilidad de extecion o modificaciones
        String auxId= asignarIdentificador();
                caracPila.addFirst(new CaracteristicasObjPagina(auxId));
//el ideal es con los dos valores, incluyendo calcular el peso------------------------------OJO-------------FALTA
        //caracPila.addFirst(new CaracteristicasObjPagina(auxId, auxPeso));
        return true;
    }
    //desapilar
    public boolean popPila(){
        objetosPila.removeFirst();
        caracPila.removeFirst();
        return true;
    }
    // asigna el nombre a cada objeto 
    private int asignarNombre() {
        Random  rnd = new Random();
        int nombre=((int) (rnd.nextInt()*rnd.nextDouble()));
        if (nombre < 0) {
            nombre=nombre*-1;
        }
        System.out.println("nombre asignado a la pila "+nombre);
        return nombre;
    }
// asigna el nombre o identificador  a la pila
    private String asignarIdentificador() {
        String VecAux[]={"A","B","C","D","E","F"};
        int auxInt=(int)(Math.random()*6 + 0);
        
        String id=0+VecAux[auxInt]+asignarNombre();
        System.out.println("objecto con nombre: "+id+"asignado a la  de la pila: "+codigo);
        
        return id;
    }
    public CaracteristicasObjPagina obtnerStackPointer(){
        return caracPila.getFirst();
    }
   

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the objetosPila
     */
    public LinkedList<Object> getObjetosPila() {
        return objetosPila;
    }

    /**
     * @param objetosPila the objetosPila to set
     */
    public void setObjetosPila(LinkedList<Object> objetosPila) {
        this.objetosPila = objetosPila;
    }

    /**
     * @return the caracPila
     */
    public LinkedList<CaracteristicasObjPagina> getCaracPila() {
        return caracPila;
    }

    /**
     * @param caracPila the caracPila to set
     */
    public void setCaracPila(LinkedList<CaracteristicasObjPagina> caracPila) {
        this.caracPila = caracPila;
    }

}
