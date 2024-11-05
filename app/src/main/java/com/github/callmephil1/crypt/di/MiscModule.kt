package com.github.callmephil1.crypt.di

import com.github.callmephil1.crypt.data.PasswordGenerator
import com.github.callmephil1.crypt.ui.compose.dialog.dialoghost.DialogController
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val miscModule = module {
    singleOf(::DialogController)
    factoryOf(::PasswordGenerator)
}