package com.example.ies.reproductormp3;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ies.reproductormp3.BBDD.ConexionSQLiteHelper;
import com.example.ies.reproductormp3.BBDD.ReproductorBBDD;
import com.example.ies.reproductormp3.Entitats.Llista;

import java.util.ArrayList;

public class Playlists extends AppCompatActivity {

    private  ConexionSQLiteHelper conn;
    private ListView llistaPlaylists;
    private ArrayList<String> llistaInformacio;
    private ArrayList<Llista> ArrayPlaylists;
    private ArrayAdapter adapter;
    private  ArrayList<String> SongsPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_reproductor", null, 1);

        llistaPlaylists = (ListView) findViewById(R.id.listPlaylist);


        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(getResources().getString(R.string.app_name));
            actionBar.setDisplayHomeAsUpEnabled(true);


        }



        FloatingActionButton floating = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(Playlists.this, AfegirPlaylist.class);
                startActivityForResult(i,1);
            }
        });


        consultarLlistaPlaylists();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, llistaInformacio);

        llistaPlaylists.setAdapter(adapter);


        //click
        llistaPlaylists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                Intent intent = new Intent(Playlists.this,ReproductorPlayList.class);
                intent.putExtra("id_playlist",ArrayPlaylists.get(i).getId());
                startActivityForResult(intent,1);

            }
        });


        //click largo
        llistaPlaylists.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {

                AlertDialog.Builder confirmacio = new AlertDialog.Builder(Playlists.this);
                confirmacio.setTitle("Eliminar Playlist");
                confirmacio.setMessage("Vols eliminar la playlist?");

                confirmacio.setCancelable(false);

                confirmacio.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        aceptar(ArrayPlaylists.get(pos).getId());
                    }
                });

                confirmacio.setNegativeButton("Cancel·lar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                confirmacio.show();

                return false;
            }




            private void aceptar(int id) {

                SQLiteDatabase db = conn.getWritableDatabase();

                Cursor cursor = db.rawQuery("DELETE FROM " + ReproductorBBDD.TAULA_LLISTA + " WHERE " + ReproductorBBDD.CAMP_ID + " = " + id, null);

                cursor.moveToNext();

                Cursor cursor2 = db.rawQuery("DELETE FROM " + ReproductorBBDD.TAULA_CANÇO + " WHERE " + ReproductorBBDD.CAMP_ID_LLISTA + " = " + id, null);

                cursor2.moveToNext();


                Toast.makeText(getApplicationContext(), "Playlist eliminada", Toast.LENGTH_SHORT).show();

                finish();
                startActivity(getIntent());

            }
        });
    }




    private void consultarLlistaPlaylists() {

        SQLiteDatabase db = conn.getReadableDatabase();

        Llista llista = null;

        ArrayPlaylists = new ArrayList<Llista>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ReproductorBBDD.TAULA_LLISTA, null);



        while(cursor.moveToNext()){
            llista = new Llista();
            llista.setId(cursor.getInt(0));

            llista.setNom(cursor.getString(1));

            ArrayPlaylists.add(llista);

        }

        obtenirLlista();
    }

    private void obtenirLlista() {

        llistaInformacio = new ArrayList<String>();

        for (int i = 0; i < ArrayPlaylists.size(); i++){
            llistaInformacio.add(ArrayPlaylists.get(i).getId()+" - "
                    + ArrayPlaylists.get(i).getNom());


        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        consultarLlistaPlaylists();
        Intent refresh = new Intent(this, Playlists.class);
        startActivity(refresh);//Start the same Activity
        finish(); //finish Activity.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //menu item selected
        switch (item.getItemId()) {
            

            case android.R.id.home:


                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
