package com.depromeet.muyaho.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.depromeet.muyaho.R
import com.depromeet.muyaho.base.BaseActivity
import com.depromeet.muyaho.databinding.ActivityMainBinding
import com.depromeet.muyaho.ui.addstock.AddStockActivity
import com.depromeet.muyaho.ui.modifystock.ModifyStockActivity
import com.depromeet.muyaho.util.setupWithNavController
import com.depromeet.muyaho.widget.HomeFABView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel, MainViewModel.ViewAction>() {
    override val layoutResId: Int
        get() = R.layout.activity_main
    override val vm: MainViewModel by viewModels()

    private var currentNavController: LiveData<NavController>? = null

    override fun observeActionCommand(action: MainViewModel.ViewAction) {
        when (action) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = vm

        if (savedInstanceState == null) {
            setUpBottomNavigationBar()
        }

        binding.viewFab.setSubBtnClickListener(object : HomeFABView.SubBtnClickListener{
            override fun OnAddBtnClick() {
                Intent(this@MainActivity, AddStockActivity::class.java).also {
                    startActivity(it)
                }

                if (binding.viewFab.isClicked) {
                    binding.viewFab.toggle()
                }
            }

            override fun OnModifyBtnClick() {
                Intent(this@MainActivity, ModifyStockActivity::class.java).also {
                    startActivity(it)
                }

                if (binding.viewFab.isClicked) {
                    binding.viewFab.toggle()
                }
            }
        })
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNavigationBar()
    }

    override fun onBackPressed() {
        if (binding.viewFab.isClicked) {
            binding.viewFab.toggle()
            return
        }
        super.onBackPressed()
    }

    private fun setUpBottomNavigationBar() {
        val navGraphIds = listOf(
                R.navigation.nav_home,
                R.navigation.nav_calc,
                R.navigation.nav_board,
                R.navigation.nav_my
        )

        val controller = binding.bottomNavigation.setupWithNavController(
                navGraphIds, supportFragmentManager, R.id.nav_host, intent
        )
        currentNavController = controller

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    binding.viewFab.visibility = View.VISIBLE
                }
                else -> {
                    binding.viewFab.visibility = View.GONE
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}
