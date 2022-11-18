package com.example.cookbook;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

//The adapter class for the RecyclerView, contains the recipe data.
class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    // Member variables.
    private ArrayList<Recipe> mRecipeData;
    private Context mContext;

    /**
     * Constructor that passes in the sports data and the context.
     *
     * @param recipeData ArrayList containing the sports data.
     * @param context Context of the application.
     */
    RecipeAdapter(Context context, ArrayList<Recipe> recipeData) {
        this.mRecipeData = recipeData;
        this.mContext = context;
    }

    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder,
                                 int position) {
        // Get current sport.
        Recipe currentRecipe = mRecipeData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentRecipe);
    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return mRecipeData.size();
    }


    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mRecipeImage;


        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mRecipeImage = itemView.findViewById(R.id.recipeImage);


            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(Recipe currentRecipe){
            // Populate the textviews with data.
            mTitleText.setText(currentRecipe.getTitle());
            mInfoText.setText(currentRecipe.getInfo());
            Glide.with(mContext).load(currentRecipe.getImageResource()).into(mRecipeImage);
        }

        @Override
        public void onClick(View view) {
            Recipe currentRecipe = mRecipeData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("title", currentRecipe.getTitle());
            detailIntent.putExtra("image_resource",
                    currentRecipe.getImageResource());
            mContext.startActivity(detailIntent);
        }
    }
}


