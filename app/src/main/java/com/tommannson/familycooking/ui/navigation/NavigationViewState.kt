package com.tommannson.familycooking.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.navigator.Navigator

@Stable
class NavigationViewState {

    private lateinit var navigator: Navigator

    val rootNavigator: Navigator get() = navigator

    fun configure(navigator: Navigator) {
        this.navigator = navigator
    }
}

@Composable
fun rememberNavigationViewState(): NavigationViewState {
    return remember(Unit) {
        NavigationViewState()
    }
}


val LocalRootNavigator =
    staticCompositionLocalOf<NavigationViewState> { error("NO local provider for") }

@Composable
fun NavigationHolder(
    navigationState: NavigationViewState = rememberNavigationViewState(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalRootNavigator provides navigationState) {
        content()
    }
}

@Composable
fun NavigatorChild(content: @Composable (Navigator) -> Unit) {
    val navigator = LocalRootNavigator.current
    content(navigator.rootNavigator)
}
