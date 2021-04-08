package com.example.mymusic.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    //attribut de la classe de bdd
    private static final String DATABASE_NAME = "mydb.sqlite";
    private static final int CURRENT_DB_VERSION = 1;

    private static DatabaseManager instance;

    //Singleton de la BDD
    public static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

    private DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, CURRENT_DB_VERSION);
    }

    //Requête SQL de création de table favorite
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table favorite " + "(title TEXT, artist TEXT, album TEXT, " + "preview TEXT, image TEXT, link TEXT);");
    }

    //Méthode de MAJ de BDD
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
            case 2:
            case 3:
            default:
        }
    }
}
