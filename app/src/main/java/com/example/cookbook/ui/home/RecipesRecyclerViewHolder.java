package com.example.cookbook.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.R;
import com.example.cookbook.ui.profile.RecyclerViewHolder;

import java.util.ArrayList;

public class RecipesRecyclerViewHolder extends RecyclerView.Adapter<RecipesRecyclerViewHolder.RecipesItem>{
    private String[] names;
    private Context context;
    private Integer[] images;

    public RecipesRecyclerViewHolder(String[] names, Integer[] images, Context context){
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
     holder.name.setText(names[position]);
     holder.link.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
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
