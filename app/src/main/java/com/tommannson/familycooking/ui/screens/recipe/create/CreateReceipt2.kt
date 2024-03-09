package com.tommannson.familycooking.ui.screens.recipe.create

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
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.CreateReceiptImageLoaded2
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.CreateReceiptInitialization2
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.TextExtractedInfo2
import com.tommannson.familycooking.ui.screens.recipe.create.state.RecipeCreationState
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.InitializationStep
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.RecipeExtractionStep
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.RecipeTextFixingStep
import com.tommannson.familycooking.ui.utils.fillInside
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReceipt2(
    modifier: Modifier = Modifier,
    getState: () -> StateFlow<RecipeCreationState>,
    getEvents: () -> SharedFlow<Unit>,
    onItemCreatedCreated: () -> Unit,
    onImageLoading: () -> Unit,
    onTextRecognitionAction: () -> Unit,
    onImageAccept: () -> Unit,
    onRestart: () -> Unit,
) {
    val screenState by getState().collectAsState()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = getEvents, key2 = scope) {
        getEvents()
            .onEach { onItemCreatedCreated() }
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

        when (val step = screenState) {
            is InitializationStep -> {
                CreateReceiptInitialization2(
                    state = step,
                    onLoadImage = onImageLoading,
                    onManualCreation = {},
                    modifier = screenModifier,
                )
            }

            is RecipeExtractionStep -> {
                CreateReceiptImageLoaded2(
                    state = step,
                    modifier = screenModifier,
                    onLoadImage = onImageLoading,
                    onTextProcessing = onTextRecognitionAction
                )
            }

            is RecipeTextFixingStep -> {
                TextExtractedInfo2(
                    step,
                    modifier = screenModifier,
                    onRetry = { },
                    onSubmit = onImageAccept
                )
            }

            is RecipeCreationState.RecipeCreationStep -> {
                //                RecipeCreation(
                //                    state = step,
                //                    modifier = screenModifier,
                //                    onRetry = { /*TODO*/ },
                //                    onSubmit = viewModel::send
                //                )
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