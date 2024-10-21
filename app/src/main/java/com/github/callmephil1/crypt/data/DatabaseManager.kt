package com.github.callmephil1.crypt.data

import android.content.Context
import androidx.room.Room
import com.github.callmephil1.crypt.BuildConfig
import net.zetetic.database.sqlcipher.SQLiteDatabase
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory

class DatabaseManager(
    private val context: Context
) {
    lateinit var database: CryptDatabase
        private set

    fun init(password: String): Result<Unit> = kotlin.runCatching {
        val databaseFile = context.getDatabasePath(BuildConfig.DATABASE_FILE)
        SQLiteDatabase.openOrCreateDatabase(
            databaseFile,
            password,
            null,
            null
        )

        database = Room
            .databaseBuilder(
                context,
                CryptDatabase::class.java,
                BuildConfig.DATABASE_FILE
            )
            .openHelperFactory(SupportOpenHelperFactory(password.toByteArray()))
            .build()
    }
}