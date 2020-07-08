package com.arman.baking.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arman.baking.R;
import com.arman.baking.databinding.ItemRecipeBinding;
import com.arman.baking.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private Context mContext;

    public RecipeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        ItemRecipeBinding itemBinding = ItemRecipeBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);

        return new RecipeViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.binder(recipe);
    }

    @Override
    public int getItemCount() {
        if (recipeList == null) {
            return 0;
        }
        return recipeList.size();
    }


    public void setRecipeList(List<Recipe> recipeList){
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        ItemRecipeBinding itemBinding;

        public RecipeViewHolder(@NonNull ItemRecipeBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        private void binder(Recipe recipe) {
            if (recipe != null) {
                itemBinding.tvName.setText(recipe.getName());
                if (recipe.getImage() !=null){
                    Picasso.with(mContext)
                            .load(recipe.getImage())
                            .into(itemBinding.imageView);
                }
            }
        }
    }
}
