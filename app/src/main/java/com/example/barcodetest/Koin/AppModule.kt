package com.example.barcodetest.Koin

import com.example.barcodetest.Koin.Greeting
import com.example.barcodetest.Koin.UserGreeeting
import com.example.barcodetest.Koin.UserPresenter
import org.koin.dsl.module


val appModule = module {

    single<UserGreeeting> { Greeting() }

    factory { UserPresenter(get()) }

}