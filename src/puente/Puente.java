/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puente;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que modela el puente.
 * 
 * @author Johan
 */
public class Puente extends Thread {
    private final Integer distancia;
    private final Integer resistencia;
    
    /**
     * Parametro de tiempo cuando los semaforos
     * estan vacios.
     */
    private final Long tiempoVacio;
    
    /**
     * Semaforo Izquierdo, false = verde, true = rojo
     */
    private Boolean semaforoIzq = false;
    /**
     * Semaforo Derecho, false = verde, true = rojo
     */
    private Boolean semaforoDerecho = true;
    
    private List<Carro> carrosIzq = new ArrayList<>();
    private List<Carro> carrosDer = new ArrayList<>();
    private List<Carro> carrosBloqueados = new ArrayList<>();
    private List<Carro> carrosTerminados = new ArrayList<>();
    
    public Puente() {
        distancia = 50;
        resistencia = 800;
        tiempoVacio = 3L;
    }
    
    /**
     * Constructor con distancia y resistencia del puente
     * 
     * @param distancia
     * @param resistencia 
     * @param tiempoVacio 
     */
    public Puente(final Integer distancia, final Integer resistencia, final Long tiempoVacio) {
        this.distancia = distancia;
        this.resistencia = resistencia;
        this.tiempoVacio = tiempoVacio;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public Integer getResistencia() {
        return resistencia;
    }

    public Boolean getSemaforoIzq() {
        return semaforoIzq;
    }

    public List<Carro> getCarrosIzq() {
        return carrosIzq;
    }

    public List<Carro> getCarrosDer() {
        return carrosDer;
    }

    public List<Carro> getCarrosBloqueados() {
        return carrosBloqueados;
    }

    public List<Carro> getCarrosTerminados() {
        return carrosTerminados;
    }
    

    public void setSemaforoIzq(Boolean semaforoIzq) {
        this.semaforoIzq = semaforoIzq;
    }

    public Boolean getSemaforoDerecho() {
        return semaforoDerecho;
    }

    public void setSemaforoDerecho(Boolean semaforoDerecho) {
        this.semaforoDerecho = semaforoDerecho;
    }
    
    public void addCarroIzq(Carro carro) {
        carrosIzq.add(carro);
    }
    
    public void addCarroDer(Carro carro) {
        carrosDer.add(carro);
    }
    
    public void addCarroBloqueado(Carro carro) {
        carrosBloqueados.add(carro);
    }    
    
    /**
     * Espera el tiempo vacio, cambia los semaforos y
     * pasa el carro correspondiente.
     */
    public void cambiarSemaforos() {
        try {
            sleep(tiempoVacio*1000);
            semaforoDerecho = !semaforoDerecho;
            semaforoIzq = !semaforoIzq;
            
            pasarCarros();
        } catch (InterruptedException ex) {
            Logger.getLogger(Puente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cuando un carro atravieza el puente, cambia los semaforos
     * y pasa el siguiente carro.
     * 
     * @param carro Carro que acaba de cruzar el puente. 
     */
    public void cambiarSemaforos(Carro carro) {
        semaforoDerecho = !semaforoDerecho;
        semaforoIzq = !semaforoIzq;
        
        if(carro.getPosicionInicial().equals("izq")) {
            carrosIzq.remove(carro);
            carrosTerminados.add(carro);            
        } else {
            carrosDer.remove(carro);
            carrosTerminados.add(carro);
        }
        
        pasarCarros();
    }
    
    /**
     * Pasa los carros indicados de extremo a extremo.
     */
    public void pasarCarros() {
        if(!semaforoIzq) {
            // Si el semaforo izq esta en verde
            if(carrosIzq.size() > 0) {
                // Si hay carros en la izquierda.
                carrosIzq.get(0).start();
            } else if(carrosDer.size() > 0) {
                // Sino hay carros en la izquierda pero si en la derecha.
                semaforoDerecho = !semaforoDerecho;
                semaforoIzq = !semaforoIzq;
                pasarCarros();
            } else {
                // Si no hay carros en ninguno de los dos lados.
                cambiarSemaforos();
            }
        } else if(!semaforoDerecho) {
            // Si el semaforo der esta en verde
            if(carrosDer.size() > 0) {
                // Si hay carros en la derecha.
                carrosDer.get(0).start();
            } else if(carrosIzq.size() > 0) {
                // Sino hay carros en la derecha pero si en la izquierda.
                semaforoDerecho = !semaforoDerecho;
                semaforoIzq = !semaforoIzq;
                pasarCarros();
            } else {
                // Si no hay carros en ninguno de los dos lados.
                cambiarSemaforos();
            }
        }
    }
    
    @Override
    public void run() {
        pasarCarros();
    }
}
