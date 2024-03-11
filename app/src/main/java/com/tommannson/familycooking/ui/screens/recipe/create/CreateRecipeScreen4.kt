package com.tommannson.familycooking.ui.screens.recipe.create

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.tommannson.familycooking.ui.navigation.NavigatorChild
import com.tommannson.familycooking.ui.screens.recipe.create.approuch4.CreateReceiptViewModel4
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.CreateReceiptViewModel3
import kotlinx.coroutines.flow.MutableSharedFlow

class CreateRecipeScreen4 : Screen {

    @Composable
    override fun Content() {
        val viewModel = getViewModel<CreateReceiptViewModel4>()

        NavigatorChild { navigator ->
            CreateReceipt4(
                getState = { viewModel.state },
                getEvents = { viewModel.events },
                modifier = Modifier.fillMaxSize(),
                dispatchEvent = viewModel::performAction,
                onItemCreatedCreated = {
                    navigator.pop()
                }
            )
        }
    }
}