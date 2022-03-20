package com.camelloncase.testedeperformance03.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.testedeperformance03.adapter.viewholder.RecipeViewHolder
import com.camelloncase.testedeperformance03.databinding.RecipeItemBinding
import com.camelloncase.testedeperformance03.model.Recipe

class RecipeAdapter(
    var recipeList: List<Recipe>,
    var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecipeViewHolder>() {

    interface OnItemClickListener {
        fun onClick(recipe: Recipe, position: Int)
        fun onDelete(recipe: Recipe, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemBinding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]

        holder.bindItem(recipe)

        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(recipe, position)
        }

        holder.deleteButton.setOnClickListener {
            onItemClickListener.onDelete(recipe, position)
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }
}