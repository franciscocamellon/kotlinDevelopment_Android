package com.camelloncase.assesment.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.assesment.databinding.ApiItemBinding
import com.camelloncase.assesment.model.Brewery

class ApiViewHolder(private var binding: ApiItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind (brewery: Brewery) {
        binding.nameTextView.text = brewery.name
        binding.streetTextView.text = brewery.street
        binding.cityTextView.text = brewery.city
        binding.stateTextView.text = brewery.state
    }
}