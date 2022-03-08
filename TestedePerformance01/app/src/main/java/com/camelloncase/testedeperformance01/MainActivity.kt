package com.camelloncase.testedeperformance01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.camelloncase.testedeperformance01.database.Recipe
import com.camelloncase.testedeperformance01.ui.management.RecipeManagementActivity
import com.camelloncase.testedeperformance01.ui.recipes.NoteClickDeleteInterface
import com.camelloncase.testedeperformance01.ui.recipes.NoteClickInterface
import com.camelloncase.testedeperformance01.ui.recipes.RecipeViewModel
import com.camelloncase.testedeperformance01.ui.recipes.RecipesRecyclerViewAdapter
import com.camelloncase.testedeperformance01.util.showMessageToUser
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {

    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: RecipeViewModel
    private lateinit var recipesRecyclerView: RecyclerView
    private lateinit var addFloatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recipesRecyclerView = findViewById(R.id.recipesRecyclerView)
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
