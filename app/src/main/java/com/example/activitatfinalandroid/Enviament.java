package com.example.activitatfinalandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

public class Enviament extends AsyncTask<String, Integer, String> {
    private Preferencies pref;
    public Enviament(Preferencies pref){
        this.pref = pref;
    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(String... params) {
        return Auxiliar.interacionPost(params[0], pref.getCodiusuari(),
                false);
    }
    @Override
    protected void onProgressUpdate(Integer... values){
    }
}