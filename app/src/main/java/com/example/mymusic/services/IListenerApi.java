package com.example.mymusic.services;

import com.example.mymusic.models.Music;

import java.util.ArrayList;

public interface IListenerApi {
    public void onReceiveMusics(ArrayList<Music> musics);
}
