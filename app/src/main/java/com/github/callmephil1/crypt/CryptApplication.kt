package com.github.callmephil1.crypt

import android.app.Application
import com.github.callmephil1.crypt.di.androidServices
import com.github.callmephil1.crypt.di.databaseModule
import com.github.callmephil1.crypt.di.managerModule
import com.github.callmephil1.crypt.di.miscModule
import com.github.callmephil1.crypt.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CryptApplication)

            modules(
                androidServices,
                databaseModule,
                miscModule,
                managerModule,
                viewModelModule
            )
        }
    }
}