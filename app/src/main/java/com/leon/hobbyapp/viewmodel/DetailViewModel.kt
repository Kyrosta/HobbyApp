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
import com.leon.hobbyapp.model.Hobby

class DetailViewModel(application: Application):AndroidViewModel(application) {
    val hobbyDetailLD = MutableLiveData<Hobby>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun detail(id: Int){

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/hobbyapp/hobby.json?id=$id"

        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                Log.d("showvolley", it)
                val sType = object : TypeToken<List<Hobby>>() { }.type
                val result = Gson().fromJson<List<Hobby>>(it, sType)
                val data = result as ArrayList<Hobby>
                hobbyDetailLD.value = data[id-1]
            },
            {
                Log.d("showvolley", it.toString())
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}