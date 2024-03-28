package com.example.wiredviewer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private static ArrayList<ThumbnailItem> thumbnailItems;
    private static Context context;

    public Adapter(ArrayList<ThumbnailItem> thumbnailItems, Context context) {
        Adapter.thumbnailItems = thumbnailItems;
        Adapter.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thumbnail_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        ThumbnailItem thumbnailItem = thumbnailItems.get(position);
        holder.textView.setText(thumbnailItem.getTitle());
        if (!thumbnailItem.getImgUrl().isEmpty()) {
            Picasso.get().load(thumbnailItem.getImgUrl()).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return thumbnailItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            ThumbnailItem thumbnailItem = thumbnailItems.get(position);
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("title", thumbnailItem.getTitle());
            intent.putExtra("image", thumbnailItem.getImgUrl());
            intent.putExtra("detailsUrl", thumbnailItem.getDetailsUrl());
            context.startActivity(intent);
        }
    }
}
