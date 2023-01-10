package com.example.pmdm_09_firebase;

import java.util.ArrayList;

public class Persona {
    private String nombre;
    private int Edad;


    //Firebase exige un constructor vacio!!

    public Persona() {
    }

    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        Edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", Edad=" + Edad +
                '}';
    }
}
