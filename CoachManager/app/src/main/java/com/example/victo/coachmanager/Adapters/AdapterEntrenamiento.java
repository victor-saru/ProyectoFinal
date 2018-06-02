package com.example.victo.coachmanager.Adapters;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.victo.coachmanager.Entidades.Deporte;

import com.example.victo.coachmanager.Entidades.Entrenamiento;

import com.example.victo.coachmanager.MenuActivity;

import com.example.victo.coachmanager.R;



import java.util.ArrayList;

public class AdapterEntrenamiento extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Entrenamiento> items;
    String deporte;

    public AdapterEntrenamiento(Activity activity, ArrayList<Entrenamiento> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() { /* Número elementos de la lista */
        return items.size();
    }

    public void clear() { /* Limpiamos el array de datos */
        items.clear();
    }

    public void addAll(ArrayList<Entrenamiento> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) { /* Obtenemos elementos de la lista */
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) { /* Obtenemos elementos de la lista */
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { /* Así hacemos que se vea en el ListView */

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.lista_entrenamientos, null);
        }

        Entrenamiento dir = items.get(position);
        Deporte d = new Deporte(String.valueOf(dir.getId_deporte()));

        TextView title = (TextView) v.findViewById(R.id.lv_nombre_entre);
        title.setText(dir.getNombre());


        System.out.println(MenuActivity.deportes.get(0));

        for (int i = 0; i < MenuActivity.deportes.size(); i++){
            if(MenuActivity.deportes.get(i).getId_deporte() == dir.getId_deporte()){
                deporte = MenuActivity.deportes.get(i).getNombre();
                break;
            }

        }

        System.out.println();

        TextView sport = (TextView) v.findViewById(R.id.sport);
        sport.setText(deporte);

        /*ImageView imagen = (ImageView) v.findViewById(R.id.imageView);
        imagen.setImageDrawable(dir.getImage());*/

        return v;
    }


}
