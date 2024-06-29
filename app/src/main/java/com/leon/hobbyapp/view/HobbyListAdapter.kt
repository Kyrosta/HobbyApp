package com.leon.hobbyapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.leon.hobbyapp.databinding.CardItemBinding
import com.leon.hobbyapp.model.News
import com.squareup.picasso.Picasso

class HobbyListAdapter(val newsList: ArrayList<News>)
    : RecyclerView.Adapter<HobbyListAdapter.HobbyViewHolder>(), ButtonActionNav {
    class HobbyViewHolder(val binding: CardItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbyViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HobbyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: HobbyViewHolder, position: Int) {
        holder.binding.nav = this
        holder.binding.news = newsList[position]

        val picasso = Picasso.Builder(holder.binding.root.context)
        picasso.listener{picasso, uri, exception -> exception.printStackTrace()}
        picasso.build().load(newsList[position].image).into(holder.binding.imgPhoto)
    }
    fun updateNews(newNewsList: List<News>){
        newsList.clear()
        newsList.addAll(newNewsList)
        notifyDataSetChanged()
    }

    override fun onButtonActionNavClick(v: View) {
        val id = v.tag.toString()
        val action = HomeFragmentDirections.actionDetailFragment(id.toInt())
        Navigation.findNavController(v).navigate(action)
    }
}


