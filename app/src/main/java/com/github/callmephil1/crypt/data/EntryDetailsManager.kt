package com.github.callmephil1.crypt.data

import com.github.callmephil1.crypt.data.entities.SecretEntry

class EntryDetailsManager(
    private val cryptDatabase: CryptDatabase
) {
    var id: Int = 0
    var issuer: String = ""
    var label: String = ""
    var qrCodeSecret = ""
    var secret: String = ""

    fun clearEntry() {
        id = 0
        issuer = ""
        label = ""
        qrCodeSecret = ""
        secret = ""
    }

    suspend fun loadEntry(id: Int) {
        val entry = cryptDatabase.secretEntryDao().get(id)
        entry?.let {
            this.id = it.id
            label = it.label
            qrCodeSecret = it.qrCodeSecret
            secret = it.secret
            issuer = it.issuer
        }
    }

    suspend fun saveEntry() {
        cryptDatabase.secretEntryDao().upsertSecretEntry(
            SecretEntry(
                id = id,
                label = label,
                qrCodeSecret = qrCodeSecret,
                secret = secret,
                issuer = issuer
            )
        )
    }
}