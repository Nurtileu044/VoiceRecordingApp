package com.ablazim.oneaviationandroid.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {
    
    protected val data = mutableListOf<T>()
    
    override fun getItemCount(): Int = data.size
    
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) = holder.bind(data[position])
    
    override fun onViewRecycled(holder: BaseViewHolder<T>) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }
    
    open fun getItem(position: Int): T = data[position]
    
    fun isEmpty() = data.size == 0
    
    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }
    
    open fun setItems(items: List<T>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }
}