package com.example.readsaga.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.readsaga.R
import com.example.readsaga.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController
    internal lateinit var binding : ActivityMainBinding
    var userId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = (supportFragmentManager.findFragmentById(R.id.fragmentHost) as NavHostFragment).navController
        NavigationUI.setupActionBarWithNavController(this, navController)
        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> binding.bottomNav.visibility = View.GONE
                R.id.registerFragment -> binding.bottomNav.visibility = View.GONE
                else -> binding.bottomNav.visibility = View.VISIBLE
            }
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.bookDetailFragment -> supportActionBar?.setDisplayHomeAsUpEnabled(true)
                R.id.registerFragment -> supportActionBar?.setDisplayHomeAsUpEnabled(true)
                else -> supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
        return navController.navigateUp()
    }
}
