package com.github.callmephil1.crypt.ui.compose.dialog.changepassword

import androidx.lifecycle.ViewModel
import com.github.callmephil1.crypt.data.CryptDatabase
import net.zetetic.database.sqlcipher.SQLiteDatabase

class ChangePasswordViewModel(
    private val cryptDatabase: CryptDatabase,
) : ViewModel() {
    fun changePassword(oldPassword: String, newPassword: String) {
        if (oldPassword == newPassword)
            return

        val database = cryptDatabase.openHelper.writableDatabase as (SQLiteDatabase)
        database.changePassword(newPassword)

    }
}