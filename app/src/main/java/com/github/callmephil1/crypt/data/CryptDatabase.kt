package com.github.callmephil1.crypt.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.callmephil1.crypt.data.dao.SecretEntryDao
import com.github.callmephil1.crypt.data.entities.SecretEntry

@Database(entities = [SecretEntry::class], version = 1)
abstract class CryptDatabase : RoomDatabase() {
    abstract fun secretEntryDao(): SecretEntryDao
}
