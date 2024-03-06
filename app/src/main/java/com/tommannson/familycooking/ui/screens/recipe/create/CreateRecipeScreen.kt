package com.tommannson.familycooking.ui.screens.recipe.create

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.tommannson.familycooking.ui.navigation.NavigatorChild

class CreateRecipeScreen : Screen {
    @Composable
    override fun Content() {
        NavigatorChild { navigator ->
            CreateReceipt(
                modifier = Modifier.fillMaxSize(),
                onItemCreatedCreated = {
                    navigator.popUntilRoot()
                }
            )
        }
    }
}