package com.leon.hobbyapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.leon.hobbyapp.databinding.CardItemBinding
import com.leon.hobbyapp.model.Hobby
import com.squareup.picasso.Picasso

class HobbyListAdapter(val hobbyList: ArrayList<Hobby>)
    : RecyclerView.Adapter<HobbyListAdapter.HobbyViewHolder>() {
    class HobbyViewHolder(val binding: CardItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbyViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HobbyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hobbyList.size
    }

    override fun onBindViewHolder(holder: HobbyViewHolder, position: Int) {
        val currHobby =hobbyList[position]
        holder.binding.txtTitle.text = currHobby.title
        holder.binding.txtUsername.text = currHobby.createdBy
        holder.binding.txtDesc.text = currHobby.description

        val picasso = Picasso.Builder(holder.binding.root.context)
        picasso.listener{picasso, uri, exception -> exception.printStackTrace()}
        picasso.build().load(hobbyList[position].imageUrl).into(holder.binding.imgPhoto)

        holder.binding.btnDetail.setOnClickListener {
            val action = HomeFragmentDirections.actionDetailFragment(currHobby.id.toString().toInt())
            Navigation.findNavController(it).navigate(action)
        }

    }
    fun updateHobby(newHobbyList: ArrayList<Hobby>){
        hobbyList.clear()
        hobbyList.addAll(newHobbyList)
        notifyDataSetChanged()
    }

}


