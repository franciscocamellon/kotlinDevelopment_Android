package com.camelloncase.testedeperformance03.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.testedeperformance03.databinding.RecipeItemBinding
import com.camelloncase.testedeperformance03.model.Recipe

class RecipesViewHolder(private var binding: RecipeItemBinding): RecyclerView.ViewHolder(binding.root) {

    private val recipeNameText = binding.recipeNameText
    private val recipeStyleText = binding.recipeStyleText
    private val recipeCreateDateText = binding.recipeCreateDateText

    fun bind(recipe: Recipe) {
        binding.apply {
            recipeNameText.text = recipe.recipeName
            recipeStyleText.text = recipe.recipeStyle
            recipeCreateDateText.text = recipe.recipeCreateDate!!.toDate().toString()
        }
    }
}