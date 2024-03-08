package com.tommannson.familycooking.ui.screens.recipe.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.tommannson.familycooking.ui.navigation.NavigatorChild
import com.tommannson.familycooking.ui.screens.recipe.create.CreateRecipeScreen
import com.tommannson.familycooking.ui.screens.recipe.create.CreateRecipeScreen2

class ListScreen : Screen {
    @Composable
    override fun Content() {
        NavigatorChild { navigator ->
            val viewModel = getViewModel<ListReceiptViewModel>()

            RecipeListComponent(
                modifier = Modifier.fillMaxSize(),
                getState = { viewModel.state },
                onOpenCreator = { navigator.push(CreateRecipeScreen2()) },
                onContentLoad = { viewModel.loadRecipes() }
            )
        }
    }
}