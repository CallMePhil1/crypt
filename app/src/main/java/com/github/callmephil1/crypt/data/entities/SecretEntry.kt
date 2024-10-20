package com.github.callmephil1.crypt.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SecretEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    val qrCodeSecret: String,
    val secret: String,
    val issuer: String
)