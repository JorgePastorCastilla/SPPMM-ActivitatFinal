package com.example.activitatfinalandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class dbQuePasaAux {
    private static dbQuePasa db;
    public dbQuePasaAux(Context context) {
        db = new dbQuePasa(context);
    }
    public static void guardarRecibido(JSONObject jObject) {
        try {
            SQLiteDatabase query = db.getWritableDatabase();
            ContentValues contenido = new ContentValues();
            if (!codiUsuariExist(jObject.getString("codiusuari"))){
                contenido.put(dbQuePasa.COL_USER_ID,
                        jObject.getString("codiusuari"));
                contenido.put(dbQuePasa.COL_USER_NAME,
                        jObject.getString("nom"));
                query.insert(dbQuePasa.TAB_USER, null, contenido);
            }
            contenido = new ContentValues();
            query = db.getWritableDatabase();
            contenido.put(dbQuePasa.COL_MSG_ID,
                    jObject.getString("codimissatge"));
            contenido.put(dbQuePasa.COL_MSG_MSG, jObject.getString("msg"));
            contenido.put(dbQuePasa.COL_MSG_DATE,
                    jObject.getString("datahora"));
            contenido.put(dbQuePasa.COL_MSG_ID_USER,
                    jObject.getString("codiusuari"));
            query.insert(dbQuePasa.TAB_MSG, null, contenido);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static boolean isEmpty(){
        boolean isEmpty = true;
        SQLiteDatabase query = db.getWritableDatabase();
        String select = "SELECT * FROM " + dbQuePasa.TAB_USER;
        Cursor cursor = query.rawQuery(select, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            isEmpty = false;
        }
        return isEmpty;
    }
    public static boolean codiUsuariExist(String codiUsuari) {
        SQLiteDatabase query = db.getWritableDatabase();
        String select = "SELECT * FROM " + dbQuePasa.TAB_USER + " WHERE " +
                dbQuePasa.COL_USER_ID + " = " + codiUsuari;
        boolean igual = false;
        Cursor cursor = query.rawQuery(select, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            igual = true;
        }
        cursor.close();
        return igual;
    }
    public static ArrayList<Missatge> listarMensajes(int idUltimoMsg){
        ArrayList<Missatge> missatges = new ArrayList<>();
        SQLiteDatabase query = db.getWritableDatabase();
        String select = "SELECT * FROM " + dbQuePasa.TAB_MSG + " WHERE " +
                dbQuePasa.COL_MSG_ID + " > " + idUltimoMsg;// + " order by "+dbQuePasa.COL_MSG_DATE+" DESC";
        Cursor cursor = query.rawQuery(select, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Missatge missatge = curToMensaje(cursor);
            missatges.add(missatge);
            cursor.moveToNext();
        }
        cursor.close();
        return missatges;
    }
    public static Missatge curToMensaje(Cursor cursor){
        Missatge missatge = new Missatge();
        missatge.setId(cursor.getString(0));
        missatge.setMsg(cursor.getString(1));
        missatge.setDate(cursor.getString(2));
        missatge.setUserId(cursor.getString(3));
        missatge.setUserName(getUserName(missatge.getUserId()));
        return missatge;
    }
    public static String getUserName(String userId){
        SQLiteDatabase query = db.getWritableDatabase();
        String select = "SELECT " + dbQuePasa.COL_USER_NAME + " FROM " +
                dbQuePasa.TAB_USER + " WHERE " + dbQuePasa.COL_USER_ID + " = " + userId;
        Cursor cursor = query.rawQuery(select, null);
        String nombre = "";
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            nombre = cursor.getString(0);
        }
        return nombre;
    }
}