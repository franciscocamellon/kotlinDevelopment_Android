package com.camelloncase.testedeperformance01.ui.recipes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.testedeperformance01.R
import com.camelloncase.testedeperformance01.model.Recipe

class RecipesRecyclerViewAdapter(
    private val context: Context,
    private val recipeClickDeleteInterface: RecipeClickDeleteInterface,
    private val recipeClickInterface: RecipeClickInterface
) : RecyclerView.Adapter<RecipesRecyclerViewAdapter.ViewHolder>() {

    private val allRecipes = ArrayList<Recipe>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipeName: TextView = itemView.findViewById(R.id.recipeNameText)
        val recipeDate: TextView = itemView.findViewById(R.id.recipeCreateDateText)
        val recipeStyle: TextView = itemView.findViewById(R.id.recipeStyleText)
        val deleteRecipe: ImageView = itemView.findViewById(R.id.deleteRecipeImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(context).inflate(
            R.layout.recipe_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.recipeName.text = allRecipes[position].recipeName
        holder.recipeStyle.text = allRecipes[position].recipeStyle

        val formattedRecipeDate = "Created: ${allRecipes[position].recipeCreateDate}"
        holder.recipeDate.text = formattedRecipeDate

        holder.deleteRecipe.setOnClickListener {
            recipeClickDeleteInterface.onDeleteIconClick(allRecipes[position])
        }

        holder.itemView.setOnClickListener {
            recipeClickInterface.onRecipeClick(allRecipes[position])
        }
    }

    override fun getItemCount(): Int {
        return allRecipes.size
    }

    fun updateList(newList: List<Recipe>) {
        allRecipes.clear()
        allRecipes.addAll(newList)
        notifyDataSetChanged()
    }
}

interface RecipeClickDeleteInterface {
    fun onDeleteIconClick(recipe: Recipe)
}

interface RecipeClickInterface {
    fun onRecipeClick(recipe: Recipe)
}
