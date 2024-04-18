package com.leon.hobbyapp.view

import androidx.recyclerview.widget.RecyclerView

class HomeListAdapter(val fmList: ArrayList<FootballManager>): RecyclerView.Adapter<HomeListAdapter.HomeViewHolder>() {
    class HomeViewHolder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root)

}