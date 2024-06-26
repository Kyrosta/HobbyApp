package com.leon.hobbyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leon.hobbyapp.databinding.FragmentDetailBinding
import com.leon.hobbyapp.model.Hobby
import com.leon.hobbyapp.viewmodel.DetailViewModel
import com.leon.hobbyapp.viewmodel.ListViewModel
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private lateinit var binding:FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_detail, container, false)
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val id = DetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel.detail(id)
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.hobbyDetailLD.observe(viewLifecycleOwner, Observer {
            binding.apply {
                val picasso = Picasso.Builder(binding.root.context)
                picasso.listener{picasso, uri, exception -> exception.printStackTrace()}
                picasso.build().load(it.imageUrl).into(binding.imgPhoto)

                txtTitle.text = it.title
                txtUsername.text = "@${it.createdBy}"
                txtDesc.text = it.content

                val wordLimitPerPage = 50 //max per halaman
                val words = it.content?.split(" ") ?: listOf() //menyimpan content lalu di split
                val pages = words.chunked(wordLimitPerPage).map { it.joinToString(" ") } //content dibagi123

                var currentPageIndex = 0

                if (currentPageIndex >= 0 && currentPageIndex < pages.size) {
                    txtDesc.text = pages[currentPageIndex]
                }
                btnPrev.isEnabled = currentPageIndex > 0
                btnNext.isEnabled = currentPageIndex < pages.size - 1

                btnNext.setOnClickListener {
                    if (currentPageIndex < pages.size - 1) {
                        currentPageIndex++
                        txtDesc.text = pages[currentPageIndex]
                        btnPrev.isEnabled = true
                        btnNext.isEnabled = currentPageIndex < pages.size - 1
                    }
                }
                btnPrev.setOnClickListener {
                    if (currentPageIndex > 0) {
                        currentPageIndex--
                        txtDesc.text = pages[currentPageIndex]
                        btnPrev.isEnabled = currentPageIndex > 0
                        btnNext.isEnabled = true
                    }
                }
            }
        })
    }
}