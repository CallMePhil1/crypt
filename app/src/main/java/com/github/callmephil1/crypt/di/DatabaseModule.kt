package com.github.callmephil1.crypt.di

import android.util.Log
import androidx.room.Room
import com.github.callmephil1.crypt.BuildConfig
import com.github.callmephil1.crypt.data.CryptDatabase
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val databaseModule = module {
    // Room
    single<CryptDatabase>(named("impl")) {
        try {
            Room
                .databaseBuilder(
                    get(),
                    CryptDatabase::class.java,
                    BuildConfig.DATABASE_FILE
                )
                .openHelperFactory(SupportOpenHelperFactory("test".toByteArray()))
                .build()
        } catch (e: Exception) {
            Log.d("CryptDatabase", e.message!!)
            throw e
        }
    }
    single<CryptDatabase>(named("inmemory")) {
        Room.inMemoryDatabaseBuilder(get(), CryptDatabase::class.java).build()
    }
}