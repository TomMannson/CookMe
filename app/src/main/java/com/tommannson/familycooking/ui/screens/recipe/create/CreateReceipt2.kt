package com.tommannson.familycooking.ui.screens.recipe.create

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.CreateReceiptImageLoaded
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.CreateReceiptImageLoaded2
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.CreateReceiptInitialization2
import com.tommannson.familycooking.ui.screens.recipe.create.state.RecipeCreationState
import com.tommannson.familycooking.ui.state.ProgressPresenceState
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
    onTextRecognitionAction: () -> Unit
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

//        when (val step = screenState) {
//            is RecipeCreationState.InitializationStep -> {
//                CreateReceiptInitialization2(
//                    state = step,
//                    onLoadImage = onImageLoading,
//                    onManualCreation = {},
//                    modifier = screenModifier,
//                )
//            }
//
//            is RecipeCreationState.RecipeExtractionStep -> {
//                CreateReceiptImageLoaded2(
//                    state = step,
//                    modifier = screenModifier,
//                    onLoadImage = onImageLoading,
//                    onTextProcessing = onTextRecognitionAction
//                )
//            }
//
//            is RecipeCreationState.RecipeTextFixingStep -> {
////                TextExtractedInfo(
////                    step.extractedText,
////                    modifier = screenModifier,
////                    onRetry = viewModel::restartProcess,
////                    onSubmit = viewModel::submitReceipt
////                )
//            }
//
//            is RecipeCreationState.RecipeCreationStep -> {
////                RecipeCreation(
////                    state = step,
////                    modifier = screenModifier,
////                    onRetry = { /*TODO*/ },
////                    onSubmit = viewModel::send
////                )
//            }
//        }


        FullParentProgressIndicator(
            progressActive = screenState.screenInfo.progressState is ProgressPresenceState.ActiveProgress
        )
    }
}


//@Preview
//@Composable
//private fun CreateReceiptPreview2() {
//    CreateReceipt2(Modifier.fillMaxSize(), {})
//}