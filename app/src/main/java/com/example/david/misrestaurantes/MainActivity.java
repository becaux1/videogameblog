package com.example.david.misrestaurantes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

        //CONSTANTES

        //Definimos las constantes que utilizaremos para los startActivityForResult
    public static final int INSERT_CODE= 0;
    public static final int EDIT_CODE= 1;
    public static final int RESUMEN_CODE= 2;

        //Aquí definimos las constantes relacionadas con los atributos de la clase Videojuego
    public static String NOMBRE= "nombre";
    public static String FOTO= "foto";
    public static String PLATAFORMA = "plataforma";
    public static String VALORACION= "valoracion";
    public static String POSICION= "posicion";

        //ATRIBUTOS
    public ListView listView;
    public ArrayList<Videojuego> modelo;
    public ArrayAdapter<Videojuego> adapter;
    public int posicion= -1;
    public Videojuego videojuego;

    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null){
            posicion= savedInstanceState.getInt(POSICION);
        }else posicion= -1;

        modelo= new ArrayList<Videojuego>();
        //modelo.add(new Videojuego("Videojuego 1", Videojuego.Valoracion.MEH));
        //modelo.add(new Videojuego("Videojuego 2", Videojuego.Valoracion.BUENO));
        db= new DBHelper(getApplicationContext());
        db.recuperar(modelo);

        listView= (ListView)findViewById(R.id.listView);
        adapter= new MiAdapter(this, modelo);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(this);
    }//onCreate

    public void onResume(){
        super.onResume();
    }//onResume

    public void onSaveInstanceState(Bundle bundle){
        if(posicion>0){
            bundle.putInt(POSICION, posicion);
        }//if
    }//onSaveInstanceState

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu, menu);
    }//onCreateContextMenu

    public boolean onContextItemSelected(MenuItem menuItem){
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        posicion= info.position;
        switch (menuItem.getItemId()){
            case R.id.menu_editar:
                Intent intent= new Intent(this, FormularioActivity.class);
                videojuego = modelo.get(posicion);
                intent.putExtra(NOMBRE, videojuego.nombre);
                intent.putExtra(FOTO, videojuego.foto);
                intent.putExtra(PLATAFORMA, videojuego.direccion);
                intent.putExtra(VALORACION, videojuego.valoracion.ordinal());
                intent.putExtra(POSICION, posicion);
                intent.putExtra("ID", videojuego.id);

                /*
                db= new DBHelper(getApplicationContext());
                db.borrar(videojuego.id);
                db.close();
                */
                startActivityForResult(intent, EDIT_CODE);
                break;
            case R.id.menu_borrar:

                //DBHelper db= new DBHelper(get1ApplicationContext());

                videojuego = modelo.get(posicion);
                db.borrar(videojuego.id);

                adapter.clear();
                modelo= new ArrayList<Videojuego>();
                db.recuperar(modelo);
                listView= (ListView)findViewById(R.id.listView);
                adapter= new MiAdapter(this, modelo);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();



        }//switch
        return true;
    }//onContextItemSelected

    public void insertar(View view){

        Intent intent= new Intent(this, FormularioActivity.class);
        startActivityForResult(intent, INSERT_CODE);

    }//insertar

    public void onActivityResult(int requestCode, int resultCode, Intent intent){

        if(requestCode!=RESUMEN_CODE){
            Bundle bundle= intent.getExtras();
            /*
            Videojuego videojuego= new Videojuego(bundle.getString(NOMBRE), (Bitmap)bundle.get(FOTO),
                    bundle.getString(PLATAFORMA), Videojuego.Valoracion.MEH);

            switch (bundle.getInt(VALORACION)){
                case 0:
                    videojuego.valoracion= Videojuego.Valoracion.BUENO;
                    break;
                case 1:
                    videojuego.valoracion= Videojuego.Valoracion.MEH;
                    break;
                case 2:
                    videojuego.valoracion= Videojuego.Valoracion.MALO;
                    break;
            }//switch

            if(requestCode==EDIT_CODE){

                adapter.remove(modelo.get(posicion));
                adapter.insert(videojuego, posicion);

            }else{
                adapter.add(videojuego);
            }//if/else anidado
            */

            adapter.clear();
            modelo= new ArrayList<Videojuego>();
            //modelo.add(new Videojuego("Videojuego 1", Videojuego.Valoracion.MEH));
            //modelo.add(new Videojuego("Videojuego 2", Videojuego.Valoracion.BUENO));
            db.recuperar(modelo);
            listView= (ListView)findViewById(R.id.listView);
            adapter= new MiAdapter(this, modelo);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }//if



    }//onActivityResult

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        posicion= position;

        Intent intent= new Intent(this, FichaActivity.class);
        videojuego = modelo.get(posicion);
        intent.putExtra(NOMBRE, videojuego.nombre);
        intent.putExtra(FOTO, videojuego.foto);
        intent.putExtra(PLATAFORMA, videojuego.direccion);
        intent.putExtra(VALORACION, videojuego.valoracion);

        startActivityForResult(intent, RESUMEN_CODE);

    }//onItemClick
}//MainActivity
