package com.tommannson.familycooking.search.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CMNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.Center){
        Box(modifier = Modifier.height(200.dp)) {
            NavigationRail(
                modifier = modifier,
                containerColor = Color.Transparent,
                contentColor = CMNavigationDefaults.navigationContentColor(),
                header = header,
                content = content,
            )
        }
    }
}

@Composable
fun RowScope.CMNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = CMNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = CMNavigationDefaults.navigationContentColor(),
            selectedTextColor = CMNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = CMNavigationDefaults.navigationContentColor(),
            indicatorColor = CMNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

@Composable
fun CMNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = CMNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = CMNavigationDefaults.navigationContentColor(),
            selectedTextColor = CMNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = CMNavigationDefaults.navigationContentColor(),
            indicatorColor = CMNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}


/**
 * Now in Android navigation default values.
 */
private object CMNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}