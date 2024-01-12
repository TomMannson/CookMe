package com.tommannson.familycooking.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tommannson.familycooking.navigation.TopLevelDestination
import com.tommannson.familycooking.search.navigation.CMNavigationRail
import com.tommannson.familycooking.search.navigation.CMNavigationRailItem
import com.tommannson.paymentorganizer.components.searchbar.AppSearchBar
import com.tommannson.paymentorganizer.theme.withAlpha

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxSize()) {
        Navigation()
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceDim.withAlpha(.5f))
        ) {

            AppSearchBar(
                modifier = Modifier.padding(16.dp),
                onSearchActivated = {}
            )
        }
    }
}


@Preview
@Composable
private fun SearchScreenPreview() {

}

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    CMNavigationRail(
        modifier = modifier,
        header = { },
    ) {
        val destination = TopLevelDestination.CookBook
        CMNavigationRailItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = destination.unselectedIcon,
                    contentDescription = null,
                )
            },
            selectedIcon = {
                Icon(
                    imageVector = destination.selectedIcon,
                    contentDescription = null,
                )
            },
            label = { Text(stringResource(destination.iconTextId)) },
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun NavigationPreview() {
    Navigation(Modifier)
}
