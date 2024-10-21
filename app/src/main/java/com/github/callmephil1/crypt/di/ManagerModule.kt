package com.github.callmephil1.crypt.di

import com.github.callmephil1.crypt.data.DatabaseManager
import com.github.callmephil1.crypt.data.EntryDetailsManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

val managerModule = module {
    single { EntryDetailsManager(get(named("impl"))) }

    single {
        DatabaseManager(get())
    }
}