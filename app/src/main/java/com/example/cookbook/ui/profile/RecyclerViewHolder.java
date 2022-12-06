package com.example.cookbook.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookbook.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewHolder extends RecyclerView.Adapter<RecyclerViewHolder.Item> {

    private ArrayList<String> names;
    private Context context;
    private ArrayList<String> images;

    public RecyclerViewHolder(ArrayList<String> names, ArrayList<String> images, Context context){
        this.names = names;
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public Item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Item(LayoutInflater.from(context).inflate(R.layout.cookbook_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Item holder, int position) {
        holder.name.setText(names.get(position));
        Glide.with(context)
                .load(R.drawable.logo)
                .into(holder.link);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class Item extends RecyclerView.ViewHolder{
        private TextView name;
        private ImageView link;

        public Item(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cookbookName);
            link = itemView.findViewById(R.id.cookbookImage);
        }

        public TextView getName(){
            return name;
        }

        public ImageView getImage(){
            return link;
        }
    }

}
