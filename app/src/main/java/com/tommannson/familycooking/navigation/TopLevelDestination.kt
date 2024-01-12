package com.tommannson.familycooking.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.tommannson.familycooking.R
import com.tommannson.paymentorganizer.DesignTexts
import com.tommannson.paymentorganizer.icons.AppIcons

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    CookBook(
        selectedIcon = AppIcons.BookOpened,
        unselectedIcon = AppIcons.Book,
        iconTextId = DesignTexts.Menu_Recipes_labal,
        titleTextId = R.string.app_name,
    ),
}