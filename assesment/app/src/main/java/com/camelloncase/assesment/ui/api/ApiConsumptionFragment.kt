package com.camelloncase.assesment.ui.api

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.assesment.adapter.ApiAdapter
import com.camelloncase.assesment.databinding.FragmentApiConsumptionBinding
import com.camelloncase.assesment.databinding.FragmentInitialBinding
import com.camelloncase.assesment.model.Brewery
import com.camelloncase.assesment.model.Recipe
import com.camelloncase.assesment.repository.BreweryRepository
import com.camelloncase.assesment.ui.recipes.RecipesFragmentDirections
import com.camelloncase.assesment.util.showMessageToUser
import com.camelloncase.assesment.viewmodel.BreweryViewModel
import com.camelloncase.assesment.viewmodel.RecipesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class ApiConsumptionFragment : Fragment() {

    private var _binding: FragmentApiConsumptionBinding? = null
    private val binding get() = _binding!!
    private lateinit var breweryRecyclerView: RecyclerView
    private lateinit var apiAdapter: ApiAdapter
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: BreweryViewModel
    private lateinit var backFloatingActionButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as AppCompatActivity?)!!.supportActionBar?.show()

        _binding = FragmentApiConsumptionBinding.inflate(inflater, container, false)

        setupRecyclerView()

        val application = requireNotNull(this.activity).application
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[BreweryViewModel::class.java]

        backFloatingActionButton = binding.backFloatingActionButton

        backFloatingActionButton.setOnClickListener {
            goToInitialScreen()
        }

        viewModel.getAllNewYorkBreweries()

        viewModel.allBreweriesByCity.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    apiAdapter.setData(it)
                }
            } else {
                showMessageToUser(context, response.code().toString())
            }
        })

        return binding.root
    }

    private fun goToInitialScreen() {
        val action = ApiConsumptionFragmentDirections.actionApiConsumptionFragmentToInitialFragment()
        findNavController().navigate(action)
    }

    private fun setupRecyclerView() {
        apiAdapter = ApiAdapter()
        breweryRecyclerView = binding.apiRecyclerView
        breweryRecyclerView.adapter = apiAdapter
        breweryRecyclerView.layoutManager = LinearLayoutManager(context)
    }

}