package com.example.david.misrestaurantes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Created by david on 11/03/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    //CONSTANTES
    public static final int DATABASE_VERSION= 1;
    public static final String DATABASE_NAME= "videojuegos.db";

    //CONSTRUCTOR/ES
    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }//constructor

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ VideojuegoContract.VideojuegosEntry.TABLE_NAME+ "("
                + VideojuegoContract.VideojuegosEntry._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + VideojuegoContract.VideojuegosEntry.NOMBRE+ " TEXT,"
        + VideojuegoContract.VideojuegosEntry.FOTO+ " BLOB,"
        + VideojuegoContract.VideojuegosEntry.PLATAFORMA+ " TEXT,"
        + VideojuegoContract.VideojuegosEntry.VALORACION+ " INTEGER"
        +")");

    }//onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }//onUpgrade

    public void agregar(String nombre, byte[] imagen, String plataforma, int valoracion){

        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values= new ContentValues();

        values.put(VideojuegoContract.VideojuegosEntry.NOMBRE, nombre);
        values.put(VideojuegoContract.VideojuegosEntry.FOTO, imagen);
        values.put(VideojuegoContract.VideojuegosEntry.PLATAFORMA, plataforma);
        values.put(VideojuegoContract.VideojuegosEntry.VALORACION, valoracion);

        database.insert(VideojuegoContract.VideojuegosEntry.TABLE_NAME, null, values);

    }//agregar

    public void recuperar(ArrayList<Videojuego> modelo){
        SQLiteDatabase database= getReadableDatabase();
        Cursor cursor= database.rawQuery("SELECT * FROM "+ VideojuegoContract.VideojuegosEntry.TABLE_NAME, null);

        String nombre, plataforma;
        byte[] foto;
        int valoracion;

        Bitmap bitmap;
        ByteArrayInputStream bais;

        int id;

        if(cursor.moveToFirst()){
            do {
                nombre= cursor.getString(cursor.getColumnIndex(VideojuegoContract.VideojuegosEntry.NOMBRE));
                foto= cursor.getBlob(cursor.getColumnIndex(VideojuegoContract.VideojuegosEntry.FOTO));

                bais= new ByteArrayInputStream(foto);

                plataforma= cursor.getString(cursor.getColumnIndex(VideojuegoContract.VideojuegosEntry.PLATAFORMA));
                valoracion= cursor.getInt(cursor.getColumnIndex(VideojuegoContract.VideojuegosEntry.VALORACION));

                Videojuego.Valoracion v;
                if(valoracion==0){
                    v= Videojuego.Valoracion.BUENO;
                }else if (valoracion==1){
                    v= Videojuego.Valoracion.MEH;
                }else v= Videojuego.Valoracion.MALO;

                id= cursor.getInt(cursor.getColumnIndex(VideojuegoContract.VideojuegosEntry._ID));

                modelo.add(new Videojuego(id, nombre, BitmapFactory.decodeStream(bais), plataforma, v));


            }while (cursor.moveToNext());
        }//if
    }

    public void borrar(int posicion){
        Log.i("Posicion", String.valueOf(posicion));


        SQLiteDatabase database= getReadableDatabase();
        int id;
        Cursor cursor= database.rawQuery("SELECT * FROM "+ VideojuegoContract.VideojuegosEntry.TABLE_NAME, null);

        if(cursor.moveToFirst()){
            do{
                id= cursor.getInt(cursor.getColumnIndex(VideojuegoContract.VideojuegosEntry._ID));
                Log.i("Posicion", String.valueOf(id));
                if(id==posicion){
                    break;
                }//ifa1
            }while (cursor.moveToNext());

        }//if

        SQLiteDatabase db2= getWritableDatabase();
        db2.delete(VideojuegoContract.VideojuegosEntry.TABLE_NAME, VideojuegoContract.VideojuegosEntry._ID+"="+posicion,null);
    }//borrar

    public void actualizar(int id, String nombre, byte[] imagen, String plataforma, int valoracion){

        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values= new ContentValues();

        values.put(VideojuegoContract.VideojuegosEntry.NOMBRE, nombre);
        values.put(VideojuegoContract.VideojuegosEntry.FOTO, imagen);
        values.put(VideojuegoContract.VideojuegosEntry.PLATAFORMA, plataforma);
        values.put(VideojuegoContract.VideojuegosEntry.VALORACION, valoracion);

        database.update(VideojuegoContract.VideojuegosEntry.TABLE_NAME, values, "_id="+id, null);

    }//agregar
}//DBHelper
