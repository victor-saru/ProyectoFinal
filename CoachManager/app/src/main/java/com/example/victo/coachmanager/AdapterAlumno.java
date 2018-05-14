package com.example.victo.coachmanager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.victo.coachmanager.Alumno;

import java.util.ArrayList;


public class AdapterAlumno extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Alumno> items;

    public AdapterAlumno (Activity activity, ArrayList<Alumno> items) {
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

    public void addAll(ArrayList<Alumno> category) {
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
            v = inf.inflate(R.layout.lista_alumnos, null);
        }

        Alumno dir = items.get(position);

        TextView title = (TextView) v.findViewById(R.id.category);
        title.setText(dir.getNombre());

        TextView description = (TextView) v.findViewById(R.id.texto);
        description.setText(dir.getGenero());

        /*ImageView imagen = (ImageView) v.findViewById(R.id.imageView);
        imagen.setImageDrawable(dir.getImage());*/

        return v;
    }
}