package com.example.mymusic.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymusic.R;
import com.example.mymusic.models.Music;
import com.example.mymusic.models.MusicAdapter;
import com.example.mymusic.repositories.FavoriteRepository;
import com.example.mymusic.services.IListenerApi;
import com.example.mymusic.services.ServerApi;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements View.OnClickListener {
    //attributs de la classe
    ListView listView;
    MusicAdapter adapter;
    EditText EditTextItem;
    Button ButtonSearchItem;
    FavoriteRepository repository;
    IMusicSelected listener;

    public void setListener(IMusicSelected listener) {
        this.listener = listener;
    }

    //Méthode create de la liste de recherche
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, null);
        listView = (ListView) v.findViewById(R.id.listViewSearch);
        adapter = new MusicAdapter(getContext());
        listView.setAdapter(adapter);
        displayFav();

        EditTextItem = (EditText) v.findViewById(R.id.EditTextItem);
        ButtonSearchItem = (Button) v.findViewById(R.id.ButtonSearchItem);
        ButtonSearchItem.setOnClickListener(this);

        //Enregistre le menu contextuel pour la listview
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Music music = (Music) parent.getItemAtPosition(position);
                listener.onMusicSelected(music);
            }
        });
        return v;
    }

    //Actualise
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    //Méthode du bouton de recherche
    @Override
    public void onClick(View v) {
        String search = EditTextItem.getText().toString();
        ServerApi.getMusics(search, getContext(), new IListenerApi() {
            @Override
            public void onReceiveMusics(ArrayList<Music> musics) {
                adapter.setMusic(musics);
                adapter.notifyDataSetChanged();
            }
        });
    }

    //Méthode de sélection d'item du menu contextuel
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContext = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Music music = (Music) adapter.getItem(adapterContext.position);
        switch (item.getItemId()) {
            //ajoute aux favoris
            case R.id.bookmarked:
                FavoriteRepository.getInstance(getContext()).add(music);
                adapter.notifyDataSetChanged();
                return true;
            //retire des favoris
            case R.id.unbookmarked:
                FavoriteRepository.getInstance(getContext()).remove(music);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //Méthode pour afficher les favoris
    public void displayFav() {
        ArrayList<Music> musics = FavoriteRepository.getInstance(getContext()).getAll();
        adapter.setMusic(musics);
        adapter.notifyDataSetChanged();
    }
}


