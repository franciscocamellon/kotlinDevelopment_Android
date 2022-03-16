package com.camelloncase.testedeperformance01.ui.management

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.camelloncase.testedeperformance01.MainActivity
import com.camelloncase.testedeperformance01.R
import com.camelloncase.testedeperformance01.model.Recipe
import com.camelloncase.testedeperformance01.ui.recipes.RecipeViewModel
import com.camelloncase.testedeperformance01.util.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RecipeManagementActivity : AppCompatActivity() {

    private val filepath = "BeerRecipesStorage"
    private var myExternalFile: File? = null
    private val isExternalStorageReadOnly = getExternalStorageReadOnlyStatus()
    private val isExternalStorageAvailable = getExternalStorageAvailableStatus()
    private lateinit var recipeNameEdt: EditText
    private lateinit var recipeStyleEdt: EditText
    private lateinit var submitButton: Button
    private lateinit var cancelButton: Button
    private lateinit var saveOnFileButton: Button
    private lateinit var viewModelFactory: ViewModelProvider.AndroidViewModelFactory
    private lateinit var viewModel: RecipeViewModel
    private var recipeId = -1

    companion object {
        private const val REQUEST_PERMISSIONS_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_management)

        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[RecipeViewModel::class.java]

        recipeNameEdt = findViewById(R.id.nameTextInputEditText)
        recipeStyleEdt = findViewById(R.id.styleTextInputEditText)
        submitButton = findViewById(R.id.submitButton)
        cancelButton = findViewById(R.id.cancelButton)

        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            saveOnFileButton.isEnabled = false
        }
        saveOnFileButton = findViewById(R.id.fileButton)

        val actionType = intent.getStringExtra("actionType")
        when(actionType) {
            "Edit" -> {
//                fillUpdateRecipeForm()
                saveOnFileButton.isEnabled = false
            }
            else -> submitButton.text = getString(R.string.btn_title_save)
        }

        submitButton.setOnClickListener {

            val recipeName = recipeNameEdt.text.toString()
            val recipeStyle = recipeStyleEdt.text.toString()

            when (actionType) {
//                "Edit" -> fillFormToEditRecipeItem(recipeName, recipeStyle)
                else -> getCurrentDateAndAddNewRecipe(recipeName, recipeStyle)
            }

        }

        cancelButton.setOnClickListener {

            goToMainActivity()

        }
    }

//    private fun fillFormToEditRecipeItem(recipeName: String, recipeStyle: String) {
//
//        val currentDate = formattedCurrentDate("dd MMM yy - hh:mm")
//
//        if (recipeName.isNotEmpty() && recipeStyle.isNotEmpty()) {
//
//            val updatedRecipe = Recipe(recipeName, recipeStyle, currentDate)
//
//            updatedRecipe.id = id
//            viewModel.updateRecipe(updatedRecipe)
//
//            showMessageToUser(this, "Recipe updated..")
//            goToMainActivity()
//
//        } else {
//
//            showMessageToUser(this, "Error when try to edit recipe")
//            goToMainActivity()
//        }
//    }

    private fun getCurrentDateAndAddNewRecipe(recipeName: String, recipeStyle: String) {

        if (recipeName.isNotEmpty() && recipeStyle.isNotEmpty()) {

            val currentDate = formattedCurrentDate("dd MMM yy - hh:mm")

            viewModel.addRecipe(Recipe(recipeName, recipeStyle, currentDate))

            showMessageToUser(this, "$recipeName successful added!")

            goToMainActivity()

        } else {
            showMessageToUser(this, "Please enter the information required!")
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        this.finish()
    }
}
