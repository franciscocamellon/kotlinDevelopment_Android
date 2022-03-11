package com.camelloncase.testedeperformance03.ui.recover

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.camelloncase.testedeperformance03.databinding.FragmentRecoverBinding


class RecoverFragment : Fragment() {

    private var _binding: FragmentRecoverBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()

        _binding = FragmentRecoverBinding.inflate(inflater, container, false)

        binding.textView.setOnClickListener {
            findNavController().navigate(
                RecoverFragmentDirections.actionNavigationRecoverToNavigationLogin()
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}