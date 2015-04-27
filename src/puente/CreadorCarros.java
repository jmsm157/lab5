/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puente;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johan
 */
public class CreadorCarros extends Thread {
    private final Puente puente;

    public Puente getPuente() {
        return puente;
    }

    
    public CreadorCarros(){ 
        this.puente = new Puente();
    }    
   
    
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
            
            int valorParaCrear= aleatorio();
            double peso=aleatorio()*100.5;
            double velocidad=(aleatorio()*10.5)/2;
            //Puente p = new Puente();
            /**
             * es para ratificar que la velocidad sea menor a 50km/h
             */
            if(velocidad<=50){
                /**
             * Creo un atributo para llevarlo al un if que me va 
             * indicar si se crea o no carro
             */
                if(valorParaCrear>6){
                    Carro ladoA=new Carro("der",peso,velocidad);
                    if(peso<=800)
                        puente.addCarroDer(ladoA); 
                    else
                        puente.addCarroBloqueado(ladoA);
                }
                else{
                    if(valorParaCrear<6){
                        Carro ladoB=new Carro("izq",peso,velocidad);
                        if(peso<=800)
                            puente.addCarroIzq(ladoB);
                        else
                          puente.addCarroBloqueado(ladoB);  
                    }                    
                }
            }
                
            // Aqui debe crear el carro y mandarlo a la cola indicada.
            
            crearCarro();
        } catch (InterruptedException ex) {
            Logger.getLogger(CreadorCarros.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    @Override
    public void run() {
        puente.start();
        crearCarro();        
    }
    
    public int aleatorio (){
        int N=11,M=1, r;
         r=(int) Math.floor(Math.random()*(N-M+1)+M);
         return r;
    }
}
