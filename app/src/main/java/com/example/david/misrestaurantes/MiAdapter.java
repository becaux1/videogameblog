package com.example.david.misrestaurantes;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by david on 05/03/2017.
 */

public class MiAdapter extends ArrayAdapter<Videojuego> {

        //definimos la estructura del ViewHolder
    static class ViewHolder{
            protected TextView tv_nombre;
            //protected TextView tv_direccion;
            protected ImageView iv_mini;
            protected LinearLayout linearLayout;
        }//ViewHolder

        //ATRIBUTOS
    private Activity ctx;
    private ArrayList<Videojuego> modelo;

        //CONSTRUCTOR/ES
    public MiAdapter(Activity ctx, ArrayList<Videojuego> modelo){
        super(ctx, R.layout.layout_fila, modelo);
        this.ctx= ctx;
        this.modelo= modelo;

    }//MiAdapter

        //METODOS
    public View getView(int posicion, View convertView, ViewGroup parent){

        View fila= convertView;

        if(fila==null){
            fila= ctx.getLayoutInflater().inflate(R.layout.layout_fila, null);
            ViewHolder vh= new ViewHolder();
            vh.tv_nombre= (TextView)fila.findViewById(R.id.tv_nombre);
            vh.linearLayout= (LinearLayout)fila.findViewById(R.id.ll_fila);
            //vh.tv_direccion= (TextView)fila.findViewById(R.id.tv_direccion);
            vh.iv_mini= (ImageView)fila.findViewById(R.id.iv_mini);

            fila.setTag(vh);
        }//if

        ViewHolder vh= (ViewHolder)fila.getTag();
        Videojuego videojuego = modelo.get(posicion);
        vh.tv_nombre.setText(videojuego.nombre);
        //vh.tv_direccion.setText(videojuego.direccion);
        vh.iv_mini.setImageBitmap(videojuego.foto);

        if (videojuego.valoracion.equals(Videojuego.Valoracion.BUENO)){
            vh.linearLayout.setBackgroundColor(Color.parseColor("#4caf50"));
        }else if(videojuego.valoracion.equals(Videojuego.Valoracion.MEH)){
            vh.linearLayout.setBackgroundColor(Color.parseColor("#ffee58"));
        }else if (videojuego.valoracion.equals(Videojuego.Valoracion.MALO)){
            vh.linearLayout.setBackgroundColor(Color.parseColor("#ef5350"));
        }//ifelses

        return fila;

    }//getView
}//MiAdapter
