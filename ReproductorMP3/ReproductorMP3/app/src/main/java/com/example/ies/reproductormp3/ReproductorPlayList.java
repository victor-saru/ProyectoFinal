package com.example.ies.reproductormp3;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;

import com.example.ies.reproductormp3.BBDD.ConexionSQLiteHelper;
import com.example.ies.reproductormp3.BBDD.ReproductorBBDD;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.OffsetDateTime;
import java.util.ArrayList;

public class ReproductorPlayList extends AppCompatActivity implements MediaController.MediaPlayerControl{

    private  ArrayList<String> SongsPath;
    private ConexionSQLiteHelper conn;
    private int id_playlist;
    private ListView listSongs;
    private ArrayList<Song> songList;
    private MusicController controller;
    private MusicService musicSrv;
    private boolean paused=false, playbackPaused=false;
    private boolean musicBound=false;
    private Intent playIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproductor_play_list);

        listSongs = (ListView) findViewById(R.id.listSongs);
        songList = new ArrayList<>();
        SongsPath = new ArrayList<>();

        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_reproductor", null, 1);




        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(getResources().getString(R.string.app_name));
            actionBar.setDisplayHomeAsUpEnabled(true);


        }

        Bundle bundleActivity = this.getIntent().getExtras();

        id_playlist = bundleActivity.getInt("id_playlist");

        Log.e("kk", String.valueOf(id_playlist));


        listSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                musicSrv.setSong(Integer.parseInt(view.getTag().toString()));

                musicSrv.playSong();

                if(playbackPaused){

                    setController();
                    playbackPaused=false;
                }

                controller.show(0);

            }

        });



        SQLiteDatabase dbplaylist = conn.getReadableDatabase();

        Cursor cursor =  dbplaylist.rawQuery("SELECT " + ReproductorBBDD.CAMP_RUTA + " FROM " + ReproductorBBDD.TAULA_CANÇO + " WHERE " + ReproductorBBDD.CAMP_ID_LLISTA + " = " + id_playlist, null);


        while(cursor.moveToNext()){
            String path = cursor.getString(0);
            SongsPath.add(path);
        }

        getSongList();


        SongAdapter songAdt = new SongAdapter(this, songList);
        listSongs.setAdapter(songAdt);

        setController();

        listSongs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {

                AlertDialog.Builder confirmacio = new AlertDialog.Builder(ReproductorPlayList.this);
                confirmacio.setTitle("Eliminar Canço Playlist");
                confirmacio.setMessage("Vols eliminar la canço de la playlist?");

                confirmacio.setCancelable(false);

                confirmacio.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        aceptar(songList.get(pos).getPath());
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
        });


    }

    private void aceptar(String path) {

        SQLiteDatabase db = conn.getWritableDatabase();

        Cursor cursor = db.rawQuery("DELETE FROM " + ReproductorBBDD.TAULA_CANÇO + " WHERE " + ReproductorBBDD.CAMP_RUTA + " LIKE '" + path + "'", null);

        cursor.moveToNext();

        Toast.makeText(getApplicationContext(), "Canço eliminada", Toast.LENGTH_SHORT).show();

        finish();
        startActivity(getIntent());
    }


    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;

            musicSrv = binder.getService();

            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }

       /* else{
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }*/
    }

    public void songPicked(View view){
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        musicSrv.playSong();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void setController() {
        controller = new MusicController(this);
        //set previous and next button listeners
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
                listSongs.setSelection(musicSrv.getSongPosn());
                Log.e("qq", String.valueOf(musicSrv.getSongPosn()));
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
                listSongs.setSelection(musicSrv.getSongPosn());
            }
        });
        //set and show
        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.listSongs));
        controller.setEnabled(true);
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //menu item selected
        switch (item.getItemId()) {
            case R.id.action_playlists:
                Intent i = new Intent(this, Playlists.class);
                startActivity(i);
                break;



            case android.R.id.home:
                musicSrv.onUnbind(playIntent);

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void playNext(){
        musicSrv.playNext();

        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    private void playPrev(){
        musicSrv.playPrev();
        if(playbackPaused){
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    @Override
    protected void onPause(){

        super.onPause();
        paused=true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(paused){
            setController();
            paused=false;
        }
    }

    @Override
    protected void onStop() {
        controller.hide();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv=null;
        super.onDestroy();
    }


    private void getSongList(){

        ContentResolver musicResolver = getContentResolver();

        Uri musicUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;

        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);

            int pathColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisPath = musicCursor.getString(pathColumn);

                if(SongsPath.contains(thisPath))
                    songList.add(new Song(thisId, thisTitle, thisArtist, thisPath));

            }
            while (musicCursor.moveToNext());


        }

        else
            Log.e("kk", "NADA");


    }

    @Override
    public void start() {
        musicSrv.go();
    }

    @Override
    public void pause() {
        playbackPaused=true;
        musicSrv.pausePlayer();
    }

    @Override
    public int getDuration() {
        if(musicSrv!=null && musicBound && musicSrv.isPng())
            return musicSrv.getDur();
        else return 0;
    }

    @Override
    public int getCurrentPosition() {

        if(musicSrv!=null && musicBound && musicSrv.isPng())
            return musicSrv.getPosn();
        else return 0;
    }

    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);
    }

    @Override
    public boolean isPlaying() {
        if(musicSrv!=null && musicBound)
            return musicSrv.isPng();
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
