package com.camelloncase.testedeperformance03.ui.management

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.camelloncase.testedeperformance03.R
import com.camelloncase.testedeperformance03.databinding.FragmentRecipeManagementBinding
import com.camelloncase.testedeperformance03.databinding.FragmentRegisterBinding
import com.camelloncase.testedeperformance03.ui.recipes.RecipeViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RecipeManagementActivity : Fragment() {

    private var _binding: FragmentRecipeManagementBinding? = null
    private val binding get() = _binding!!
//    private val isExternalStorageReadOnly = getExternalStorageReadOnlyStatus()
//    private val isExternalStorageAvailable = getExternalStorageAvailableStatus()
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecipeManagementBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, viewModelFactory)[RecipeViewModel::class.java]

        recipeNameEdt = binding.nameTextInputEditText
        recipeStyleEdt = binding.styleTextInputEditText
        submitButton = binding.submitButton
        cancelButton = binding.cancelButton

        val actionType = intent.getStringExtra("actionType")
        when(actionType) {
            "Edit" -> {
                fillUpdateRecipeForm()
                saveOnFileButton.isEnabled = false
            }
            else -> submitButton.text = getString(R.string.btn_title_save)
        }

        submitButton.setOnClickListener {

            val recipeName = recipeNameEdt.text.toString()
            val recipeStyle = recipeStyleEdt.text.toString()

            when (actionType) {
                "Edit" -> fillFormToEditRecipeItem(recipeName, recipeStyle)
                else -> getCurrentDateAndAddNewRecipe(recipeName, recipeStyle)
            }

        }

        cancelButton.setOnClickListener {

            goToMainActivity()

        }

        saveOnFileButton.setOnClickListener {

            callWriteOnSDCard()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            var i = 0
            while (i < permissions.size) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    when (permissions[i]) {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE -> saveRecipeOnFile()
                    }
                }
                i++
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun callWriteOnSDCard() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                )) {
                callPermissionDialog(this, this@RecipeManagementActivity,
                    "It is necessary to allow WRITE_EXTERNAL_STORAGE!",
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSIONS_CODE
                )
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSIONS_CODE
                )
            }
        } else {
            saveRecipeOnFile()
        }
    }

    private fun saveRecipeOnFile() {

        val fileName = getFileName(recipeNameEdt.text.toString())
        val fileData = prepareFileData(recipeNameEdt.text.toString(), recipeStyleEdt.text.toString())

        myExternalFile = File(getExternalFilesDir(filepath), fileName)

        if (myExternalFile!!.exists()) {

            myExternalFile!!.delete()

        } else {

            try {

                val fileOutPutStream = FileOutputStream(myExternalFile)
                fileOutPutStream.write(fileData.toByteArray())
                fileOutPutStream.close()

                showMessageToUser(this, "Recipe successfully saved on file!")

            } catch (e: IOException) {

                e.printStackTrace()

                showMessageToUser(this, "Error on write file to disk!")
            }
            goToMainActivity()
        }
    }

    private fun fillUpdateRecipeForm() {

        val recipeName = intent.getStringExtra("recipeName")
        val recipeStyle = intent.getStringExtra("recipeStyle")

        recipeId = intent.getIntExtra("recipeId", -1)
        recipeNameEdt.setText(recipeName)
        recipeStyleEdt.setText(recipeStyle)
        submitButton.text = getString(R.string.btn_title_update)
    }

    private fun fillFormToEditRecipeItem(recipeName: String, recipeStyle: String) {

        val currentDate = formattedCurrentDate("dd MMM yy - hh:mm")

        if (recipeName.isNotEmpty() && recipeStyle.isNotEmpty()) {

            val updatedRecipe = Recipe(recipeName, recipeStyle, currentDate)

            updatedRecipe.recipeId = recipeId
            viewModel.updateRecipe(updatedRecipe)

            showMessageToUser(this, "Recipe updated..")
            goToMainActivity()

        } else {

            showMessageToUser(this, "Error when try to edit recipe")
            goToMainActivity()
        }
    }

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
