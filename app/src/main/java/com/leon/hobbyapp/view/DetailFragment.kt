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

        if (arguments != null){
            viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
            viewModel.detail(DetailFragmentArgs.fromBundle(requireArguments()).id)
            observeViewModel()
        }
    }

    fun observeViewModel() {
        viewModel.hobbyDetailLD.observe(viewLifecycleOwner, Observer {
            binding.apply {

                txtTitle.text = it.title
                txtUsername.text = "@${it.createdBy}"

                val wordLimitPerPage = 50
                val words = it.content?.split(" ") ?: listOf()
                val pages = words.chunked(wordLimitPerPage).map { it.joinToString(" ") }

                var currentPageIndex = 0
                displayPage(currentPageIndex, pages)
                buttonState(currentPageIndex, pages.size)

                btnNext.setOnClickListener {
                    currentPageIndex++
                    displayPage(currentPageIndex, pages)
                    buttonState(currentPageIndex, pages.size)
                }

                btnPrev.setOnClickListener {
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