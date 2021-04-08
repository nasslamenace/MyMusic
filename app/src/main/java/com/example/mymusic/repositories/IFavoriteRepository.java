package com.example.mymusic.repositories;

import com.example.mymusic.models.Music;

import java.util.ArrayList;

//Controller des méthodes de modification BDD
public interface IFavoriteRepository {

    public boolean add(Music m);

    public boolean remove(Music m);

    public boolean isFavorite(Music m);

    public ArrayList<Music> getAll();
}
