package com.whyte.test.ui

import android.content.Intent
import com.whyte.test.R
import com.whyte.test.base.BaseFragment
import com.whyte.test.base.StatefulResource
import com.whyte.test.data.model.CategoryDataModel
import com.whyte.test.data.model.SubCat
import com.whyte.test.databinding.FragmentHomeBinding
import com.whyte.test.ui.adapter.CategoryAdapter
import com.whyte.test.ui.adapter.OnCategoryonlyClickListener
import com.whyte.test.ui.adapter.OnSubCategoryonlyClickListener
import com.whyte.test.ui.adapter.SubCatAdapter
import com.whyte.test.utils.extentions.showToast
import com.whyte.test.utils.naviGateTo
import com.whyte.test.viewmodelimpl.ViewmodelHomeImp
import com.yariksoffice.lingver.Lingver
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*

class FragmentHome : BaseFragment<FragmentHomeBinding, ViewmodelHomeImp>() {
    private val mViemodel by viewModel<ViewmodelHomeImp>()
    override val mViewModel: ViewmodelHomeImp
        get() = mViemodel

    override fun getLayoutResId(): Int = R.layout.fragment_home
    val onCategoryClickListener = object : OnCategoryonlyClickListener {
        override fun onCategoryClickListener(mData: CategoryDataModel, position: Int) {
            moveToNext(mData.id)
        }
    }
    private var mAdapterCategory: CategoryAdapter = CategoryAdapter(onCategoryClickListener)


    override fun init() {
        initUI()
        observeItems()
    }

    private fun initUI() {
        getViewDataBinding().apply {
            recyclerCatgry.adapter = mAdapterCategory
            btnLang.setOnClickListener { LangClick() }
        }
    }


    private fun observeItems() {
        with(getViewModelObject()) {

            apiResponseCategory.observe(this@FragmentHome, { stateFulRes ->
                if (stateFulRes.state == StatefulResource.State.SUCCESS) {
                    if (stateFulRes.isSuccessful() and (stateFulRes.hasData())) {
                        val data = stateFulRes.getData()
                        data?.let { modelcategory ->
                            if (modelcategory.status == 200) {
                                Timber.e("at 200==${modelcategory}")
                                mAdapterCategory.setListItem(modelcategory.data)
                            } else {
                                showToast("Some Error", false)
                            }
                        }
                    }
                }
            })
        }

    }

    fun LangClick() {
        if (Locale.getDefault().getLanguage() == "ar") {
            mSessionManager.appLanguage = "en"
            Lingver.getInstance().setLocale(requireContext(), "en")
            GlobalScope.launch {
                delay(500)
                startActivity(
                    Intent(
                        requireContext(),
                        MainActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        } else {
            mSessionManager.appLanguage = "ar"
            Lingver.getInstance().setLocale(requireContext(), "ar")
            GlobalScope.launch {
                delay(500)
                startActivity(
                    Intent(
                        requireContext(),
                        MainActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }
    }
    fun moveToNext(CatId:Int){
        naviGateTo(
            FragmentHomeDirections.actionHomeToItem(CatId),

            )

    }
}