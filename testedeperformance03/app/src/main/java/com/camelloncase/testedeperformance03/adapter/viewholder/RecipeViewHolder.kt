package com.camelloncase.testedeperformance03.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.testedeperformance03.databinding.RecipeItemBinding
import com.camelloncase.testedeperformance03.model.Recipe

class RecipeViewHolder(private val itemBinding: RecipeItemBinding): RecyclerView.ViewHolder(itemBinding.root) {

    val deleteButton = itemBinding.deleteImageView

    fun bindItem(recipe: Recipe) {
        itemBinding.nameTextView.text = recipe.recipeName
        itemBinding.styleTextView.text = recipe.recipeStyle
        itemBinding.createDateTextView.text = "Create: ${recipe.recipeCreateDate}"

        if(recipe.recipeUpdateDate != null) {
            itemBinding.updateDateTextView.text = "Update: ${recipe.recipeUpdateDate}"
        }
    }
}