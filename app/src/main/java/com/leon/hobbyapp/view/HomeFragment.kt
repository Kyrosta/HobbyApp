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
import com.leon.hobbyapp.model.News
import com.leon.hobbyapp.viewmodel.ListViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val news = arrayListOf<News>()
    private val adapter = HobbyListAdapter(news)
    private lateinit var viewModel : ListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        binding.refreshLayout.setOnRefreshListener {
            binding.recyclerView.visibility =View.GONE
            binding.txtError.visibility = View.GONE
            binding.progressLoad.visibility =View.VISIBLE
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.newsLD.observe(viewLifecycleOwner, Observer { adapter.updateNews(it) })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true){
                binding.recyclerView.visibility = View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            }
            else{
                binding.recyclerView.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })

        viewModel.errorLD.observe(viewLifecycleOwner, Observer {
            if(it == true){
                binding.txtError.visibility = View.VISIBLE
            }
            else{
                binding.txtError.visibility = View.GONE
            }
        })
    }
}