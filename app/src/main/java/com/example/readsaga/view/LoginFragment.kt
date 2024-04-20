package com.example.readsaga.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readsaga.R
import com.example.readsaga.databinding.FragmentLoginBinding
import com.example.readsaga.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView


class LoginFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.btnLogin.setOnClickListener {
            val username = "niko"
            val password = "123"
//            val username = binding.txtUsername.text
//            val password = binding.txtPassword.text
            viewModel.login(username.toString(), password.toString(), it)
        }
        binding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionRegister()
            Navigation.findNavController(view).navigate(action)
        }
    }
}