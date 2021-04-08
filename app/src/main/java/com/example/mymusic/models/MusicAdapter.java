package com.example.mymusic.models;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymusic.R;
import com.example.mymusic.repositories.FavoriteRepository;
import com.example.mymusic.services.ServerApi;

import java.util.ArrayList;

public class MusicAdapter extends BaseAdapter {

    //Attribut de la classe
    private Context context;
    private ArrayList<Music> musics;
    private FavoriteRepository repository;

    //Getter & Setter
    public void setMusic(ArrayList<Music> music) {
        this.musics = music;
    }

    public MusicAdapter(Context context) {
        this.context = context;
        this.musics = new ArrayList<Music>();
        this.repository = FavoriteRepository.getInstance(context);
    }

    @Override
    public int getCount() {
        return this.musics.size();
    }

    @Override
    public Object getItem(int position) {
        return this.musics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_music, null);
        TextView textArtist = (TextView) rowView.findViewById(R.id.textViewArtistItem);
        textArtist.setText(musics.get(position).getArtist());
        TextView textTitle = (TextView) rowView.findViewById(R.id.textViewTitleItem);
        textTitle.setText(musics.get(position).getTitle());
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewItem);
        ServerApi.loadImage(context, musics.get(position).getImage(), imageView);

        ImageView imageviewFav = rowView.findViewById(R.id.imageViewFav);
        if (repository.isFavorite(musics.get(position))) {
            imageviewFav.setImageResource(android.R.drawable.star_on);
        } else {
            imageviewFav.setImageResource(android.R.drawable.star_off);
        }
        return rowView;
    }
}
