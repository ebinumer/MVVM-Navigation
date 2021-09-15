package com.whyte.test.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.whyte.test.data.model.SubCat
import com.whyte.test.data.model.DiffUtilCategorData
import com.whyte.test.data.model.DiffUtilSubCatData
import com.whyte.test.databinding.RecyclerCategoryBinding
import com.whyte.test.databinding.RecyclerSubCategoryHomeBinding

class SubCatAdapter( private val mListener: OnSubCategoryonlyClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListItem: MutableList<SubCat> = arrayListOf()

    fun setListItem(mData: List<SubCat>) {
        val diff= DiffUtilSubCatData(this.mListItem,mData)
        val res= DiffUtil.calculateDiff(diff)
        this.mListItem.clear()
        this.mListItem.addAll(mData)
        res.dispatchUpdatesTo(this)
    }

    fun mClearData(){
        this.mListItem.clear()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewholderCategory(
            RecyclerSubCategoryHomeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        mListItem[position]?.let {
            (holder as ViewholderCategory).bind(
                mListener,it
            )

        }
    }

    override fun getItemCount() = mListItem.size

    inner class ViewholderCategory(val mBinding: RecyclerSubCategoryHomeBinding) :
        RecyclerView.ViewHolder(
            mBinding.root
        ) {
        fun bind(mListener: OnSubCategoryonlyClickListener, mData: SubCat) {
            mBinding.data = mData
            mBinding.root.setOnClickListener {
                mListener.onSubCategoryClickListener(
                    mData, adapterPosition
                )
            }
        }
    }
}
interface OnSubCategoryonlyClickListener {
    fun onSubCategoryClickListener(mData:SubCat , position: Int)
}
