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
import com.leon.hobbyapp.model.Hobby

class DetailViewModel(application: Application):AndroidViewModel(application) {
    val hobbyDetailLD = MutableLiveData<Hobby>()
    val loadingLD = MutableLiveData<Boolean>()
    val errorLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun detail(id: String){
        loadingLD.value = true
        errorLD.value = false

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/hobbyapp/detail.php?id=${id}"

        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                hobbyDetailLD.value = Gson().fromJson(it, Hobby::class.java)
                Log.d("ShowVolley", it)
            },
            {
                Log.d("ShowVolley", it.toString())
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }


}