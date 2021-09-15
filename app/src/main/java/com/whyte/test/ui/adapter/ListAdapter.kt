package com.whyte.test.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.whyte.test.data.model.ListModelData
import com.whyte.test.data.model.ListModelDataDiffUtil
import com.whyte.test.databinding.RecyclerItemListBinding

class ListAdapter (
    val mListener: ListlickListener
) : PagedListAdapter<ListModelData, RecyclerView.ViewHolder>(
    ListModelDataDiffUtil
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderList(
            RecyclerItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as ViewHolderList).bindItem(
                it, mListener
            )
        }
    }
    inner class ViewHolderList(val mBinding: RecyclerItemListBinding) :
        RecyclerView.ViewHolder(
            mBinding.root
        ) {
        fun bindItem(mData: ListModelData, mListenerGet: ListlickListener) {
            mBinding.apply {
                data = mData
                root.setOnClickListener {
                    mListenerGet.onListlick(mData)
                }
            }
        }
    }

}


interface ListlickListener {
    fun onListlick(mData: ListModelData)
}