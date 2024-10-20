package com.github.callmephil1.crypt.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.github.callmephil1.crypt.data.entities.SecretEntry

@Dao
interface SecretEntryDao {

    @Delete
    suspend fun delete(secretEntry: SecretEntry)

    @Query("SELECT * FROM secretentry WHERE id = :id")
    suspend fun get(id: Int): SecretEntry?

    @Query("SELECT * FROM secretentry")
    suspend fun getAll(): List<SecretEntry>

    @Upsert
    suspend fun upsertSecretEntry(secretEntry: SecretEntry)

}