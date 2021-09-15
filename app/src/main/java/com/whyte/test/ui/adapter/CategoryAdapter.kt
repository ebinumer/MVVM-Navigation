package com.whyte.test.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.whyte.test.data.model.CategoryDataModel
import com.whyte.test.data.model.DiffUtilCategorData
import com.whyte.test.data.model.SubCat
import com.whyte.test.databinding.RecyclerCategoryBinding
import timber.log.Timber

class CategoryAdapter( private val mListener: OnCategoryonlyClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListItem: MutableList<CategoryDataModel> = arrayListOf()

    fun setListItem(mData: List<CategoryDataModel>) {
        val diff= DiffUtilCategorData(this.mListItem,mData)
        val res= DiffUtil.calculateDiff(diff)
        this.mListItem.clear()
        this.mListItem.addAll(mData)
        res.dispatchUpdatesTo(this)
    }

    fun mClearData(){
        this.mListItem.clear()
        notifyDataSetChanged()
    }

    val mOnSubCategoryonlyClickListener = object : OnSubCategoryonlyClickListener {
        override fun onSubCategoryClickListener(mData: SubCat, position: Int) {

        }
    }
    private var mSubCatAdapter: SubCatAdapter = SubCatAdapter(mOnSubCategoryonlyClickListener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewholderCategory(
            RecyclerCategoryBinding.inflate(
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

    inner class ViewholderCategory(val mBinding: RecyclerCategoryBinding) :
        RecyclerView.ViewHolder(
            mBinding.root
        ) {
        fun bind(mListener: OnCategoryonlyClickListener, mData: CategoryDataModel) {
            mBinding.recyclerSubCategories.adapter=mSubCatAdapter
            mBinding.data = mData
            Timber.e("clicked${mData.subCat.isNullOrEmpty()}")
            if(mData.subCat.isNullOrEmpty()){
                Timber.e("inside")
                mSubCatAdapter.setListItem(mData.subCat)
            }

            mBinding.root.setOnClickListener {
                Timber.e("clicked${mData}")

                mListener.onCategoryClickListener(
                    mData, adapterPosition
                )
            }
        }
    }
}
interface OnCategoryonlyClickListener {
    fun onCategoryClickListener(mData: CategoryDataModel, position: Int)
}