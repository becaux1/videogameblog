package com.example.david.misrestaurantes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import java.io.ByteArrayOutputStream;

public class FormularioActivity extends AppCompatActivity {

        //CONSTANTES
        static final int REQUEST_IMAGE_CAPTURE = 1;


    //ATRIBUTOS
    protected Videojuego videojuego;
    protected Intent intent;

    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        intent= getIntent();

        if(intent.getExtras()!=null){
            rellenarFormulario();
        }//if
    }//onCreate

    private void rellenarFormulario(){

        Bundle extras= intent.getExtras();

        ((EditText)findViewById(R.id.et_nombre)).setText(extras.getString(MainActivity.NOMBRE));
        ((ImageView)findViewById(R.id.iv_foto)).setImageBitmap((Bitmap)extras.get(MainActivity.FOTO));
        ((EditText)findViewById(R.id.et_direccion)).setText(extras.getString(MainActivity.PLATAFORMA));

        RadioGroup radioGroup= (RadioGroup)findViewById(R.id.rg_valoracion);

        switch (extras.getInt(MainActivity.VALORACION, 1)){
            case 0:
                radioGroup.check(R.id.rb_malo);
                break;
            case 1:
                radioGroup.check(R.id.rb_medio);
                break;
            case 2:
                radioGroup.check(R.id.rb_bueno);
                break;
        }//switch

    }//rellenarFormulario

    public void guardar(View view){

        ImageView imageView= (ImageView) findViewById(R.id.iv_foto);
        Bitmap bitmap = null;
        ByteArrayOutputStream baos = null;
        byte[] byteArray = new byte[0];
        try{
            bitmap= ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            if(bitmap!=null){
                baos= new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.WEBP, 100, baos);
                byteArray= baos.toByteArray();
            }


        }catch (Exception e){}

        String nombre= ((EditText)findViewById(R.id.et_nombre)).getText().toString();
        String direccion= ((EditText)findViewById(R.id.et_direccion)).getText().toString();
        int valoracion=0;

        switch (((RadioGroup)findViewById(R.id.rg_valoracion)).getCheckedRadioButtonId()){

            case R.id.rb_bueno:
                valoracion= 0;
                break;
            case R.id.rb_medio:
                valoracion= 1;
                break;
            case R.id.rb_malo:
                valoracion= 2;
                break;

        }//switch

        if(intent.getExtras()!=null){

            Bundle extra= intent.getExtras();
            db= new DBHelper(getApplicationContext());
            db.actualizar(extra.getInt("ID"), nombre, byteArray, direccion, valoracion);
            db.close();

        }else {
            db = new DBHelper(getApplicationContext());
            db.agregar(nombre, byteArray, direccion, valoracion);
            db.close();
        }
        setResult(RESULT_OK, intent);
        finish();


    }//guardar

    public void hacerFoto(View view){

        Intent takePictureIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }//if

    }//hacerFoto

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){

            Bundle extras= data.getExtras();
            Bitmap imagen= (Bitmap)extras.get("data");
            ((ImageView)findViewById(R.id.iv_foto)).setImageBitmap(imagen);

        }//if
    }//onActivityResult




}//FormularioActivity
