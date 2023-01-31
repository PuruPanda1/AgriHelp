package com.system.agrihelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.system.agrihelp.databinding.ActivityMainBinding
import com.system.agrihelp.ui.bottomSheet.CreateNewBottomSheet
import com.system.agrihelp.utility.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLocale()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

//        val appBarConfiguration = AppBarConfiguration(binding.bottomNavigationView.menu)
//        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.createNew) {
                val bottomSheet = CreateNewBottomSheet()
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
                false
            } else {
                NavigationUI.onNavDestinationSelected(item, navController)
                true
            }
        }

        binding.bottomNavigationView.setOnItemReselectedListener { item ->
            if (item.itemId == R.id.createNew) {
                val bottomSheet = CreateNewBottomSheet()
                bottomSheet.show(supportFragmentManager, bottomSheet.tag)
            } else {
                val reselectedDestinationId = item.itemId
                navController.popBackStack(reselectedDestinationId, inclusive = false)
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.webViewFragment || destination.id == R.id.commentFragment) {
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

    }

    private fun setLocale() {
        val sharedPref = SharedPref(this)
        val lang = sharedPref.getLanguage()
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }

}