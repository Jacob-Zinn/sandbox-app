package com.creators.sandbox.ui

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.creators.sandbox.R
import com.creators.sandbox.databinding.ActivityMainBinding
import com.creators.sandbox.models.Doctor
import com.creators.sandbox.viewmodels.MainViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber


@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class MainActivity : BaseActivity(), NavigationBarView.OnItemReselectedListener {

    private lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("zzz Please at least make it here")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragments_container) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBar()
        setupBottomNavigationView()

        subscribeObservers()
    }

    override fun onResume() {
        super.onResume()
        // refreshing global data
        viewModel.refreshData()
//        viewModel.doctorRef.addListenerForSingleValueEvent(viewModel.eventListener)


        // clearing all notifications
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }



    private fun setupActionBar() {
        setSupportActionBar(binding.toolBar)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.mainFragment, R.id.accountFragment)
        )
        binding.toolBar.setupWithNavController(navController, appBarConfiguration)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = ""
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnItemReselectedListener(this)
    }

    private fun subscribeObservers(){

    }

    override fun expandAppBar() {
        findViewById<AppBarLayout>(R.id.app_bar).setExpanded(true)
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        binding.bottomNavigationView.performHapticFeedback(
            HapticFeedbackConstants.VIRTUAL_KEY,
            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
        )
        when (item.title){
            getString(R.string.analytics) -> {
                while (navController.currentDestination?.id ?: R.id.mainFragment != R.id.mainFragment) {
                    navController.popBackStack()
                }
            }

            getString(R.string.account) -> {
                while (navController.currentDestination?.id ?: R.id.accountFragment != R.id.accountFragment) {
                    navController.popBackStack()
                }
            }
        }
    }

}