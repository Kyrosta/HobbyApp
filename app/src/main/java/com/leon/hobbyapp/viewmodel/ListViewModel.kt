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

class ListViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    private val job = Job()
    val newsLD = MutableLiveData<List<News>>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()

    fun refresh() {
        loadingLD.value = true
        errorLD.value = false

        launch {
            val db = buildDb(getApplication())
            newsLD.postValue(db.hobbyDao().selectAllNews())
            loadingLD.postValue(false)
        }
    }

    fun delete(news: News){
        launch {
            val db = buildDb(getApplication())
            db.hobbyDao().deleteNews(news)
            newsLD.postValue(db.hobbyDao().selectAllNews())
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}