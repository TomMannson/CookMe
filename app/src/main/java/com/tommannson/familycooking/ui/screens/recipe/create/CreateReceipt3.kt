package com.tommannson.familycooking.ui.screens.recipe.create

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tommannson.familycooking.ui.base.UIEvent
import com.tommannson.familycooking.ui.screens.recipe.create.approuch4.ActiveStep
import com.tommannson.familycooking.ui.screens.recipe.create.approuch4.RecipeCreationState
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.ScreenAction
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.CreateReceiptImageLoaded4
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.CreateReceiptInitialization4
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.RecipeCreation4
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.TextExtractedInfo4
import com.tommannson.familycooking.ui.utils.fillInside
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReceipt4(
    modifier: Modifier = Modifier,
    getState: () -> StateFlow<RecipeCreationState>,
    getEvents: () -> StateFlow<List<UIEvent>>,
    dispatchEvent: (ScreenAction) -> Unit,
    onItemCreatedCreated: () -> Unit
) {
    val screenState by getState().collectAsState()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = getEvents, key2 = scope) {
        getEvents()
            .onEach {
                it.forEach {
                    onItemCreatedCreated()
                    it.handle()
                }
            }
            .launchIn(scope)
    }


    ConstraintLayout(modifier = modifier) {
        val (toolbar, childScreenRef) = createRefs()

        TopAppBar(
            modifier = Modifier.constrainAs(toolbar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            title = {
                Text(text = "Create Receipt")
            }
        )

        val startGuildLine = createGuidelineFromStart(16.dp)
        val endGuildLine = createGuidelineFromEnd(16.dp)

        val screenModifier = remember {
            Modifier.constrainAs(childScreenRef) {
                fillInside(toolbar.bottom, parent.bottom, startGuildLine, endGuildLine)
            }
        }

        when (screenState.step) {
            ActiveStep.ImageSelection -> {
                CreateReceiptInitialization4(
                    state = screenState,
                    onLoadImage = { dispatchEvent(ScreenAction.SelectImage) },
                    onManualCreation = {},
                    modifier = screenModifier,
                )
            }

            ActiveStep.ImageAcceptance -> {
                CreateReceiptImageLoaded4(
                    state = screenState,
                    modifier = screenModifier,
                    onLoadImage = { dispatchEvent(ScreenAction.SelectImage) },
                    onTextProcessing = { dispatchEvent(ScreenAction.ProcessImage) }
                )
            }

            ActiveStep.RecipeExtraction -> {
                TextExtractedInfo4(
                    screenState,
                    modifier = screenModifier,
                    onRetry = { },
                    onSubmit = { dispatchEvent.invoke(ScreenAction.ProcessExtractedRecipe) }
                )
            }

            ActiveStep.RecipeFixing -> {
                RecipeCreation4(
                    state = screenState,
                    modifier = screenModifier,
                    onRetry = { /*TODO*/ },
                    onSubmit = { dispatchEvent.invoke(ScreenAction.ProcessExtractedRecipe) }
                )
            }

            ActiveStep.Finished -> {
                Box {}
            }
        }


        FullParentProgressIndicator(progressActive = screenState.progressActive)
    }
}

//@Preview
//@Composable
//private fun CreateReceiptPreview2() {
//    CreateReceipt2(Modifier.fillMaxSize(), {})
//}