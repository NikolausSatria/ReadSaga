package com.example.readsaga.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.readsaga.R
import com.example.readsaga.databinding.FragmentBookListBinding
import com.example.readsaga.viewmodel.BookViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class BookListFragment : Fragment() {
    private lateinit var binding: FragmentBookListBinding
    private lateinit var viewModel:BookViewModel
    private val bookListAdapter =BookListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)
        viewModel.fetch()


        (activity as MainActivity).userId = BookListFragmentArgs.fromBundle(requireArguments()).userId
        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = bookListAdapter

        Log.d("CEK USERID", (activity as MainActivity).userId)
        val swipe = view.findViewById<SwipeRefreshLayout>(R.id.refreshLayout)
        swipe.setOnRefreshListener {
            viewModel.fetch()
            binding.recView.visibility = View.GONE
            binding.txtError.visibility = View.GONE
            binding.progressLoad.visibility = View.VISIBLE
            swipe.isRefreshing = false
        }
        observeViewModel()
    }
    fun observeViewModel() {
        viewModel.bookLD.observe(viewLifecycleOwner, Observer {
            bookListAdapter.updateBookList(it)
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.recView.visibility = View.GONE
                binding.progressLoad.visibility = View.INVISIBLE
            }
            else {
                binding.recView.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })

        viewModel.bookLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.txtError?.visibility = View.VISIBLE
            }
            else {
                binding.txtError?.visibility = View.GONE
            }
        })
    }
}