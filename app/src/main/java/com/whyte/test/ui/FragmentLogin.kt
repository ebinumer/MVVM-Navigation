package com.whyte.test.ui

import com.whyte.test.R
import com.whyte.test.base.BaseFragment
import com.whyte.test.base.StatefulResource
import com.whyte.test.databinding.FragmentLoginBinding
import com.whyte.test.utils.extentions.showToast
import com.whyte.test.utils.naviGateTo
import com.whyte.test.viewmodelimpl.ViewmodelLoginImpl
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FragmentLogin: BaseFragment<FragmentLoginBinding, ViewmodelLoginImpl>()  {

    private val mViemodel by viewModel<ViewmodelLoginImpl>()
    override val mViewModel: ViewmodelLoginImpl
        get() = mViemodel

    override fun getLayoutResId(): Int = R.layout.fragment_login

    override fun init() {

        if (mSessionManager.appOpenStatus){
            moveHome()
        }
        observeItems()
    }
    private fun observeItems() {
        with(getViewModelObject()) {

            apiResponseLogin.observe(this@FragmentLogin, { stateFulRes ->
                if (stateFulRes.state == StatefulResource.State.SUCCESS) {
                    if (stateFulRes.isSuccessful() and (stateFulRes.hasData())) {
                        val data = stateFulRes.getData()
                        data?.let { modellogin ->
                            if (modellogin.status==200) {
                                Timber.e("at 200==${modellogin}")

                                mSessionManager.token=modellogin.data.token
                                mSessionManager.appOpenStatus=true
                                moveHome()
                            } else {
                                showToast("Some Error",false)

                            }
                        }
                    }
                }
            })
        }

    }
    fun moveHome(){
        naviGateTo(
            FragmentLoginDirections.actionLoginToHome(),

            )
    }
}