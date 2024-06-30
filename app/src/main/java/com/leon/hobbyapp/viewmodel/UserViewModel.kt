package com.leon.hobbyapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.leon.hobbyapp.model.User
import com.leon.hobbyapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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

    fun update(password:String, uuid: Int) {
        launch {
            val db = buildDb(getApplication())
            db.hobbyDao().updateUser(password, uuid)
            updateLD.postValue(true)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

}