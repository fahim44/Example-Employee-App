package com.fahim.example_employee_app.ui.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fahim.example_employee_app.R
import android.view.View
import dagger.android.support.DaggerAppCompatActivity




class TabPageActivity : DaggerAppCompatActivity() {

    companion object {
        private val TOP_LEVEL_DESTINATIONS = setOf(
            R.id.navigation_list,
            R.id.navigation_search,
            R.id.navigation_add_remove
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_page)
        //supportActionBar?.hide()
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            TOP_LEVEL_DESTINATIONS
        )

        navController.addOnDestinationChangedListener { _,destination,_ ->
            val isTopLevelDestination = TOP_LEVEL_DESTINATIONS.contains(destination.id)
            if(isTopLevelDestination) {
                supportActionBar?.hide()
                navView.visibility = View.VISIBLE
            }
            else {
                supportActionBar?.show()
                navView.visibility = View.GONE
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onBackPressed() {
        if(!findNavController(R.id.nav_host_fragment).navigateUp())
            super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp() || super.onSupportNavigateUp()
    }
}
