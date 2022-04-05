package com.camelloncase.assesment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.assesment.adapter.viewholder.ApiViewHolder
import com.camelloncase.assesment.databinding.ApiItemBinding
import com.camelloncase.assesment.model.Brewery

class ApiAdapter(): RecyclerView.Adapter<ApiViewHolder>() {

    private var breweryList = emptyList<Brewery>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiViewHolder {
        val itemBinding = ApiItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApiViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ApiViewHolder, position: Int) {
        val brewery = breweryList[position]
        holder.bind(brewery)
    }

    override fun getItemCount(): Int {
        return breweryList.size
    }

    fun setData(newList: List<Brewery>) {
        breweryList = newList
        notifyDataSetChanged()
    }
}