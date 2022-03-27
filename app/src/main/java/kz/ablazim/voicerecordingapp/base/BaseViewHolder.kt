package com.ablazim.oneaviationandroid.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(
    view: View,
    private val onClickListener: ((T) -> Unit)? = null
) : RecyclerView.ViewHolder(view) {
    
    fun bind(item: T) {
        onBind(item)
        onClickListener?.let { listener ->
            itemView.setOnClickListener { listener(item) }
        }
    }
    
    protected open fun onBind(item: T) = Unit
    
    open fun onViewRecycled() = Unit
}