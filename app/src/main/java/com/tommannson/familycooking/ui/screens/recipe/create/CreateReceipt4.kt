package com.tommannson.familycooking.ui.screens.recipe.create

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.ScreenAction
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.state.RecipeCreationState
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.CreateReceiptImageLoaded3
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.CreateReceiptInitialization3
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.RecipeCreation3
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.TextExtractedInfo3
import com.tommannson.familycooking.ui.utils.fillInside
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReceipt3(
    modifier: Modifier = Modifier,
    getState: () -> StateFlow<RecipeCreationState>,
    dispatchEvent: (ScreenAction) -> Unit,
) {
    val screenState by getState().collectAsState()

    val scope = rememberCoroutineScope()
//    LaunchedEffect(key1 = getEvents, key2 = scope) {
//        getEvents()
//            .onEach { onItemCreatedCreated() }
//            .launchIn(scope)
//    }


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
            is RecipeCreationState.ImageSelectionStep -> {
                CreateReceiptInitialization3(
                    state = step,
                    onLoadImage = { dispatchEvent(ScreenAction.SelectImage) },
                    onManualCreation = {},
                    modifier = screenModifier,
                )
            }

            is RecipeCreationState.RecipeTextExtractionStep -> {
                CreateReceiptImageLoaded3(
                    state = step,
                    modifier = screenModifier,
                    onLoadImage = { dispatchEvent(ScreenAction.SelectImage) },
                    onTextProcessing = { dispatchEvent(ScreenAction.ProcessImage) }
                )
            }

            is RecipeCreationState.RecipeTextFixingStep -> {
                TextExtractedInfo3(
                    step,
                    modifier = screenModifier,
                    onRetry = { },
                    onSubmit = { dispatchEvent.invoke(ScreenAction.ProcessExtractedRecipe) }
                )
            }

            is RecipeCreationState.RecipeSubmittedStep -> {
                RecipeCreation3(
                    state = step,
                    modifier = screenModifier,
                    onRetry = { /*TODO*/ },
                    onSubmit = { dispatchEvent.invoke(ScreenAction.ProcessExtractedRecipe) }
                )
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