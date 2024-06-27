package com.leon.hobbyapp.viewmodel

import android.app.Application
import android.content.Context
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leon.hobbyapp.model.Hobby
import com.leon.hobbyapp.model.User
import com.leon.hobbyapp.view.RegisterFragmentDirections
import org.json.JSONObject

class UserViewModel(application: Application): AndroidViewModel(application) {
    var userLD = MutableLiveData<User>()
    val registerLD = MutableLiveData<Boolean>()
    val updateLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun signin(username: String, password: String) {
        val url = "http://10.0.2.2/hobbyapp/login.php"
        queue = Volley.newRequestQueue(getApplication())

        val stringRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                userLD.value = Gson().fromJson(response, User::class.java)
                Log.d("Login", "Result: $response")
            },
            { error ->
                Log.e("Login", "Error: ${error.message}")
            }
        ) {
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

fun register(username:String, firstName: String, lastName: String, email: String, password: String){

    queue = Volley.newRequestQueue(getApplication())
    val url = "http://10.0.2.2/hobbyapp/register.php"
    val stringRequest = object : StringRequest(
        Method.POST, url,
        {response->
            registerLD.value = true
            Log.d("Register", "Result: ${response}")
        },
        {
            registerLD.value = false
            Log.d("Register", it.toString())
        }
    ) {
        override fun getParams(): MutableMap<String, String> {
            val params = HashMap<String, String>()
            params["username"] = username
            params["firstname"] = firstName
            params["lastname"] = lastName
            params["email"] = email
            params["password"] = password
            return params
        }
    }
    stringRequest.tag = TAG
    queue?.add(stringRequest)
}

fun update(id:Int, firstName: String, lastName: String, newPassword: String){

    queue = Volley.newRequestQueue(getApplication())
    val url = "http://10.0.2.2/hobbyapp/update.php"
    val stringRequest = object : StringRequest(
        Method.POST, url,{response->
            updateLD.value = true
            Log.d("Update", "Result: ${response}")
        },
        {
            updateLD.value = false
            Log.d("Update", it.toString())
        }
    ){
        override fun getParams(): MutableMap<String, String>? {
            val params = HashMap<String, String>()
            params["id"] = id.toString()
            params["firstName"] = firstName
            params["lastName"] = lastName
            params["password"] = newPassword
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