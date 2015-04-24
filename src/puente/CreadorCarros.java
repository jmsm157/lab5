/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puente;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johan
 */
public class CreadorCarros extends Thread {
    private final Puente puente;
    
    public CreadorCarros(final Puente puente) {
        this.puente = puente;
    }
    
    /**
     * Crea el carro con parametros aleatoreos.
     * 
     * Calcula y espera un tiempo aleatorio.
     * Crea el carro.
     * Lo envia a la cola indicada.
     * 
     */
    public void crearCarro() {
        try {
            Long tiempo = 333L; // Calcula el tiempo aleatorio
            sleep(tiempo);
            
            // Aqui debe crear el carro y mandarlo a la cola indicada.
            
            crearCarro();
        } catch (InterruptedException ex) {
            Logger.getLogger(CreadorCarros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        crearCarro();
    }
}
