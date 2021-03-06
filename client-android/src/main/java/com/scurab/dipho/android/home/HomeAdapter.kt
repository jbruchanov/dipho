package com.scurab.dipho.android.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scurab.dipho.android.databinding.ItemThreadBinding
import com.scurab.dipho.common.model.Thread

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var items = emptyList<Thread>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemThreadBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(val views: ItemThreadBinding) : RecyclerView.ViewHolder(views.root) {
        fun bind(thread: Thread) = with(views) {
            subject.text = thread.subject
        }
    }
}

