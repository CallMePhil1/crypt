package com.github.callmephil1.crypt.di

import androidx.room.Room
import com.github.callmephil1.crypt.data.CryptDatabase
import com.github.callmephil1.crypt.data.DatabaseManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

val databaseModule = module {
    // Room
    single<CryptDatabase>(named("impl")) {
        get<DatabaseManager>().database
    }
    single<CryptDatabase>(named("inmemory")) {
        Room.inMemoryDatabaseBuilder(get(), CryptDatabase::class.java).build()
    }
}