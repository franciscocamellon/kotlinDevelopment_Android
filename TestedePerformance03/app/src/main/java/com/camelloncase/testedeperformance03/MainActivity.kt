package com.camelloncase.testedeperformance03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.testedeperformance03.database.Recipe
import com.camelloncase.testedeperformance03.databinding.ActivityMainBinding
import com.camelloncase.testedeperformance03.ui.management.RecipeManagementActivity
import com.camelloncase.testedeperformance03.ui.recipes.NoteClickDeleteInterface
import com.camelloncase.testedeperformance03.ui.recipes.NoteClickInterface
import com.camelloncase.testedeperformance03.ui.recipes.RecipeViewModel
import com.camelloncase.testedeperformance03.ui.recipes.RecipesRecyclerViewAdapter
import com.camelloncase.testedeperformance03.util.showMessageToUser
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: RecipeViewModel
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var addFloatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipesRecyclerView = findViewById(R.id.recipesRecyclerView)
        recipesRecyclerView = binding.recipesRecyclerView
        addFloatingActionButton = findViewById(R.id.addFloatingActionButton)

        recipesRecyclerView.layoutManager = LinearLayoutManager(this)

        val recipesRecyclerViewAdapter = RecipesRecyclerViewAdapter(this, this, this)

        recipesRecyclerView.adapter = recipesRecyclerViewAdapter

        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[RecipeViewModel::class.java]

        viewModel.allNotes.observe(this) { list ->
            list?.let {
                recipesRecyclerViewAdapter.updateList(it)
            }
        }
        addFloatingActionButton.setOnClickListener {
            goToRecipeManagementActivity()
        }
    }

    override fun onNoteClick(recipe: Recipe) {

        val intent = Intent(this@MainActivity, RecipeManagementActivity::class.java)

        intent.putExtra("actionType", "Edit")
        intent.putExtra("recipeName", recipe.recipeName)
        intent.putExtra("recipeStyle", recipe.recipeStyle)
        intent.putExtra("recipeId", recipe.recipeId)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(recipe: Recipe) {

        val message = "${recipe.recipeName} successful deleted!"

        viewModel.deleteRecipe(recipe)
        showMessageToUser(this, message)
    }

    private fun goToRecipeManagementActivity() {
        val intent = Intent(this@MainActivity, RecipeManagementActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}
