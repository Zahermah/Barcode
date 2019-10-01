package com.example.barcodetest.Koin

import android.view.View
import android.widget.Toast

class UserPresenter(val rep: UserGreeting) {

    fun messageUser(view: View?) {
        Toast.makeText(view?.context, rep.sayGreeting(), Toast.LENGTH_SHORT).show()
    }
}