package com.tommannson.familycooking.ui.screens.recipe.create

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.tommannson.familycooking.ui.navigation.NavigatorChild
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.CreateReceiptViewModel3
import kotlinx.coroutines.flow.MutableSharedFlow

class CreateRecipeScreen3 : Screen {

    @Composable
    override fun Content() {
        val viewModel = getViewModel<CreateReceiptViewModel3>()

        NavigatorChild { navigator ->
            CreateReceipt3(
                getState = { viewModel.state },
                modifier = Modifier.fillMaxSize(),
                dispatchEvent = viewModel::performAction,
            )
        }
    }
}