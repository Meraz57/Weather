package com.example.weather.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.weather.MainActivity
import com.example.weather.R
import com.example.weather.databinding.FragmentLoginBinding


class Login : Fragment() {

    private var _binding:FragmentLoginBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding= FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGoogle.setOnClickListener {
            Intent(context, MainActivity::class.java).apply {
                startActivity(this)
            }
            requireActivity().finish()

        }
        binding.btnFacebook.setOnClickListener {
            Intent(context, MainActivity::class.java).apply {
                startActivity(this)
            }
            requireActivity().finish()

        }

    }
    


}