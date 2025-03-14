package com.github.callmephil1.crypt.ui.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface NavigationHelper {
    val navigationFlow: StateFlow<Destination?>

    fun navigate(destination: Destination)
}

class NavigationHelperImpl() : NavigationHelper {
    private val _navigationFlow: MutableStateFlow<Destination?> = MutableStateFlow(null)

    override val navigationFlow: StateFlow<Destination?>
        get() = _navigationFlow.asStateFlow()

    override fun navigate(destination: Destination) {
        _navigationFlow.update { destination }
    }
}