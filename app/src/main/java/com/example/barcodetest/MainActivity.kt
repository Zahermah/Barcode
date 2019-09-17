package com.example.barcodetest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.barcodetest.view.CameraScannerFragment
import com.example.barcodetest.view.ItemsViewFragment
import com.example.barcodetest.view.LoginActivity
import com.example.barcodetest.view.ShowFirebaseList
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {

    private val STRING_FRAGMENT_ONE = "fragment_one"
    private val STRING_FRAGMENT_TWO = "fragment_two"
    private val STRING_FRAGMENT_THREE = "fragment_three"
    private var fragment: Fragment? = null
    private val REQUEST_CAMERA_Permission = arrayOf(Manifest.permission.CAMERA)
    private val REQUEST_CODE = 10


    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    fragment = supportFragmentManager.findFragmentByTag(STRING_FRAGMENT_ONE)
                    switchFragment(CameraScannerFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_home -> {
                    fragment = supportFragmentManager.findFragmentByTag(STRING_FRAGMENT_TWO)
                    switchFragment(ItemsViewFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_firebase -> {
                    fragment = supportFragmentManager.findFragmentByTag(STRING_FRAGMENT_THREE)
                    switchFragment(ShowFirebaseList())
                    return@OnNavigationItemSelectedListener true
                }
            }
            fragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_holder, it).commit()
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.menu.findItem(R.id.navigation_dashboard).setChecked(true)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun switchFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        onPause()
        fragmentTransaction.replace(R.id.fragment_holder, fragment)
        onResume()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (allPermissionsGranted()) {
                switchFragment(CameraScannerFragment())
            } else {
                Toast.makeText(this, "Permissions not granted by user", Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }

    private fun allPermissionsGranted() = REQUEST_CAMERA_Permission.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


}