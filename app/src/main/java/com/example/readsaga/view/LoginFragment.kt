package com.example.readsaga.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readsaga.R
import com.example.readsaga.databinding.FragmentLoginBinding
import com.example.readsaga.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationItemView


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

        binding.btnLogin.setOnClickListener {
            val username = "niko"
            val password = "123"
//            val username = binding.txtUsername.text.toString()
//            val password = binding.txtPassword.text.toString()
            viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
            viewModel.login(username, password)

            viewModel.userLD.observe(viewLifecycleOwner, Observer {
                val userLogin = it
                if (userLogin != null){
                    val action = LoginFragmentDirections.actionBookList()
                    Navigation.findNavController(view).navigate(action)
                }
            })
        }

        binding.btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionRegister()
            Navigation.findNavController(view).navigate(action)
        }

    }

}