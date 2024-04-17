package com.example.readsaga.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.readsaga.R
import com.example.readsaga.databinding.FragmentBookDetailBinding
import com.example.readsaga.viewmodel.BookDetailViewModel
import com.example.readsaga.viewmodel.BookViewModel
import com.squareup.picasso.Picasso


class BookDetailFragment : Fragment() {
    private lateinit var binding: FragmentBookDetailBinding
    private lateinit var viewModel: BookDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null){
            viewModel = ViewModelProvider(this).get(BookDetailViewModel::class.java)
            viewModel.getDetails(BookDetailFragmentArgs.fromBundle(requireArguments()).id)
            observeViewModel()
        }
    }
    private fun observeViewModel() {
        viewModel.bookDetailLD.observe(viewLifecycleOwner, Observer {
            Log.d("test", it.id.toString())
            if (it != null){

                binding.txtTitleDetail.text = it.title
                binding.txtSenderDetail.text = it.sender
                binding.txtCommentDetail.text = it.comment
//                Picasso.get().load(it.image).into(binding.imageViewBookDetail)
                val picasso = this.context?.let { it1 -> Picasso.Builder(it1) }
                picasso?.listener { picasso, uri, exception ->
                    exception.printStackTrace()
                }
                picasso?.build()?.load(viewModel.bookDetailLD.value?.image)?.into(binding.imageViewBookDetail)
            }

        })
    }
}