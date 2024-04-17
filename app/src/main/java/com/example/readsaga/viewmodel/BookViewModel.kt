package com.example.readsaga.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.readsaga.model.Books
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BookViewModel(application: Application): AndroidViewModel(application) {
    var bookLD = MutableLiveData<ArrayList<Books>>()
    var bookDetailLD = MutableLiveData<Books>()
    val bookLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetch(){
        loadingLD.value = true
        bookLoadErrorLD.value = false
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/readsaga/book.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                val sType = object : TypeToken<ArrayList<Books>>() {}.type
                val result = Gson().fromJson<List<Books>>(it, sType)
                bookLD.value = result as ArrayList<Books>?

                Log.d("Success", it)
                loadingLD.value = false
            }, {
                loadingLD.value = false
                bookLoadErrorLD.value = false
                Log.d("load error", it.toString())
            }
        )

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun getDetail(id:String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/readsaga/book_detail.php?id=$id"

        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                bookDetailLD.value = Gson().fromJson(it, Books::class.java)

                Log.d("Success", it)
            }, {
                Log.d("Error", it.toString())
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}