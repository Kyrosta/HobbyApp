package com.leon.hobbyapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.leon.hobbyapp.R
import com.leon.hobbyapp.databinding.FragmentHomeBinding
import com.leon.hobbyapp.model.Hobby
import com.leon.hobbyapp.viewmodel.ListViewModel


class HomeFragment : Fragment() {

    private lateinit var bind: FragmentHomeBinding
    private val hobby = arrayListOf<Hobby>()
    private val adapter = HobbyListAdapter(hobby)
    private lateinit var viewModel : ListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        observeViewModel()

        bind.recyclerView.layoutManager = LinearLayoutManager(context)
        bind.recyclerView.adapter = adapter
    }

    fun observeViewModel(){
        viewModel.hobbyLD.observe(viewLifecycleOwner, Observer {
            Log.d("Data", it.toString())
            adapter.updateHobby(it)
        })
    }
}