package com.example.activitatfinalandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

public class Recepcio extends AsyncTask<Integer, Integer, String> {
    private ListView lv;
    private Context context;
    private Preferencies pref;
    private boolean codi;
    public Recepcio(Context context, ListView lv, Preferencies pref, boolean
            codi){
        this.lv = lv;
        this.context = context;
        this.pref = pref;
        this.codi = codi;
    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }
    @Override
    protected String doInBackground(Integer... params) {
        return Auxiliar.interacioGet(pref.getCodiusuari(), codi);
    }
    @Override
    protected void onProgressUpdate(Integer... values){
    }
    @Override
    protected void onPostExecute(String result) {
        Auxiliar.processarMissatges(lv, context,
                pref.getCodiusuari(),result);
    }
}
