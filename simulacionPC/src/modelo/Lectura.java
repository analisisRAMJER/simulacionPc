/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.stream.FileImageInputStream;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode

/**
 *
 * @author TOSHIBA
 */
public class Lectura {

    private String pagina = "";
    private boolean encontro = true;
    private String ruta;
    private String textoDeDoc;
    private String textoDeDocx;
    private InputStream entradaArch1;
    private InputStream entradaArch2;

    public Lectura() {
    }

    public Lectura(String ruta) {
        this.ruta = ruta;
    }

    public String[] Leer() {

        int caracter;
        
        //Se crea el objeto File con la ruta del archivo
        //En la ruta recuerden que se debe poner doble barra "\\"
        //File archivoDocx = new File(getRuta());
        File archivoDoc = new File(getRuta());

        try {
            //Creamos el stream fijense bien los objetos usados
            setEntradaArch1(new FileInputStream(archivoDoc));
            //setEntradaArch2(new FileInputStream(archivoDocx));

        } catch (Exception ex) {
            //Manejar Excepcion IO y FileNotFound
            System.out.println("Fallo al tratar de abrir el archivo.\n" + ex.toString()
                    + "Error en archivo"
                    + javax.swing.JOptionPane.ERROR_MESSAGE);
        }

        //Metodos para leer dependiendo de si es .doc o .docx
        leerDoc(getEntradaArch1());
        //leerDocx(getEntradaArch2());

        // se imprime
        System.out.println(getTextoDeDoc());
        //System.out.println(getTextoDeDocx());

        setPagina(getTextoDeDoc());
        return dividir();

    }

    private void leerDoc(InputStream doc) {
        //Creamos el extractor pasandole el stream
        WordExtractor we;
        try {
            we = new WordExtractor(doc);
            //Regresamos lo leído		
            setTextoDeDoc(we.getText());
        } catch (IOException ex) {
            Logger.getLogger(Lectura.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Fallo al leer del archivo.\n" + ex.toString()
                    + "Error en archivo" + javax.swing.JOptionPane.ERROR_MESSAGE);
        }

    }

    private void leerDocx(InputStream docx) {
        //Se crea un documento que la POI entiende pasandole el stream
        //instanciamos el obj para extraer contenido pasando el documento
        try {
            XWPFWordExtractor xwpf_we = new XWPFWordExtractor(new XWPFDocument(docx));

            setTextoDeDocx(xwpf_we.getText());
        } catch (Exception e) {
            System.out.println("Fallo al leer del archivo.\n" + e.toString()
                    + "Error en archivo" + javax.swing.JOptionPane.ERROR_MESSAGE);
        }

    }


    /*    public String[] Leer() {

     int caracter;
     String text = "";
        

     try {
     InputStreamReader archivoOrigen = new InputStreamReader(new FileInputStream(ruta));

     try {
     while ((caracter = archivoOrigen.read()) != -1) {
     text += (char) caracter;
     }
     archivoOrigen.close();
     } catch (IOException e) {
     //                javax.swing.JOptionPane.showMessageDialog(null,
     //                        "Fallo al leer del archivo.\n" + e.toString(),
     //                        "Error en archivo",
     //                        javax.swing.JOptionPane.ERROR_MESSAGE);
     System.out.println( "Fallo al leer del archivo.\n" + e.toString()
     +"Error en archivo"+javax.swing.JOptionPane.ERROR_MESSAGE);
               
     }
     } catch (FileNotFoundException e) {
            
     System.out.println("Fallo al tratar de abrir el archivo.\n" + e.toString()+
     "Error en archivo"+
     javax.swing.JOptionPane.ERROR_MESSAGE);
     encontro=false;
     }
     setPagina(text);
     return dividir();
        
     }*/
    public String[] dividir() {
        int cont = 0;

        String[] split = getPagina().split(" ");

        return split;
    }

    private void setPagina(String text) {
        pagina = text;
    }

    /**
     * @return the pagina
     */
    public String getPagina() {
        return pagina;
    }

    /**
     * @return the encontro
     */
    public boolean isEncontro() {
        return encontro;
    }

    /**
     * @param encontro the encontro to set
     */
    public void setEncontro(boolean encontro) {
        this.encontro = encontro;
    }

    /**
     * @return the ruta
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     * @return the textoDeDoc
     */
    public String getTextoDeDoc() {
        return textoDeDoc;
    }

    /**
     * @param textoDeDoc the textoDeDoc to set
     */
    public void setTextoDeDoc(String textoDeDoc) {
        this.textoDeDoc = textoDeDoc;
    }

    /**
     * @return the textoDeDocx
     */
    public String getTextoDeDocx() {
        return textoDeDocx;
    }

    /**
     * @param textoDeDocx the textoDeDocx to set
     */
    public void setTextoDeDocx(String textoDeDocx) {
        this.textoDeDocx = textoDeDocx;
    }

    /**
     * @return the entradaArch1
     */
    public InputStream getEntradaArch1() {
        return entradaArch1;
    }

    /**
     * @param entradaArch1 the entradaArch1 to set
     */
    public void setEntradaArch1(InputStream entradaArch1) {
        this.entradaArch1 = entradaArch1;
    }

    /**
     * @return the entradaArch2
     */
    public InputStream getEntradaArch2() {
        return entradaArch2;
    }

    /**
     * @param entradaArch2 the entradaArch2 to set
     */
    public void setEntradaArch2(InputStream entradaArch2) {
        this.entradaArch2 = entradaArch2;
    }
    

}
