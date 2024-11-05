package com.github.callmephil1.crypt.di

import android.content.Context
import com.github.callmephil1.crypt.data.PasswordGenerator
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun appModule(context: Context): KoinAppDeclaration = {
    androidLogger()
    androidContext(context)

    modules(
        androidServices,
        databaseModule,
        managerModule,
        viewModelModule,
        module {
            factoryOf(::PasswordGenerator)
        }
    )
}