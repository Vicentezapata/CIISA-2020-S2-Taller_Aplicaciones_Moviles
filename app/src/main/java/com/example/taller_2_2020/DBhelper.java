package com.example.taller_2_2020;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    String tbl_user = "CREATE TABLE tbl_user (id_user integer PRIMARY KEY AUTOINCREMENT, rut varchar,nombre varchar,apellido varchar,genero varchar,edad integer, fecha_nac date, contrasena varchar)";
    String tbl_datos = "CREATE TABLE tbl_datos (id_dato integer PRIMARY KEY AUTOINCREMENT, nombre_coctel varchar, base1 varchar, base2 varchar, jugo1 varchar, jugo2 varchar)";

    //METODO CONSTRUCTUR
    public DBhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //METODO PARA CREAR LAS TABLAS
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tbl_user);
        db.execSQL(tbl_datos);

    }
    //METODO PARA ACTUALIZAR LA BD
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
