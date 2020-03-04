package com.example.activitatfinalandroid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterQuePasa extends ArrayAdapter<Missatge> {
    private Context context;
    private ArrayList<Missatge> missatges;
    private String idUserActual;
    public AdapterQuePasa(Context context, int resource, ArrayList<Missatge>
            missatges, String idUserActual){
        super(context, resource, missatges);
        this.context = context;
        this.missatges = missatges;
        this.idUserActual = idUserActual;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Missatge missatge = missatges.get(position);
        View view;
        LayoutInflater inflater =(LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(idUserActual.equals(missatge.getUserId())){
            view = inflater.inflate(R.layout.mensaje_derecha, null);
        }else{
            view = inflater.inflate(R.layout.mensaje_izquierda, null);
        }
        TextView nombre = view.findViewById(R.id.nombre);
        TextView msg = view.findViewById(R.id.msg);
        TextView fecha = view.findViewById(R.id.fecha);
        nombre.setText(missatge.getUserName());
        msg.setText(missatge.getMsg());
        fecha.setText(missatge.getDate());
        return view;
    }
}
