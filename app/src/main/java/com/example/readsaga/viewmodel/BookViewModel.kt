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
import org.json.JSONException
import org.json.JSONObject

class BookViewModel(application: Application): AndroidViewModel(application) {
    var bookLD = MutableLiveData<ArrayList<Books>>()
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

    fun getHistory(id: String){
        loadingLD.value = true
        bookLoadErrorLD.value = false
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/readsaga/read_history.php"

        val stringRequest = object: StringRequest(
            Method.POST, url, { response->
                try {
                    val obj = JSONObject(response)
                    Log.d("CEK OBJ", obj.getString("Result"))
                    if (obj.getString("Result") == "Success") {
                        val data = obj.getJSONArray("Data")
                        val gson = Gson()
                        val booksListType = object : TypeToken<ArrayList<Books>>() {}.type
                        val result: ArrayList<Books> = gson.fromJson(data.toString(), booksListType)
                        bookLD.value = result

                        Log.d("Success", data.toString())
                    }
                } catch (e: JSONException) {
                    Log.e("JSON Error", "Error converting response to JSON", e)
                }
            }, {
                loadingLD.value = false
                Log.d("load error", it.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["id"] = id
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

        override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}