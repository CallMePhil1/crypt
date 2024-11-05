package com.github.callmephil1.crypt.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.KoinAppDeclaration

fun appModule(context: Context): KoinAppDeclaration = {
    androidLogger()
    androidContext(context)

    modules(
        androidServices,
        databaseModule,
        miscModule,
        managerModule,
        viewModelModule
    )
}