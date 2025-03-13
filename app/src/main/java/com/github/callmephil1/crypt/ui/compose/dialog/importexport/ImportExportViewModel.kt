package com.github.callmephil1.crypt.ui.compose.dialog.importexport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.callmephil1.crypt.data.CryptDatabase
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class ImportExportViewModel(
    private val cryptDatabase: CryptDatabase
) : ViewModel() {

    private fun decrypt(key: String, content: ByteArray): ByteArray {
        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        return cipher.doFinal(content)
    }

    private fun encrypt(key: String, content: ByteArray): ByteArray {
        val secretKey = SecretKeySpec(key.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher.doFinal(content)
    }

    fun export(filePath: String, encryptionKey: String) {
        viewModelScope.launch {
            val secrets = cryptDatabase.secretEntryDao().getAll()
            val cipherText = encrypt(encryptionKey, Json.encodeToString(secrets).encodeToByteArray())
            File(filePath).writeBytes(cipherText)
        }
    }

    fun import(filePath: String, encryptionKey: String) {

    }
}