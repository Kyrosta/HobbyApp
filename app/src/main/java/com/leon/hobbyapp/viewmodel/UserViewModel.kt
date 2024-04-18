package com.leon.hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leon.hobbyapp.model.User
import com.leon.hobbyapp.view.MainActivity.Companion.user

class UserViewModel(application: Application): AndroidViewModel(application) {
    val userLD = MutableLiveData<User>()
    var successLD = MutableLiveData<Boolean>()
    val userErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun signin(user: String, pass: String) {
        loadingLD.value = true
        userErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/hobbyapp/login.php?username=$user&password=$pass"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object: TypeToken<User>() {}.type
                val result = Gson().fromJson<User>(it, sType)
                userLD.value = result
                loadingLD.value = false
                Log.d("showvolley", it)
            },
            {
                Log.d("showvolley", it.toString())
                userErrorLD.value = true
                loadingLD.value = false
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun register(user: User){
        loadingLD.value = true
        userErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/hobbyapp/register.php?username=${user.username}&firstname=${user.firstName}&lastname=${user.lastName}&email=${user.email}&password=${user.password}"
        Log.d("url", url)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object: TypeToken<Boolean>() {}.type
                val result = Gson().fromJson<Boolean>(it, sType)
                successLD.value = result
                loadingLD.value = false
                Log.d("showvolley", it)
            },
            {
                Log.d("showvolley", it.toString())
                userErrorLD.value = true
                loadingLD.value = false
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun update(firstName: String, lastName: String, newPassword: String){
        loadingLD.value = true
        userErrorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/hobbyapp/update.php?username=${user?.username}&firstname=$firstName&lastname=$lastName&newpass=$newPassword"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object: TypeToken<Boolean>() {}.type
                val result = Gson().fromJson<Boolean>(it, sType)
                successLD.value = result
                loadingLD.value = false
                Log.d("showvolley", it)
            },
            {
                Log.d("showvolley", it.toString())
                userErrorLD.value = true
                loadingLD.value = false
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}