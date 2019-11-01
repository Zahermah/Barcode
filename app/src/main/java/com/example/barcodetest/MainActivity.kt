package com.example.barcodetest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.barcodetest.Koin.appModule
import com.example.barcodetest.view.CameraScannerFragment
import com.example.barcodetest.view.FirebaseListFragment
import com.example.barcodetest.view.ItemsViewFragment
import com.example.barcodetest.view.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {
    private var fragment: Fragment? = null
    private val REQUEST_CAMERA_Permission = arrayOf(Manifest.permission.CAMERA)
    private val REQUEST_CODE = 10

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    requestPermissions(REQUEST_CAMERA_Permission, REQUEST_CODE)
                    switchFragment(CameraScannerFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_home -> {
                    switchFragment(ItemsViewFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_firebase -> {
                    switchFragment(FirebaseListFragment())
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
        navView.menu.findItem(R.id.navigation_dashboard).isChecked = true

        startKoin {
            androidContext(this@MainActivity)
            androidLogger()
            modules(appModule)
        }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (allPermissionsGranted()) {
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by user, Closing app",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUEST_CAMERA_Permission.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }
}