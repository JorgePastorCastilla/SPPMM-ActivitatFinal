package com.example.activitatfinalandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();//ESTO PUEDE QUE PETE
    ListView lv;
    EditText editText;
    Intent intent;
    Runnable getResponceAfterInterval;
    private ReceptorXarxa receptor;
    private static final int LOGIN = 007;
    Preferencies pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new Preferencies(this);
        dbQuePasaAux db = new dbQuePasaAux(this);
        IntentFilter filter = new
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receptor = new ReceptorXarxa();
        this.registerReceiver(receptor, filter);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Log.w("preLogIn", pref.getUser() + " " + pref.getPassword());
        try {
            JSONObject conexion = new
                    JSONObject(Auxiliar.interacionPost(pref.getUser(), pref.getPassword(),
                    true));
            if (conexion.getBoolean("correcta")) {
                pref.setToken(conexion.getString("token"));
            } else {
                Intent intent = new Intent(this, LogIn.class);
                startActivityForResult(intent, LOGIN);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.list);
        editText = findViewById(R.id.msg);
        Runnable getResponceAfterInterval = new Runnable() {
            public void run() {
                try {
                    new Recepcio(MainActivity.this, lv, pref,
                            dbQuePasaAux.isEmpty()).execute();
                } catch (Exception e) {
                }
                handler.postDelayed(this, 1000 * 60);
            }
        };
    }




    public void enviar(View view) {
        if (!editText.getText().toString().equals("")) {
            new Enviament(pref).execute(editText.getText().toString());
            editText.setText("");
            new Recepcio(this, lv, pref, dbQuePasaAux.isEmpty()).execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    JSONObject user = new
                            JSONObject(data.getStringExtra("user"));
                    pref.setCodiusuari(user.getString("codiusuari"));
                    pref.setUser(user.getString("nom"));
                    pref.setToken(user.getString("token"));
                    pref.setPassword(data.getStringExtra("pass"));
                } catch (JSONException e) {
                    Log.d("JSON_ERROR", e.getMessage());
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                intent = new Intent(this, LogIn.class);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.removeCallbacks(getResponceAfterInterval);
        handler.post(getResponceAfterInterval);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Donam de baixa el receptor de broadcast quan es destrueix l’aplicació
        if (receptor != null) {
            this.unregisterReceiver(receptor);
        }

    }
    public void comprovaConnectivitat(Context context) {
        //Obtenim un gestor de les connexions de xarxa
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE); //Obtenim l’estat de la xarxa
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //Si està connectat
        if (networkInfo != null && networkInfo.isConnected()) {
            //Xarxa OK
            Toast.makeText(this, "Xarxa ok", Toast.LENGTH_LONG).show();
        } else {
            //Xarxa no disponible
            Toast.makeText(this, "Xarxa no disponible",
                    Toast.LENGTH_LONG).show();
        }

    }

    public class ReceptorXarxa extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Actualitza l'estat de la xarxa
            comprovaConnectivitat(context);
        }
    }

}



