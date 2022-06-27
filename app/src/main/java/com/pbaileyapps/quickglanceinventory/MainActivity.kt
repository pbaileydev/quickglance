package com.pbaileyapps.quickglanceinventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolBar = findViewById<Toolbar>(R.id.toolbar_two)
        setSupportActionBar(toolBar)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavView)
        val navigationController = findNavController(R.id.fragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.home2,R.id.shop))
        //Actually makes the bottom nav work.
        //This is all you really need.
        firebaseAuth = FirebaseAuth.getInstance()
        bottomNav.setupWithNavController(navigationController)
        setupActionBarWithNavController(navigationController,appBarConfiguration)
        //This makes the actionbar use the names of our fragments from our nav graph in our nav controller
        val appBarConfig = AppBarConfiguration(navigationController.graph)
        setupActionBarWithNavController(navigationController,appBarConfiguration)
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            // Note that system bars will only be "visible" if none of the
            // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                // TODO: The system bars are visible. Make any desired
                // adjustments to your UI, such as showing the action bar or
                // other navigational controls.
            } else {
                window.decorView.apply {
                    // Hide both the navigation bar and the status bar.
                    // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
                    // a general rule, you should design your app to hide the status bar whenever you
                    // hide the navigation bar.
                    systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
                }
            }
        }

    }


    override fun onBackPressed() {
        super.onBackPressed()
        firebaseAuth.signOut()
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)

        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}