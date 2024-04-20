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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso


class BookDetailFragment : Fragment() {
    private lateinit var binding: FragmentBookDetailBinding
    private lateinit var viewModel: BookDetailViewModel
    private var commentIndex = 0

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
            val book = BookDetailFragmentArgs.fromBundle(requireArguments()).id
            viewModel = ViewModelProvider(this).get(BookDetailViewModel::class.java)
            viewModel.getDetails(book)
            observeViewModel()
        }
    }
    fun observeViewModel() {
        viewModel.bookDetailLD.observe(viewLifecycleOwner, Observer {book->
            Log.d("BookDetailFragment: id", book.id.toString())
            if (book == null && viewModel.bookDetailLD.value?.comment == null){
            }
            else{
                binding.txtTitleDetail.text = book.title
                binding.txtAuthorDetail.text = "by " + book.author
                binding.txtSenderDetail.text = "@" + book.sender

                val picasso = this.context?.let { it1 -> Picasso.Builder(it1) }
                picasso?.listener { picasso, uri, exception ->
                    exception.printStackTrace()
                }
                picasso?.build()?.load(viewModel.bookDetailLD.value?.image)?.into(binding.imageViewBookDetail)

                val comment = book.comment?.split("\r\n")?.toTypedArray()
                binding.txtCommentDetail.text = comment?.get(commentIndex).toString()

                binding.btnPrev.visibility = View.GONE

                binding.btnNext.setOnClickListener {
                    if (comment != null) {
                        if (commentIndex < comment.size - 1) {
                            commentIndex++
                            binding.txtCommentDetail.text = comment[commentIndex]
                            binding.btnPrev.visibility = View.VISIBLE
                        }
                        if(commentIndex == comment.size -1) {
                            binding.btnNext.visibility = View.GONE
                        }
                    }
                }

                binding.btnPrev.setOnClickListener {
                    if (commentIndex > 0) {
                        commentIndex--
                        binding.txtCommentDetail.text = comment?.get(commentIndex)
                        binding.btnNext.visibility = View.VISIBLE
                    }
                    if (commentIndex == 0) {
                        binding.btnPrev.visibility = View.GONE
                    }
                }
            }
        })
    }
}