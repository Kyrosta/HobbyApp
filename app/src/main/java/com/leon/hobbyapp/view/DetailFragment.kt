package com.leon.hobbyapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.leon.hobbyapp.R
import com.leon.hobbyapp.databinding.FragmentDetailBinding
import com.leon.hobbyapp.viewmodel.DetailViewModel
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.core.Observer

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
        if (arguments != null) {
            val id = DetailFragmentArgs.fromBundle(requireArguments()).id
            viewModel.fetch(id)
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.dataLD.observe(viewLifecycleOwner, Observer {
            with (binding) {
                HomeActivity.load_picture(requireView(), it.photo_url.toString(), imgPhotoDetail)
                var now_index = 0
                txtTitle.text = it.title
                txtUsername.text = "Dibuat oleh ${it.creator}"
                val content_per_paragraph = it.content?.split("\n")
                Log.d("cekdata", content_per_paragraph.toString())
                val size_par = content_per_paragraph?.size

                txtContent.text = content_per_paragraph?.get(now_index)
                checkPageNow(now_index, size_par!!.toInt())

                btnNext.setOnClickListener {
                    now_index++
                    txtContent.text = content_per_paragraph[now_index]
                    checkPageNow(now_index, size_par.toInt())
                }

                btnPrev.setOnClickListener {
                    now_index--
                    txtContent.text = content_per_paragraph[now_index]
                    checkPageNow(now_index, size_par.toInt())
                }
            }
        })
    }

    fun checkPageNow(now_index: Int, size_par: Int) {
        with (binding) {
            when (now_index) {
                0 -> {
                    btnPrev.isEnabled = false
                    btnNext.isEnabled = true
                }
                size_par.minus(1) -> {
                    btnPrev.isEnabled = true
                    btnNext.isEnabled = false
                }
                else -> {
                    btnPrev.isEnabled = true
                    btnNext.isEnabled = true
                }
            }
        }
    }
}