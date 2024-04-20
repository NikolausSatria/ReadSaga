package com.example.readsaga.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.readsaga.R
import com.example.readsaga.databinding.FragmentRegisterBinding
import com.example.readsaga.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class RegisterFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreate.setOnClickListener { view->
            val email = binding.txtEmail.text.toString()
            val username = binding.txtUsername.text.toString()
            val firstName = binding.txtFirstName.text.toString()
            val lastName = binding.txtLastName.text.toString()
            val password = binding.txtPassword.text.toString()
            val confirmPassword = binding.txtConfPassword.text.toString()

            if (email.isNotEmpty() && username.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && password.isNotEmpty()) {
                if (password == confirmPassword){
                    viewModel = ViewModelProvider(this@RegisterFragment).get(UserViewModel::class.java)
                    viewModel.register(email, username, firstName, lastName, password, "")
                    viewModel.registerLD.observe(viewLifecycleOwner, Observer{
                        if (it == true){
                            val action = RegisterFragmentDirections.actionLogin()
                            Navigation.findNavController(view).navigate(action)
                        }
                    })
                } else{
                    Toast.makeText(requireContext(), "Password and confirm password do not match", Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}