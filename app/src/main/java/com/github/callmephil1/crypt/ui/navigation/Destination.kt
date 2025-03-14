package com.github.callmephil1.crypt.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    object Authentication : Destination()
    @Serializable
    object Entries : Destination()
    @Serializable
    class EntryDetails(val id: Int) : Destination()
    @Serializable
    object QrScanner : Destination()
}