package com.camelloncase.testedeperformance03.ui.management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.camelloncase.testedeperformance03.R
import com.camelloncase.testedeperformance03.databinding.FragmentManagementBinding
import com.camelloncase.testedeperformance03.model.Recipe
import com.camelloncase.testedeperformance03.util.formattedCurrentDate
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ManagementFragment : Fragment() {

    private var _binding: FragmentManagementBinding? = null
    private val binding get() = _binding!!
    private var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentManagementBinding.inflate(inflater, container, false)

        binding.submitButton.setOnClickListener {
            val name = binding.nameTextInputEditText.text.toString()
            val style = binding.styleTextInputEditText.text.toString()
            val date = formattedCurrentDate("dd MMM yy - hh:mm")

            val recipe = Recipe(name, style, date)

            val documentReference = db.collection("Recipes")

            documentReference.add(recipe.toMap()).addOnSuccessListener {
                binding.nameTextInputEditText.text?.clear()
                binding.styleTextInputEditText.text?.clear()

                Toast.makeText(context,"Successfully Saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

}