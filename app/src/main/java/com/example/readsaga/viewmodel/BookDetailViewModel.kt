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
//import com.example.readsaga.model.Paragraphs
import com.google.gson.Gson
import org.json.JSONObject

class BookDetailViewModel(application: Application): AndroidViewModel(application) {
    val bookDetailLD = MutableLiveData<Books>()
    val bookLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "VolleyTag"
    private var queue: RequestQueue? = null
    fun getDetails(id:String){
        queue = Volley.newRequestQueue(getApplication())
        val bookUrl = "http://10.0.2.2/readsaga/book_detail.php?id=$id"

        val bookRequest = StringRequest(
            Request.Method.GET, bookUrl, {
                val jsonObject = JSONObject(it)
                val dataArray = jsonObject.getJSONArray("data")
                if (dataArray.length() > 0) {
                    val data = dataArray.getJSONObject(0).toString()
                    val result = Gson().fromJson(data, Books::class.java)
                    bookDetailLD.value = result
                    loadingLD.value = false
                }
                Log.d("BookDetailViewModel: get book success", it)
            }, {
                Log.d("Error", it.toString())
                bookLoadErrorLD.value = true
                loadingLD.value = false
            }
        )
        bookRequest.tag = TAG
        queue?.add(bookRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}