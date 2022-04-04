package com.camelloncase.assesment.adapter.viewholder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.assesment.databinding.RecipeItemBinding
import com.camelloncase.assesment.model.Recipe

class RecipeViewHolder(private val itemBinding: RecipeItemBinding): RecyclerView.ViewHolder(itemBinding.root) {

    val deleteButton = itemBinding.deleteImageView

    @SuppressLint("SetTextI18n")
    fun bindItem(recipe: Recipe) {

        itemBinding.nameTextView.text = recipe.recipeName
        itemBinding.styleTextView.text = recipe.recipeStyle
        itemBinding.createDateTextView.text = "Create: ${recipe.recipeCreateDate}"

        if(recipe.recipeUpdateDate != null) {
            itemBinding.updateDateTextView.text = "Update: ${recipe.recipeUpdateDate}"
        }
    }
}