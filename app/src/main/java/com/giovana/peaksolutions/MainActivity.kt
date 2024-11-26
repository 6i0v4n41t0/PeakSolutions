package com.giovana.peaksolutions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.giovana.peaksolutions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //toolbar
    private lateinit var appBarConfiguration: AppBarConfiguration

    //navegação
    private lateinit var navController: NavController

    //binding
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    private val menuScreens = listOf(
        R.id.produtosFragment,
        R.id.vendasFragment,
        R.id.producaoFragment,
        R.id.materiaisFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //configura binding
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configura navegaçao e toolbar
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentCV.id) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            controller.currentDestination?.let {
                if (it.id in menuScreens) {
                    destination.parent?.setStartDestination(it.id)
                }
                if (it.id == R.id.entrarFragment) {
                    binding.toolbar.visibility = View.GONE
                } else {
                    binding.toolbar.visibility = View.VISIBLE
                }
            }
        }
    }
}