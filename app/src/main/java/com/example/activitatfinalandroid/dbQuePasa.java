package com.example.activitatfinalandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbQuePasa extends SQLiteOpenHelper {
    public static final int VERSION_DB = 3;
    public static final String NOMBRE_DB = "quepasa.db";
    public static final String TAB_USER = "usuario";
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_NAME = "name";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_IMG = "img";
    private final String CREATE_TABLE_USER = "create table " + TAB_USER +
            "("
            + COL_USER_ID + " integer primary key, "
            + COL_USER_NAME + " text not null, "
            + COL_USER_EMAIL + " text, "
            + COL_USER_IMG + " text)";
    public static final String TAB_MSG = "msg";
    public static final String COL_MSG_ID = "id";
    public static final String COL_MSG_MSG = "msg";
    public static final String COL_MSG_DATE = "date";
    public static final String COL_MSG_ID_USER = "userId";
    public static final String COL_MSG_LAST_MSG = "lstMsg";
    private final String CREATE_TABLE_MSG = "create table " + TAB_MSG + "("
            + COL_MSG_ID + " integer primary key, "
            + COL_MSG_MSG + " text not null, "
            + COL_MSG_DATE + " text not null, "
            + COL_MSG_ID_USER + " integer not null, "
            + COL_MSG_LAST_MSG + " integer default 0 not null, "
            + "FOREIGN KEY ("+ COL_MSG_ID_USER +") REFERENCES " + TAB_USER +
            "(" + COL_USER_ID + "))";
    public dbQuePasa(Context context){
        super(context, NOMBRE_DB, null, VERSION_DB);
    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_USER);
        Log.i("QuePasa",CREATE_TABLE_MSG);
        database.execSQL(CREATE_TABLE_MSG);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(dbQuePasa.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TAB_MSG);
        db.execSQL("DROP TABLE IF EXISTS " + TAB_USER);
        onCreate(db);
    }
}