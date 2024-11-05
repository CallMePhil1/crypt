package com.github.callmephil1.crypt.di

import com.github.callmephil1.crypt.data.CryptDatabase
import com.github.callmephil1.crypt.data.clipboard.Clipboard
import com.github.callmephil1.crypt.ui.compose.changepassword.ChangePasswordViewModel
import com.github.callmephil1.crypt.ui.compose.entries.EntriesScreenViewModel
import com.github.callmephil1.crypt.ui.compose.newentry.EntryDetailsViewModel
import com.github.callmephil1.crypt.ui.compose.login.LoginViewModel
import com.github.callmephil1.crypt.ui.compose.qrscan.QrScanViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ChangePasswordViewModel(
            get(named("impl"))
        )
    }
    viewModel {
        EntriesScreenViewModel(
            get<Clipboard>(named("impl")),
            get<CryptDatabase>(named("impl")),
            get(),
            get(),
            get()
        )
    }

    viewModel {
        EntryDetailsViewModel(
            get<Clipboard>(named("impl")),
            get(),
            get(),
            get()
        )
    }
    viewModelOf(::LoginViewModel)
    viewModelOf(::QrScanViewModel)
}