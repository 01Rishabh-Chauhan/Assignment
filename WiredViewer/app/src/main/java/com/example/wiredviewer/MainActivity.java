package com.example.wiredviewer;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView home, notes, addNotes;
    EditText notesText;
    private RecyclerView recyclerView;
    private RecyclerView notesRecyclerView;
    private Adapter adapter;
    private NotesAdapter nAdapter;
    private ArrayList<ThumbnailItem> thumbnailItems = new ArrayList<>();
    private ProgressBar progressBar;
    private ArrayList<NotesThumbnailItem> notesThumbnailItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        notesRecyclerView = findViewById(R.id.notes_recyclerView);
        addNotes = findViewById(R.id.noted_add_btn);
        notesText = findViewById(R.id.notes_txt);
        home = findViewById(R.id.home_img);
        notes = findViewById(R.id.note_img);
        notesRecyclerView.setHasFixedSize(true);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        nAdapter = new NotesAdapter(notesThumbnailItems, this);
        notesRecyclerView.setAdapter(nAdapter);
        
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(thumbnailItems, this);
        recyclerView.setAdapter(adapter);
        Content content = new Content();
        content.execute();
    }

    public void HomeBtn(View view) {
        home.setImageResource(R.drawable.ic_home_btn);
        notes.setImageResource(R.drawable.ic_notes_btn_off);
        recyclerView.setVisibility(View.VISIBLE);
        notesText.setVisibility(View.GONE);
        addNotes.setVisibility(View.GONE);
        notesRecyclerView.setVisibility(View.GONE);
    }

    public void NoteBtn(View view) {
        recyclerView.setVisibility(View.GONE);

        home.setImageResource(R.drawable.ic_home_btn_off);
        notes.setImageResource(R.drawable.ic_notes_btn);
        notesText.setVisibility(View.VISIBLE);
        addNotes.setVisibility(View.VISIBLE);
        notesRecyclerView.setVisibility(View.VISIBLE);
    }

    public void AddNotesBtn(View view) {
        String notes = notesText.getText().toString();
        if(notes.isEmpty()){
            notesText.setError("This field cannot be empty!");
            return;
        }

        notesThumbnailItems.add(new NotesThumbnailItem(notes));
        saveData();
        notesText.setEnabled(false);
        notesText.setText(null);
        notesText.setEnabled(true);
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notesThumbnailItems);
        editor.putString("task_list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task_list",null);
        Type type = new TypeToken<ArrayList<NotesThumbnailItem>>() {}.getType();

        notesThumbnailItems = gson.fromJson(json,type);
        if(notesThumbnailItems == null) {
            notesThumbnailItems = new ArrayList<>();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class Content extends AsyncTask<Void, Void, Void> {
        Content() { }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));

            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String url = "https://www.wired.com/";
                Document doc = Jsoup.connect(url).get();

                // Selecting meta tags containing image URLs
                Elements metaTags = doc.select("meta[property^=og:image]");

                // Selecting meta tags containing titles
                Elements titleTags = doc.select("meta[property^=og:title]");

                for (int i = 0; i < metaTags.size(); i++) {
                    String imageUrl = metaTags.get(i).attr("content");
                    String title = titleTags.get(i).attr("content");
                    thumbnailItems.add(new ThumbnailItem(imageUrl, title, "Details")); // You can modify 'Details' accordingly
                    Log.d("items", "img:" + imageUrl + ".title:" + title);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
