package com.example.readsaga.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.readsaga.R
import com.example.readsaga.databinding.FragmentProfileBinding
import com.example.readsaga.viewmodel.UserViewModel
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel:UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val userId = (activity as MainActivity).userId
        Log.d("CEK USRID", userId)

        viewModel.getUserProfile(userId)
        viewModel.userLD.observe(viewLifecycleOwner, Observer{
            val user = viewModel.userLD.value
            binding.txtUsernameProfile.setText(user?.username)
            binding.txtFirstNameProfile.setText(user?.first_name)
            binding.txtLastNameProfile.setText(user?.last_name)
            binding.txtImageUrlProfile.setText(user?.url_photo)
//            Picasso.get().load(user?.url_photo).into(binding.imageViewProfile)
            if (!user?.url_photo.isNullOrEmpty()) {
                Picasso.get().load(user?.url_photo).into(binding.imageViewProfile)
            } else {
                Picasso.get().load(R.drawable.baseline_person_24).into(binding.imageViewProfile)
            }
        })

        binding.btnUpdate.setOnClickListener {
            val firstName = binding.txtFirstNameProfile.text
            val lastName = binding.txtLastNameProfile.text
            val oldPass = binding.txtOldPassword.text
            val newPass = binding.txtNewPassword.text
            val urlPhoto = binding.txtImageUrlProfile.text

            viewModel.updateUserProfile(userId, firstName.toString(), lastName.toString(), oldPass.toString(), newPass.toString(), urlPhoto.toString())
            viewModel.getUserProfile(userId)
            binding.txtOldPassword.text.clear()
            binding.txtNewPassword.text.clear()
        }

        binding.btnLogOut.setOnClickListener {
            (activity as MainActivity).userId = ""
            viewModel.userLD.value = null
            val action = ProfileFragmentDirections.actionLoginFragment()
            Navigation.findNavController(it).navigate(action)

        }

    }

}