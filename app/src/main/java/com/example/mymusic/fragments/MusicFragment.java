package com.example.mymusic.fragments;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymusic.R;
import com.example.mymusic.models.Music;
import com.example.mymusic.repositories.FavoriteRepository;
import com.example.mymusic.services.ServerApi;

import java.io.IOException;

public class MusicFragment extends Fragment implements View.OnClickListener {

    //attributs de la classe
    ImageView imageView;
    TextView textViewAlbum, textViewArtist, textViewTitle;
    Button buttonDeezer, buttonPlay;
    Music currentMusic = new Music();
    RadioButton radioFavYes, radioFavNo;
    FavoriteRepository repository;
    MediaPlayer player = new MediaPlayer();

    //Méthode de creation de page de détail


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_music, null);
        imageView = (ImageView) v.findViewById(R.id.imageViewDetail);
        textViewAlbum = (TextView) v.findViewById(R.id.TextDetailAlbum);
        textViewArtist = (TextView) v.findViewById(R.id.TextDetailArtist);
        textViewTitle = (TextView) v.findViewById(R.id.TextDetailTitle);
        buttonPlay = (Button) v.findViewById(R.id.ButtonPlay);
        buttonDeezer = (Button) v.findViewById(R.id.ButtonDeezer);
        buttonPlay.setOnClickListener(this);
        buttonDeezer.setOnClickListener(this);

        repository = FavoriteRepository.getInstance(getContext());
        radioFavYes = (RadioButton) v.findViewById(R.id.RadioButtonYes);
        radioFavYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.add(currentMusic);
            }
        });
        radioFavNo = (RadioButton) v.findViewById(R.id.RadioButtonNo);
        radioFavNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.remove(currentMusic);
                radioFavYes.setChecked(false);
                radioFavNo.setChecked(true);
            }
        });
        refresh();
        return v;
    }


    //Méthode d'actualisation
    public void select(Music m) {
        currentMusic = m;
        if (textViewAlbum != null) {
            refresh();
        }
    }

    public void refresh() {
        textViewAlbum.setText(currentMusic.getAlbum());
        textViewArtist.setText(currentMusic.getArtist());
        textViewTitle.setText(currentMusic.getTitle());
        ServerApi.loadImage(getContext(), currentMusic.getImage(), imageView);
        if (repository.isFavorite(currentMusic)) {
            radioFavYes.setChecked(true);
            radioFavNo.setChecked(false);
        } else {
            radioFavYes.setChecked(false);
            radioFavNo.setChecked(true);
        }
    }

    //Méthode de des boutons Link Deezer & Play
    @Override
    public void onClick(View v) {
        if (v.equals(buttonDeezer)) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(currentMusic.getLink()));
            startActivity(intent);
        } else {
            if (!player.isPlaying()) {
                try {
                    player.reset();
                    player.setDataSource(getContext(), Uri.parse(currentMusic.getPreview()));
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                player.stop();
            }
        }
    }
}
