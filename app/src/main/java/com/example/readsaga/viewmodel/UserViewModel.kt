package com.example.readsaga.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.readsaga.model.User
import com.example.readsaga.view.LoginFragmentDirections
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class UserViewModel(application: Application): AndroidViewModel(application) {
    val userLD = MutableLiveData<User?>()
    val registerLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val isLoginLD = MutableLiveData<Boolean>()

    val TAG = "VolleyTag"
    private var queue: RequestQueue? = null
fun login(username:String, password:String, view: View){
    loadingLD.value = true
    queue = Volley.newRequestQueue(getApplication())
    val url = "http://10.0.2.2/readsaga/login.php"
    val stringRequest = object : StringRequest(
        Method.POST, url, {response->
            val obj = JSONObject(response)
            Log.d("CEK OBJ", obj.getString("Result"))
            if (obj.getString("Result") == "Success") {
                val data = obj.getString("Data")
                val sType =object :TypeToken<User>() { }.type
                val result =Gson().fromJson<User>(data, sType)
                userLD.value = result

                val action = LoginFragmentDirections.actionBookListFragment(result.id.toString())
                Navigation.findNavController(view).navigate(action)
                Log.d("LoginResponse", data)
                Toast.makeText(getApplication(), "Login Successful", Toast.LENGTH_SHORT).show()
                Log.d("CEK USRLD", userLD.value.toString())

                isLoginLD.value = true
            }
            else{
                userLD.value = null
                Toast.makeText(getApplication(), "Login Gagal", Toast.LENGTH_SHORT).show()
                Log.d("login error", response.toString())
                isLoginLD.value = false
            }
        },
        {
            Log.d("login error", it.message.toString())
        }
    )
    {
        override fun getParams(): MutableMap<String, String>? {
            val params = HashMap<String, String>()
            params["username"] = username
            params["password"] = password
            return params
        }
    }
    stringRequest.tag = TAG
    queue?.add(stringRequest)
}
    fun register(email: String, username: String, firstName: String, lastName: String, password: String, urlPhoto: String){
        queue = Volley.newRequestQueue(getApplication())
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
                params["first_name"] = firstName
                params["last_name"] = lastName
                params["password"] = password
                params["url_photo"] = urlPhoto
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun getUserProfile(id: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/readsaga/get_profile.php"
        val stringRequest = object : StringRequest(
            Method.POST, url, {response->
                val obj = JSONObject(response)
                Log.d("OBJ UP", obj.getString("Result"))
                if (obj.getString("Result") == "Success") {
                    val data = obj.getString("Data")
                    val sType =object :TypeToken<User>() { }.type
                    val result =Gson().fromJson<User>(data, sType)
                    userLD.value = result
                    Log.d("USER PROFILE", result.toString())
                }
            },
            {
                Log.d("Failed getUserProfile", it.message.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = id
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun updateUserProfile(id: String, firstName: String, lastName: String, oldPass: String, newPass: String, urlPhoto: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/readsaga/update_profile.php"
        val stringRequest = object : StringRequest(
            Method.POST, url, {response->
                Log.d("OBJ UPDATE", response)
                val obj = JSONObject(response)
                val result = obj.getString("Result")
                val message = obj.getString("message")
                if (result == "Success") {
                    Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(getApplication(), "Update failed: $message", Toast.LENGTH_SHORT).show()
                }
            },
            {
                Toast.makeText(getApplication(), it.message.toString(), Toast.LENGTH_SHORT).show()
                Log.d("Failed getUserProfile", it.message.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = id
                params["firstName"] = firstName
                params["lastName"] = lastName
                params["oldPass"] = oldPass
                params["newPass"] = newPass
                params["urlPhoto"] = urlPhoto

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