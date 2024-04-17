package com.example.readsaga.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.readsaga.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserViewModel(application: Application): AndroidViewModel(application) {
    val userLD = MutableLiveData<User>()
    val registerLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "VolleyTag"
    private var queue: RequestQueue? = null

    fun login(username:String, password:String){
        loadingLD.value = true
        queue = Volley.newRequestQueue(getApplication())
        //IP Change
        val url = "http://10.0.2.2/readsaga/login.php"

        val stringRequest = object : StringRequest(
            Method.POST, url, {response->
                userLD.value = Gson().fromJson(response, User::class.java)
                loadingLD.value = false

                Toast.makeText(getApplication(), "Login Successful", Toast.LENGTH_SHORT).show()
                Log.d("Success", response)
            }, {
                loadingLD.value = false
                Toast.makeText(getApplication(), "Login Failed", Toast.LENGTH_SHORT).show()
                Log.d("login error", it.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun register(email: String, username: String, first_name: String, last_name: String, password: String, url_photo: String){
        queue = Volley.newRequestQueue(getApplication())
        //IP Change
        val url = "http://10.0.2.2/readsaga/register.php"

        val stringRequest = object : StringRequest(
            Method.POST, url, {response->
                registerLD.value = true
                Toast.makeText(getApplication(), "Register Success", Toast.LENGTH_SHORT).show()
                Log.d("Success", response)
            }, {
                registerLD.value = false
//                Toast.makeText(getApplication(), "Register Failed", Toast.LENGTH_SHORT).show()
                Log.d("Register Failed", it.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["email"] = email
                params["username"] = username
                params["first_name"] = first_name
                params["last_name"] = last_name
                params["password"] = password
                params["url_photo"] = url_photo
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