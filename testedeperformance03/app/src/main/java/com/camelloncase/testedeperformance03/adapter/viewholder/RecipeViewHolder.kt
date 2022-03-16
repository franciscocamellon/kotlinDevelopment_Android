package com.camelloncase.testedeperformance03.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.testedeperformance03.databinding.RecipeItemBinding
import com.camelloncase.testedeperformance03.model.Recipe

class RecipeViewHolder(private val itemBinding: RecipeItemBinding): RecyclerView.ViewHolder(itemBinding.root) {

    private val button = itemBinding.deleteRecipeImageView

    fun bindItem(recipe: Recipe) {
        itemBinding.recipeNameText.text = recipe.recipeName
        itemBinding.recipeStyleText.text = recipe.recipeStyle
        itemBinding.recipeCreateDateText.text = recipe.recipeCreateDate

    }

}