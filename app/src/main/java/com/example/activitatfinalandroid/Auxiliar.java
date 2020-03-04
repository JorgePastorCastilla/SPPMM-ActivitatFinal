package com.example.activitatfinalandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

public class Auxiliar {
    public static String interacionPost(String arg1, String arg2, boolean
            login){
        URL url;
        StringBuilder text = new StringBuilder();
        try {
            // Agafam la URL que s'ha passat com argument
            if(login){
                url = new
                        URL("http://52.44.95.114/quepassaeh/server/public/login/");
            } else{
                url = new
                        URL("http://52.44.95.114/quepassaeh/server/public/provamissatge/");
            }
            // Feim la connexió a la URL
            HttpURLConnection httpURLConnection = (HttpURLConnection)
                    url.openConnection();
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setChunkedStreamingMode(25000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream out = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new
                    OutputStreamWriter(out, Charset.forName("UTF-8")));
            if(login){
                writer.write("email=" + arg1 + "&password=" + arg2);
            }else{
                writer.write("msg=" + arg1 + "&codiusuari=" +
                        Integer.parseInt(arg2));
            }
            writer.flush();
            writer.close();
            out.close();
            // Codi de la resposta
            int responseCode = httpURLConnection.getResponseCode();
            Log.d("RUN", "Descarrega "+responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Recollim texte
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(httpURLConnection.getInputStream()));
                String liniatxt;
                while ((liniatxt = in.readLine()) != null) {
                    text.append(liniatxt);
                }
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }
    public static String interacioGet(String idUser, boolean codi) {
        StringBuilder text = new StringBuilder();
        URL url;
        try {
            // Agafam la URL que s'ha passat com argument
            if (!codi) {
                url = new
                        URL("http://52.44.95.114/quepassaeh/server/public/provamissatge/");
            } else {
                url = new
                        URL("http://52.44.95.114/quepassaeh/server/public/provamissatge/" + idUser);
            }
            // Feim la connexió a la URL
            HttpURLConnection httpURLConnection = (HttpURLConnection)
                    url.openConnection();
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setChunkedStreamingMode(25000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            // Codi de la resposta
            int responseCode = httpURLConnection.getResponseCode();
            Log.d("RUN", "Descarrega " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Recollim texte
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(httpURLConnection.getInputStream()));
                String liniatxt;
                while ((liniatxt = in.readLine()) != null) {
                    text.append(liniatxt);
                }
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }
    public static void processarMissatges(ListView lv, Context context,
                                          String idUser, String result){
        // Quan ha acabat la tasca, Agafam string que es un JSON
        Log.w("TEXTE", result);
        try {
            JSONObject json = new JSONObject(result);
            JSONArray jArray = json.getJSONArray("dades");
            // Llista de descarregues
            ArrayList<HashMap<String, String>> llista = new ArrayList<>();
            // Guarda a la llista
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                dbQuePasaAux.guardarRecibido(jObject);
            }
            mostraMissatges(lv, context, 0, idUser);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
    public static void mostraMissatges(ListView lv, Context context, int
            ultimoMensaje, String idUser){
        ArrayList<Missatge> mensajes =
                dbQuePasaAux.listarMensajes(ultimoMensaje);
        AdapterQuePasa adapter = new AdapterQuePasa(context,
                R.layout.mensaje_izquierda, mensajes, idUser);
        lv.setAdapter(adapter);
    }
}
