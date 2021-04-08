package com.example.mymusic.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.mymusic.models.Music;

import java.util.ArrayList;

public class FavoriteRepository implements IFavoriteRepository {

    //attributs de la classe
    private static FavoriteRepository instance;
    private DatabaseManager dbm;

    //Singleton de la BDD
    FavoriteRepository(Context context) {
        dbm = DatabaseManager.getInstance(context);
    }

    public static FavoriteRepository getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteRepository(context);
        }
        return instance;
    }

    //Méthode d'ajout dans la BDD
    @Override
    public boolean add(Music m) {
        if (isFavorite(m)) return false;
        ContentValues values = new ContentValues();
        values.put("title", m.getTitle());
        values.put("artist", m.getArtist());
        values.put("album", m.getAlbum());
        values.put("preview", m.getPreview());
        values.put("image", m.getImage());
        values.put("link", m.getLink());
        //insert dans la BDD
        long line = dbm.getWritableDatabase().insert("favorite", null, values);
        return line != 0;
    }

    //Méthode de suppression dans la BDD
    @Override
    public boolean remove(Music m) {
        String[] identifier = {m.getArtist(), m.getAlbum(), m.getTitle()};
        //delete dans la BDD
        long line = dbm.getWritableDatabase().delete("favorite", "artist=? and album=? and title=?", identifier);
        return line != 0;
    }

    //Méthode d'affichage des favoris dans la BDD
    @Override
    public boolean isFavorite(Music m) {
        String[] identifier = {m.getArtist(), m.getAlbum(), m.getTitle()};
        Cursor c = dbm.getReadableDatabase().rawQuery("select * from favorite where artist=? and album=? and title=?;", identifier);
        return c.getCount() > 0;
    }

    @Override
    public ArrayList<Music> getAll() {
        ArrayList<Music> musics = new ArrayList<>();
        Cursor c = dbm.getReadableDatabase().rawQuery("select * from favorite", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Music m = new Music();
            m.setTitle(c.getString(0));
            m.setArtist(c.getString(1));
            m.setAlbum(c.getString(2));
            m.setPreview(c.getString(3));
            m.setImage(c.getString(4));
            m.setLink(c.getString(5));
            musics.add(m);
            c.moveToNext();
        }
        c.close();
        return musics;
    }
}
