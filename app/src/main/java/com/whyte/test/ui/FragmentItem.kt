package com.whyte.test.ui

import com.whyte.test.R
import com.whyte.test.base.BaseFragment
import com.whyte.test.data.model.ListModelData
import com.whyte.test.databinding.FragmentItemlistBinding
import com.whyte.test.ui.adapter.ListAdapter
import com.whyte.test.ui.adapter.ListlickListener
import com.whyte.test.viewmodelimpl.ViewmodelItemImpl
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class FragmentItem : BaseFragment<FragmentItemlistBinding, ViewmodelItemImpl>() {
    private val mViemodel by viewModel<ViewmodelItemImpl>()
    {
        parametersOf(
            arguments?.let {
                FragmentItemArgs.fromBundle(it).catId
            }
        )
    }
    override val mViewModel: ViewmodelItemImpl
        get() = mViemodel

    override fun getLayoutResId(): Int = R.layout.fragment_itemlist
    val mListner = object : ListlickListener {
        override fun onListlick(mData: ListModelData) {

        }

    }

    private var mListAdapter = ListAdapter(mListner)
    override fun init() {
        getViewDataBinding().apply {
            recyclerList.adapter = mListAdapter
        }
        observeItems()
    }

    private fun observeItems() {
        with(getViewModelObject()) {
            apiResponseMlistData.observe(viewLifecycleOwner, {
                Timber.e("it==$it")
                mListAdapter.submitList(it)

            })
        }
    }


}