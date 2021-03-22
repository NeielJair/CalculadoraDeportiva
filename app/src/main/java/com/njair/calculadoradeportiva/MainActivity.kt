package com.njair.calculadoradeportiva

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
/*import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController*/
import com.njair.calculadoradeportiva.ui.fragments.SetsFragment
import com.njair.calculadoradeportiva.ui.fragments.TimesFragment
import com.njair.calculadoradeportiva.ui.fragments.WeightsFragment

class MainActivity : AppCompatActivity() {

    private val setsFragment = SetsFragment()
    private val timesFragment = TimesFragment()
    private val weightsFragment = WeightsFragment()
    private var activeFragment: Fragment = setsFragment
    private val fragmentMap = mapOf(
        R.id.navigation_sets to setsFragment,
        R.id.navigation_times to timesFragment,
        R.id.navigation_weights to weightsFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        var fT = supportFragmentManager.beginTransaction()
        for (fragment in fragmentMap.values) {
            fT
                .add(R.id.nav_host_fragment, fragment, fragment.javaClass.name)
                .hide(fragment)
        }
        fT.show(activeFragment).commit()

        navView.setOnNavigationItemSelectedListener { item ->
            val fragment: Fragment? = fragmentMap[item.itemId]
            if (fragment == null)
                false
            else {
                fT = supportFragmentManager.beginTransaction()
                if (activeFragment !== fragment)
                    fT.setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                fT.hide(activeFragment).show(fragment).commit()
                activeFragment = fragment
                true
            }
        }


        /*val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_sets, R.id.navigation_weights))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/
    }
}