package com.depromeet.muyaho.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

abstract class BaseFragment<T : ViewDataBinding, R : BaseViewModel<A>, A : Action> : Fragment() {
    protected lateinit var binding: T

    abstract val layoutResId: Int
    abstract val vm: R

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (vm !is NoViewModel) binding.setVariable(BR.vm, vm)
        observeViewModel()
    }

    protected open fun observeViewModel() {
        if (vm is NoViewModel) return

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            vm.actionReceiver.collect { action ->
                observeActionCommand(action)
            }
        }
    }

    protected open fun observeActionCommand(action: A) = Unit
}