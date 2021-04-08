package com.example.mymusic.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.models.Music;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServerApi {

    private static String URL_API = "http://api.deezer.com/search?q=";

    public static void getMusics(String search, Context context, final IListenerApi listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_API + search,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<Music> musics = new ArrayList<Music>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray tracks = jsonObject.getJSONArray("data");
                            for (int i = 0; i < tracks.length(); i++) {
                                JSONObject track = tracks.getJSONObject(i);
                                Music m = new Music();
                                m.setTitle(track.getString("title"));
                                m.setArtist(track.getJSONObject("artist").getString("name"));
                                m.setAlbum(track.getJSONObject("album").getString("title"));
                                m.setImage(track.getJSONObject("album").getString("cover_medium"));
                                m.setPreview(track.getString("preview"));
                                m.setLink(track.getString("link"));
                                musics.add(m);
                            }
                            listener.onReceiveMusics(musics);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("SERVERAPI", "error lors de la requete a l'api");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("SERVERAPI", "error lors de la requete a l'api");
                    }
                });
        queue.add(stringRequest);
    }

    public static void loadImage(Context context, String url, final ImageView imageView) {
        RequestQueue queue = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.invalidate();
                imageView.setImageBitmap(response);
            }
        }, 0, 0, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(imageRequest);
    }
}
