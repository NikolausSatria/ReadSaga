package com.example.readsaga.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.readsaga.databinding.BookListItemBinding
import com.example.readsaga.model.Books
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class BookListAdapter(val bookList: ArrayList<Books>): RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {
    class BookViewHolder(var binding: BookListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = BookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        with(holder.binding){
            val picasso = Picasso.Builder(holder.itemView.context)
            picasso.listener { picasso, uri, exception ->
                exception.printStackTrace()
            }
            picasso.build().load(bookList[position].image).into(imageView)
            picasso.build().load(bookList[position].image).into(imageView, object:
                Callback {
                override fun onSuccess() {
                    progressImage.visibility = View.INVISIBLE
                    imageView.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Log.e("picasso_error", e.toString())
                }
            })
            txtBookID.text = bookList[position].id
            txtBookTitle.text = bookList[position].title
            txtBookDescription.text = bookList[position].description

            btnReadMore.setOnClickListener {
                val action = BookListFragmentDirections.actionBookDetailFragment(bookList[position].id.toString())
                Navigation.findNavController(it).navigate(action)
                Log.d("check id", bookList[position].id.toString())
            }
        }
    }

    fun updateBookList(newBookList:ArrayList<Books>) {
        bookList.clear()
        bookList.addAll(newBookList)
        notifyDataSetChanged()
    }
}