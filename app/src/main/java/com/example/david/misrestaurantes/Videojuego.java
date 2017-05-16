package com.example.david.misrestaurantes;

import android.graphics.Bitmap;

/**
 * Created by david on 05/03/2017.
 */

public class Videojuego {

        //ATRIBUTOS
    protected String nombre, direccion;
    protected Bitmap foto;
    protected enum Valoracion{MALO, MEH, BUENO};
    protected Valoracion valoracion;
    protected int id;

        //CONSTRUCTOR/ES
    public Videojuego(int id, String nombre, Bitmap foto, String direccion, Valoracion valoracion){
        this.id= id;
        this.nombre= nombre;
        this.foto= foto;
        this.direccion= direccion;
        this.valoracion= valoracion;
    }//constructor

    public Videojuego(String nombre, Valoracion valoracion){
        this.nombre= nombre;
        this.valoracion= valoracion;
    }//constructor

}//Videojuego
