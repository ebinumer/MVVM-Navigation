package com.whyte.test.di

import com.whyte.test.viewmodelimpl.ViewmodelHomeImp
import com.whyte.test.viewmodelimpl.ViewmodelItemImpl
import com.whyte.test.viewmodelimpl.ViewmodelLoginImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mViewmodel = module {
    viewModel { ViewmodelLoginImpl(get()) }
    viewModel { ViewmodelHomeImp(get()) }
    viewModel {(mData: Int) -> ViewmodelItemImpl(mData,get(),get()) }
}