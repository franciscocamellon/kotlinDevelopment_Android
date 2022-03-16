package com.camelloncase.testedeperformance03.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.testedeperformance03.R
import com.camelloncase.testedeperformance03.adapter.viewholder.RecipesViewHolder
import com.camelloncase.testedeperformance03.databinding.RecipeItemBinding
import com.camelloncase.testedeperformance03.model.Recipe

class RecipesRecyclerViewAdapter(
    var list: List<Recipe>,
    var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecipesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val inflater = RecipeItemBinding.inflate(LayoutInflater.from(parent.context))
        return RecipesViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(item, position)
        }
        holder.itemView.findViewById<Button>(R.id.deleteRecipeImageView).setOnClickListener {
            onItemClickListener.onDelete(item, position)
        }
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onClick(item: Recipe, position: Int)
        fun onDelete(item: Recipe, position: Int)
    }
}
