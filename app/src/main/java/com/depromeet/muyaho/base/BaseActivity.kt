package com.depromeet.muyaho.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

abstract class BaseActivity<T : ViewDataBinding, R : BaseViewModel<A>, A : Action> :
    AppCompatActivity() {
    val TAG = this.toString()
    protected lateinit var binding: T

    abstract val layoutResId: Int
    abstract val vm: R

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this
        if (vm !is NoViewModel) binding.setVariable(BR.vm, vm)

        observeViewModel()
    }

    protected open fun observeViewModel() {
        if (vm is NoViewModel) return

        lifecycleScope.launchWhenStarted {
            vm.actionReceiver.collect { action ->
                observeActionCommand(action)
            }
        }
    }

    protected open fun observeActionCommand(action: A) = Unit

}