package com.example.readsaga.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.readsaga.R
import com.example.readsaga.databinding.FragmentHistoryBinding
import com.example.readsaga.viewmodel.BookViewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var viewModel: BookViewModel
    private  val bookListAdapter = BookListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = (activity as MainActivity).userId


        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        viewModel.getHistory(userId)
        binding.recViewHistory.layoutManager = LinearLayoutManager(context)
        binding.recViewHistory.adapter = bookListAdapter
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.bookLD.observe(viewLifecycleOwner, Observer {
            bookListAdapter.updateBookList(it)
        })
    }
}