package com.example.barcodetest

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.barcodetest.view.CameraScannerFragment
import com.example.barcodetest.view.ItemsViewFragment
import com.example.barcodetest.view.LoginActivity
import com.example.barcodetest.view.showfirebasetest
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    private var fragment: Fragment? = null
    private val REQUEST_CAMERA = 1

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(ItemsViewFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                switchFragment(CameraScannerFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_firebase -> {
                switchFragment(showfirebasetest())
                return@OnNavigationItemSelectedListener true
            }
        }
        fragment?.let { supportFragmentManager.beginTransaction().replace(R.id.fragment_holder, it).commit() }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_holder, fragment)
        fragmentTransaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.Login_User -> {
                startActivity(Intent(this, LoginActivity::class.java))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}