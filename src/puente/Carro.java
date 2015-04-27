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
public class Carro extends Thread {
    private String posicionInicial;
    private Double peso;
    private Double velocidad;
    private Integer tiempo;
    private Puente puente;

    public Carro(String posicionInicial, Double peso, Double velocidad) {
        this.posicionInicial = posicionInicial;
        this.peso = peso;
        this.velocidad = velocidad;
        //this.puente = puente;
        if(puente != null) {
            tiempo= ((Double)(puente.getDistancia()/velocidad)).intValue();
        }
    }
    
    public String getPosicionInicial() {
        return posicionInicial;
    }

    public void setPosicionInicial(String posicionInicial) {
        this.posicionInicial = posicionInicial;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
        if(puente != null) {
            Double t = puente.getDistancia()/velocidad;
            setTiempo(t.intValue());
        }
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public Puente getPuente() {
        return puente;
    }

    public void setPuente(Puente puente) {
        this.puente = puente;
    }
    
    @Override
    public void run() {
        try {
            Integer t = tiempo*1000;
            sleep(t.longValue());
            puente.cambiarSemaforos(this);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Carro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
