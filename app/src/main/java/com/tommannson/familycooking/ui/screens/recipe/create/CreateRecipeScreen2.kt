package com.tommannson.familycooking.ui.screens.recipe.create

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.hilt.getViewModel
import com.tommannson.familycooking.ui.navigation.NavigatorChild
import com.tommannson.familycooking.ui.screens.recipe.create.state.StateMachine
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

class CreateRecipeScreen2 : Screen {

    @Composable
    override fun Content() {
        val viewModel = getViewModel<CreateReceiptViewModel2>()
        val screenFlowManager = remember(Unit) {
            StateMachine(viewModel)
        }

        NavigatorChild { navigator ->
            CreateReceipt2(
                getState = { MutableStateFlow(screenFlowManager.currentStep) },
                getEvents = { MutableSharedFlow() },
                modifier = Modifier.fillMaxSize(),
                onItemCreatedCreated = navigator::popUntilRoot,
                onImageLoading = screenFlowManager::loadImage,
                onTextRecognitionAction = screenFlowManager::acceptImage,
//                onRestart = {},
//                onImageAccept = {}
            )
        }
    }
}