package com.count.svjchrysler.count.Models;

/**
 * Created by mvaldez on 15/08/2016.
 */
public class CategoryCar {
    private int id;
    private int idEncuestador;
    private int particular;
    private int bicicleta;
    private int publico;
    private int repartidor;
    private int motocicleta;
    private int taxi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEncuestador() {
        return idEncuestador;
    }

    public void setIdEncuestador(int idEncuestador) {
        this.idEncuestador = idEncuestador;
    }

    public int getParticular() {
        return particular;
    }

    public void setParticular(int particular) {
        this.particular = particular;
    }

    public int getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta(int bicicleta) {
        this.bicicleta = bicicleta;
    }

    public int getPublico() {
        return publico;
    }

    public void setPublico(int publico) {
        this.publico = publico;
    }

    public int getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(int repartidor) {
        this.repartidor = repartidor;
    }

    public int getMotocicleta() {
        return motocicleta;
    }

    public void setMotocicleta(int motocicleta) {
        this.motocicleta = motocicleta;
    }

    public int getTaxi() {
        return taxi;
    }

    public void setTaxi(int taxi) {
        this.taxi = taxi;
    }
}
