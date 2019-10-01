package com.example.barcodetest.Koin

import org.koin.dsl.module


val appModule = module {

    single<UserGreeting> { Greeting() }

    factory { UserPresenter(get()) }

}