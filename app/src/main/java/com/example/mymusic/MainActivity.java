package com.example.mymusic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mymusic.fragments.IMusicSelected;
import com.example.mymusic.fragments.MusicFragment;
import com.example.mymusic.fragments.SearchFragment;
import com.example.mymusic.models.Music;
import com.example.mymusic.models.MusicAdapter;
import com.example.mymusic.repositories.FavoriteRepository;
import com.example.mymusic.services.IListenerApi;
import com.example.mymusic.services.ServerApi;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements IMusicSelected {

    //Attributs de la classe
    SearchFragment searchFragment;
    MusicFragment musicFragment;

    //Méthode Create de la classe, affiche le main layout
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //appel du layout principal
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.frameLayout) != null) {
            searchFragment = new SearchFragment();
            musicFragment = new MusicFragment();

            getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, searchFragment).commit();
        } else {
            searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentSearch);
            musicFragment = (MusicFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMusic);
            getSupportFragmentManager().beginTransaction().hide(musicFragment).commit();
        }
        searchFragment.setListener(this);
    }

    //Méthode de create du menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Appel du menu "main"
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Méthode pour éviter la fermeture du crash sur le back system
    @Override
    public void onBackPressed() {
        if (musicFragment.isAdded() && findViewById(R.id.frameLayout)!=null && musicFragment.isVisible()){
            getSupportFragmentManager().beginTransaction().hide(musicFragment).show(searchFragment).commit();
        } else{
            super.onBackPressed();
        }
    }

    //Méthode de création du menu contextuel
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //Appel du menu "menufav"
        getMenuInflater().inflate(R.menu.menufav, menu);
    }

    //Méthode pour le bouton de favoris du menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_favorites) {
            searchFragment.displayFav();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    //Méthode de sélection de musique
    @Override
    public void onMusicSelected(Music music) {
        if (findViewById(R.id.frameLayout) !=null) {
            //cache le searchfragment et ajoute le musicfragment si if valide , sinon cache le searchfragment et montre le musicfragment
            if (!musicFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction()
                        .hide(searchFragment)
                        .add(R.id.frameLayout, musicFragment)
                        .commit();
            } else {

                getSupportFragmentManager().beginTransaction()
                        .hide(searchFragment)
                        .show(musicFragment)
                        .commit();
            }
            //Appel de la fonction de sélection
            musicFragment.select(music);
        } else {
            getSupportFragmentManager().beginTransaction().show(musicFragment).commit();
        }
        musicFragment.select(music);
    }
}

