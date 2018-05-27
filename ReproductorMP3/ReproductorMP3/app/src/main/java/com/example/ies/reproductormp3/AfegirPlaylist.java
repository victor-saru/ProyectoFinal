package com.example.ies.reproductormp3;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ies.reproductormp3.BBDD.ConexionSQLiteHelper;
import com.example.ies.reproductormp3.BBDD.ReproductorBBDD;
import com.example.ies.reproductormp3.Entitats.Llista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AfegirPlaylist extends AppCompatActivity {

    private ListView llistaCansons;
    private Button bttCrear;
    private Button bttCancelar;
    private ArrayList<Song> songList;
    private SongAdapter songAdt;
    private ArrayList<String> NomCansons;
    private ArrayAdapter<String> adapter;
    private ArrayList<Song> selectedItems;
    private ConexionSQLiteHelper conn;
    private EditText edtNom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afegir_playlist);

        llistaCansons = (ListView) findViewById(R.id.listCansons);
        bttCrear = (Button) findViewById(R.id.bttCrear);
        bttCancelar = (Button) findViewById(R.id.bttCancelar);
        edtNom = (EditText) findViewById(R.id.edtNom);


        songList = new ArrayList<Song>();

        getSongList();

        Collections.sort(songList, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        songAdt = new SongAdapter(this, songList);
        NomCansons = new ArrayList<String>();
        for(int i = 0; i < songList.size(); i++){
            NomCansons.add(songList.get(i).getTitle());
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, NomCansons);

        llistaCansons.setAdapter(adapter);




        bttCrear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                SparseBooleanArray checked = llistaCansons.getCheckedItemPositions();

                for (int i = 0; i < checked.size(); i++) {
                    // Item position in adapter
                    int position = checked.keyAt(i);
                    // Add sport if it is checked i.e.) == TRUE!
                    if (checked.valueAt(i))
                        selectedItems.add(songList.get(position));
                }
                for(int i = 0; i < selectedItems.size(); i++){
                    Log.e("kk", selectedItems.get(i).toString());
                }

                conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_reproductor", null, 1);
                SQLiteDatabase db = conn.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(ReproductorBBDD.CAMP_NOM_LLISTA, edtNom.getText().toString());
                Long idResultat = db.insert(ReproductorBBDD.TAULA_LLISTA, ReproductorBBDD.CAMP_ID,values);

                db.close();

                SQLiteDatabase db2 = conn.getReadableDatabase();
                Cursor cursor = db2.rawQuery("SELECT " + ReproductorBBDD.CAMP_ID + " FROM " + ReproductorBBDD.TAULA_LLISTA + " WHERE " + ReproductorBBDD.CAMP_NOM_LLISTA + " LIKE '" + edtNom.getText().toString() + "'", null);



                cursor.moveToNext();
                int id_llista = cursor.getInt(0);


                for(int i = 0; i < selectedItems.size(); i++){
                    ContentValues valuesSongs = new ContentValues();
                    valuesSongs.put(ReproductorBBDD.CAMP_ID_LLISTA, id_llista);
                    valuesSongs.put(ReproductorBBDD.CAMP_RUTA, selectedItems.get(i).getPath());
                    db2.insert(ReproductorBBDD.TAULA_CANÇO, ReproductorBBDD.CAMP_ID_LLISTA, valuesSongs);
                    Log.e("kk", String.valueOf(id_llista));
                    Log.e("kk", selectedItems.get(i).getPath());
                }




                if(idResultat == -1){
                    Toast.makeText(getApplicationContext(),"Playlist ja creada", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(),"Playlist creada", Toast.LENGTH_SHORT).show();
                }

                finish();

            }

        });
        selectedItems = new ArrayList<Song>();



        }



    public void getSongList(){
        //query external audio
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        //iterate over results if valid
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);

            int pathColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA); //modificado
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisPath = musicCursor.getString(pathColumn);
                Log.e("kk", thisPath);
                songList.add(new Song(thisId, thisTitle, thisArtist, thisPath));
            }
            while (musicCursor.moveToNext());
        }
    }
}
