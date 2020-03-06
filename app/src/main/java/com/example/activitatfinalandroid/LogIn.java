package com.example.activitatfinalandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LogIn extends AppCompatActivity {

  /*  Button boton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        boton = findViewById(R.id.btnEnviarLogin);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login(){
        EditText username;
        EditText password;
        username = findViewById( R.id.etEmailLogin );
        password = findViewById( R.id.etContraLogin );
        String user = username.getText().toString();
        String pass = password.getText().toString();

        String cosasNazis = Auxiliar.interacionPost(user,pass,true);

        Intent intent = new Intent();
        intent.putExtra("user",cosasNazis);
        intent.putExtra("pass",pass);

        setResult(700, intent);
        super.finish();
    }*/

    private EditText email;
    private EditText pass;
    private Button btnLogin;

    private String emailEnvia;
    private String passEnvia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Obtenemos los datos
        email = findViewById(R.id.etEmailLogin);
        pass = findViewById(R.id.etContraLogin);
        btnLogin = findViewById(R.id.btnEnviarLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Comprobamos que los datos sean correctos
                if (vacio(email.getText().toString(), pass.getText().toString())) {
                    emailEnvia = email.getText().toString();
                    passEnvia = pass.getText().toString();
                    //Toast.makeText(getApplicationContext(), "Login Valido", Toast.LENGTH_LONG).show();
                    //guardarShared();

                    String result = Auxiliar.interacionPost(emailEnvia.toUpperCase(), passEnvia.toUpperCase(), true);

                    if(!result.trim().isEmpty())
                    {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(result);
                            if (json.getBoolean("correcta"))
                            {
                                Intent intent = new Intent();
                                intent.putExtra("user", json.getString("dades"));
                                intent.putExtra("pass", pass.getText().toString().toUpperCase());
                                setResult(RESULT_OK, intent);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), "Login incorrecto", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    public boolean vacio(String email, String pass) {
        if (!("").equals(email.trim()) || !("").equals(pass.trim())) {
            return true;
        } else {
            // ERROR
            Log.d("ERROR LOGIN", "Campos email o password incorrectos.");
        }
        return false;
    }
/*
    public void guardarShared() {
        SharedPreferences sharedPreferences = getSharedPreferences("datos_login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(usuario);
        editor.putString("usuario", json);
        editor.apply();
        finish();
    }*/

}
