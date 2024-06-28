package com.leon.hobbyapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leon.hobbyapp.model.News
import com.leon.hobbyapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel(application: Application):AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    val hobbyLD = MutableLiveData<News>()

    fun addTodo(list:List<News>) {
        launch {
            val db = buildDb(getApplication())
            db.hobbyDao().insertAllNews(*list.toTypedArray())
        }
    }

    fun fetch(uuid:Int) {
        launch {
            val db = buildDb(getApplication())
            hobbyLD.postValue(db.hobbyDao().selectNews(uuid))
        }
    }

    fun update(news: News) {
        launch {
            val db = buildDb(getApplication())
            db.hobbyDao().updateNews(news)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}