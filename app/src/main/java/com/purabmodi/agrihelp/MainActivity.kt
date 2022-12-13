package com.purabmodi.agrihelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.purabmodi.agrihelp.databinding.ActivityMainBinding
import com.purabmodi.agrihelp.ui.bottomSheet.CreateNewBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(binding.bottomNavigationView.menu)
        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            if(item.itemId == R.id.createNew){
                val bottomSheet = CreateNewBottomSheet()
                bottomSheet.show(supportFragmentManager,bottomSheet.tag)
                false
            }else{
                NavigationUI.onNavDestinationSelected(item, navController)
                true
            }
        }

        binding.bottomNavigationView.setOnItemReselectedListener { item ->
            if(item.itemId == R.id.createNew){
                val bottomSheet = CreateNewBottomSheet()
                bottomSheet.show(supportFragmentManager,bottomSheet.tag)
            }
            else{
                val reselectedDestinationId = item.itemId
                navController.popBackStack(reselectedDestinationId, inclusive = false)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return super.onSupportNavigateUp()
    }

}