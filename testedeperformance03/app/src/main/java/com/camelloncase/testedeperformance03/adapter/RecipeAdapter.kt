package com.camelloncase.testedeperformance03.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.testedeperformance03.R
import com.camelloncase.testedeperformance03.adapter.viewholder.RecipeViewHolder
import com.camelloncase.testedeperformance03.databinding.RecipeItemBinding
import com.camelloncase.testedeperformance03.model.Recipe

class RecipeAdapter(var list: List<Recipe>,
                    var onItemClickListener: OnItemClickListener
                    ): RecyclerView.Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemBinding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = list[position]
        holder.bindItem(recipe)
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(recipe, position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onClick(item: Recipe, position: Int)
        fun onDelete(item: Recipe, position: Int)
    }
}