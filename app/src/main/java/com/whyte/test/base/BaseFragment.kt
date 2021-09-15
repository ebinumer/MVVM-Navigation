package com.whyte.test.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.whyte.test.BR
import com.whyte.test.R
import com.whyte.test.utils.Constants
import com.whyte.test.utils.SessionManager
import com.whyte.test.utils.extentions.showToast
import org.koin.android.ext.android.inject
import timber.log.Timber

abstract class BaseFragment <Binding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment() {
    protected abstract val mViewModel: ViewModel
    protected lateinit var bindingObject: Binding
    protected val mSessionManager by inject<SessionManager>()

    fun getViewDataBinding(): Binding = bindingObject

    fun getViewModelObject(): ViewModel = mViewModel

    var appCompatActivity: AppCompatActivity? = null


    open fun drawoverStatusBar(): Boolean = false

    open fun getStatusBarColorForFragment(): Int = R.color.purple_700



    open fun setScreenNotTouchable() {
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    open fun setScreenTouchable() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appCompatActivity = activity as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        if (drawoverStatusBar()) {
//            activity?.window?.apply {
//                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//                statusBarColor = ContextCompat.getColor(requireContext(), getStatusBarColorForFragment())
//            }
//        }
        bindingObject = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        return bindingObject.root
    }

    open fun moveToBackStack(){
        activity?.onBackPressed()!!
    }


    open fun passDataToPreviousActivity(intent: Intent, resultCode:Int=1234){
        activity?.let {
            it.setResult(resultCode,intent)
            moveToFinish()
        }
    }

    open fun moveToFinish(){
        activity?.finish()
    }

    /**
     * Get layout resource id which inflate in onCreateView.
     */
    @LayoutRes
    abstract fun getLayoutResId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doDataBinding()
    }

    /**
     * Do your other stuff in init after binding layout.
     */
    abstract fun init()

    private fun doDataBinding() {
//        bindingObject.lifecycleOwner =
//            viewLifecycleOwner // it is extra if you want to set life cycle owner in binding
        // Here your viewModel and binding variable imlementation
        bindingObject.setVariable(
            BR.viewmodel,
            mViewModel
        )  // In all layout the variable name should be "viewModel"
        bindingObject.executePendingBindings()
        init()
        observeViewModelBaseItems()
    }

    private fun observeViewModelBaseItems() {
        with(mViewModel) {
            showToastMessage.observe(viewLifecycleOwner, {
                showToast(it)
            })
            showToastMessageFromResources.observe(viewLifecycleOwner, {
                showToast(getString(it))
            })
            setScreenNotTouchable.observe(viewLifecycleOwner, {
                if (it) {
                    setScreenNotTouchable()
                } else {
                    setScreenTouchable()
                }
            })
        }
    }

    fun hidekeyboard(view: View){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

}