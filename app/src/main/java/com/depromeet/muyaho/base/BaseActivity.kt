package com.depromeet.muyaho.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

abstract class BaseActivity<T : ViewDataBinding, R : BaseViewModel<A>, A : Action>(
) : AppCompatActivity() {
    protected lateinit var binding: T

    abstract val layoutResId: Int
    abstract val vm: R

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this

        observeViewModel()
    }

    protected open fun observeViewModel() {
        Log.d("!@# vm", vm.toString())
        if (vm is NoViewModel) return

        lifecycleScope.launchWhenStarted {
            vm.actionReceiver.collect { action ->
                Log.d("!@#", action.toString())
                observeActionCommand(action)
            }
        }
    }

    protected open fun observeActionCommand(action: A) = Unit

}