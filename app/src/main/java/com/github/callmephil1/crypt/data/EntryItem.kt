package com.github.callmephil1.crypt.data

data class EntryItem(
    val id: Int,
    val name: String,
    var secretCountDown: Float = 0f,
    var otpCountDown: Float= 0f,
    var otpRefreshCountDown: Float = 0f,
    val otpSecret: String = "",
    val otpText: String
)