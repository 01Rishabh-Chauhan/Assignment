package com.example.wiredviewer;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private ArrayList<NotesThumbnailItem> notesThumbnailItems;
    private Context ncontext;


    public NotesAdapter(ArrayList<NotesThumbnailItem> notesThumbnailItems, Context context) {
        this.notesThumbnailItems = notesThumbnailItems;
        this.ncontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_thumbnail_item, parent, false);

        return new com.example.wiredviewer.NotesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotesThumbnailItem notesThumbnailItem = notesThumbnailItems.get(position);
        holder.textView.setText(notesThumbnailItem.getNote());
    }

    @Override
    public int getItemCount() {
        return notesThumbnailItems.size();
    }

    public void onDeleteNote() {
        SharedPreferences sharedPreferences = ncontext.getSharedPreferences("shared", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notesThumbnailItems);
        editor.putString("task_list", json);
        editor.commit();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.note_txt_view);
            delete = itemView.findViewById(R.id.dustBin);
            delete.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (v.equals(delete)) {
                notesThumbnailItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, notesThumbnailItems.size());
                //deleting it from the shared preferences
                onDeleteNote();
            } else {
               NotesThumbnailItem notesThumbnailItem=notesThumbnailItems.get(position);

                ClipboardManager myClipboard = (ClipboardManager) ncontext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip = ClipData.newPlainText("text", notesThumbnailItem.getNote());
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(v.getContext(), "Note copied to clipboard!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

