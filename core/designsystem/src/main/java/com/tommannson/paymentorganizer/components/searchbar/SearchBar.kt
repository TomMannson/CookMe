package com.tommannson.paymentorganizer.components.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tommannson.paymentorganizer.components.searchbar.SearchBarDefaults.IconPadding2

@Composable
fun AppSearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Search",
    onSearchActivated: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.surface)
            .clickable(enabled = true, onClick = onSearchActivated),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "search",
            modifier = Modifier.padding(IconPadding2)
        )
        Text(
            text = hint,
            modifier = Modifier.padding(vertical = IconPadding2)
        )
    }
}

@Preview
@Composable
fun CNSearchBarOldPreview() {
    AppSearchBar(
        onSearchActivated = {}
    )
}

object SearchBarDefaults {
    val IconPadding = 8.dp
    val IconPadding2 = 8.dp
}