package com.playlab

import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.playlab.marvelcomicsinfo.model.Comic

class PagingDataTestUtils {
    companion object {
        fun PagingData<Comic>.myHelperTransformFunction(title: String? = ""): PagingData<Comic> {
            return if (title.isNullOrEmpty()) this
            else this.filter { item -> item.title == title }
        }
    }

    class NoopListCallback : ListUpdateCallback {
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
    }

    class MyDiffCallback : DiffUtil.ItemCallback<Comic>() {
        override fun areItemsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Comic, newItem: Comic): Boolean {
            return oldItem.id == newItem.id
        }
    }
}