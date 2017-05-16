package com.example.david.misrestaurantes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

public class FichaActivity extends AppCompatActivity {

        //ATRIBUTOS
    protected Videojuego videojuego;
    protected Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha);

        intent= getIntent();
        rellenarFormulario();
    }//onCreate
    private void rellenarFormulario(){

        Bundle extras= intent.getExtras();
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.lineaLayout_ficha);

        ((TextView)findViewById(R.id.tv_ficha_nombre)).setText(extras.getString(MainActivity.NOMBRE));
        ((ImageView)findViewById(R.id.iv_ficha_foto)).setImageBitmap((Bitmap)extras.get(MainActivity.FOTO));
        ((TextView)findViewById(R.id.tv_ficha_direccion)).setText(extras.getString(MainActivity.PLATAFORMA));
        RadioGroup radioGroup= (RadioGroup)findViewById(R.id.rg_valoracion);

        Videojuego.Valoracion valoracion= (Videojuego.Valoracion)extras.get(MainActivity.VALORACION);

        if(valoracion.equals(Videojuego.Valoracion.BUENO)) {
            linearLayout.setBackgroundColor(Color.parseColor("#4caf50"));
        }else if(valoracion.equals(Videojuego.Valoracion.MEH))
        {
            linearLayout.setBackgroundColor(Color.parseColor("#ffee58"));
        } else if(valoracion.equals(Videojuego.Valoracion.MALO)){
            linearLayout.setBackgroundColor(Color.parseColor("#ef5350"));}//else


    }//rellenarFormulario

    public void volver(View view){
        setResult(RESULT_OK, intent);
        finish();
    }//onPulsame

}//FichaActivity
