package com.leon.hobbyapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.leon.hobbyapp.R
import com.leon.hobbyapp.databinding.FragmentDetailBinding
import com.leon.hobbyapp.viewmodel.ListViewModel
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private lateinit var binding:FragmentDetailBinding
    private lateinit var viewModel: ListViewModel

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
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        if (arguments != null) {
            val id = DetailFragmentArgs.fromBundle(requireArguments()).id
            viewModel.detail(id)
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.hobbyLD.observe(viewLifecycleOwner, Observer { hobbies ->
            if (hobbies.isNotEmpty()) {
                val hobby = hobbies[0]
                binding.txtTitle.text = hobby.title
                binding.txtUsername.text = "@${hobby.createdBy}"

                val wordLimitPerPage = 50
                val words = hobby.content?.split(" ") ?: listOf()
                val pages = words.chunked(wordLimitPerPage).map { it.joinToString(" ") }

                var currentPageIndex = 0
                displayPage(currentPageIndex, pages)

                binding.btnPrev.isEnabled = false
                binding.btnNext.isEnabled = pages.size > 1

                binding.btnNext.setOnClickListener {
                    currentPageIndex++
                    displayPage(currentPageIndex, pages)
                    buttonState(currentPageIndex, pages.size)
                }

                binding.btnPrev.setOnClickListener {
                    currentPageIndex--
                    displayPage(currentPageIndex, pages)
                    buttonState(currentPageIndex, pages.size)
                }
            }
        })
    }

    fun displayPage(index: Int, pages: List<String>) {
        if (index >= 0 && index < pages.size) {
            binding.txtDesc.text = pages[index]
        }
    }

    fun buttonState(currentPageIndex: Int, pageCount: Int) {
        binding.btnPrev.isEnabled = currentPageIndex > 0
        binding.btnNext.isEnabled = currentPageIndex < pageCount - 1
    }
}