package com.example.cookbook.ui.home;

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
import com.example.cookbook.ui.profile.RecyclerViewHolder;

import java.util.ArrayList;

public class RecipesRecyclerViewHolder extends RecyclerView.Adapter<RecipesRecyclerViewHolder.RecipesItem>{
    private ArrayList<String> names;
    private Context context;
    private ArrayList<String> images;

    public RecipesRecyclerViewHolder(ArrayList<String> names, ArrayList<String> images, Context context){
        this.names = names;
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipesItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipesRecyclerViewHolder.RecipesItem(LayoutInflater.from(context).inflate(R.layout.list_item_single,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesItem holder, int position) {
     holder.name.setText(names.get(position));
     Glide.with(context)
             .load(images.get(position))
             .into(holder.link);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class RecipesItem extends RecyclerView.ViewHolder{
        private TextView name;
        private ImageView link;

        public RecipesItem(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recipeName);
            link = itemView.findViewById(R.id.recipeImage);
        }

        public TextView getName(){
            return name;
        }

        public ImageView getImage(){
            return link;
        }
    }
}
