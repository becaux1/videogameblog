package com.example.david.misrestaurantes;

import android.provider.BaseColumns;

/**
 * Created by david on 11/03/2017.
 */

public class VideojuegoContract {

    public static abstract class VideojuegosEntry implements BaseColumns{
        public static final String TABLE_NAME= "videojuegos";
        public static final String ID= "id";
        public static final String NOMBRE= "nombre";
        public static final String FOTO= "foto";
        public static final String PLATAFORMA = "plataforma";
        public static final String VALORACION= "valoracion";

    }//RestaurantesEntry
}//VideojuegoContract
