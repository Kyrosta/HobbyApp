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
import com.leon.hobbyapp.model.User
import com.leon.hobbyapp.util.buildDb
import com.leon.hobbyapp.view.RegisterFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application): AndroidViewModel(application) , CoroutineScope {
    private val job = Job()
    val userLD = MutableLiveData<User>()
    val registerLD = MutableLiveData<Boolean>()
    val updateLD = MutableLiveData<Boolean>()
    val loginLD = MutableLiveData<Boolean>()

    fun fetch(uuid:Int) {
        launch {
            val db = buildDb(getApplication())
            userLD.postValue(db.hobbyDao().selectUser(uuid))
        }
    }

    fun login(username:String,password: String){
        launch {
            val db = buildDb(getApplication())
            val user = db.hobbyDao().loginUser(username, password)
            if (user != null) {
                userLD.postValue(user)
                loginLD.postValue(true)
            } else {
                loginLD.postValue(false)
            }
        }
    }

    fun register(user: User){
        launch {
            val db = buildDb(getApplication())
            db.hobbyDao().insertAllUser(user)
            registerLD.postValue(true)
        }
    }

    fun update(username: String, email:String, password:String, uuid:Int) {
        launch {
            val db = buildDb(getApplication())
            db.hobbyDao().updateUser(username,email,password, uuid)
            updateLD.postValue(true)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

}