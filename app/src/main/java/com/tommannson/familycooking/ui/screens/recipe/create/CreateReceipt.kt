package com.tommannson.familycooking.ui.screens.recipe.create

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.CreateReceiptImageLoaded
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.CreateReceiptImageLoadingError
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.CreateReceiptInitialization
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.RecipeCreation
import com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization.TextExtractedInfo
import com.tommannson.familycooking.ui.utils.fillInside
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReceipt(
    modifier: Modifier = Modifier,
    onItemCreatedCreated: () -> Unit
) {
    val navController = rememberNavController()
    val startRoute = "example"

    NavHost(navController, startDestination = startRoute) {
        composable("example") { backStackEntry ->
            val viewModel = hiltViewModel<CreateReceiptViewModel>()
            val screenState by viewModel.state.collectAsState()

            val scope = rememberCoroutineScope()
            LaunchedEffect(key1 = viewModel) {
                viewModel.event
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

                when (val step = screenState.step) {
                    is Step.NotInitialized -> {
                        CreateReceiptInitialization(
                            onLoadImage = viewModel::performImageLoading,
                            onManualCreation = {},
                            modifier = screenModifier,
                        )
                    }

                    is Step.ImageProvided -> {
                        CreateReceiptImageLoaded(
                            modifier = screenModifier,
                            loadedImageUri = step.imageUri,
                            onLoadImage = viewModel::performImageLoading,
                            onTextProcessing = viewModel::performImageTextProcessing
                        )
                    }

                    is Step.ImageInfoError -> {
                        CreateReceiptImageLoadingError(
                            modifier = screenModifier,
                            onLoadImage = viewModel::performImageLoading
                        )
                    }

                    is Step.ImageTextExtractionFinished -> {
                        TextExtractedInfo(
                            step.extractedText,
                            modifier = screenModifier,
                            onRetry = viewModel::restartProcess,
                            onSubmit = viewModel::submitReceipt
                        )
                    }

                    is Step.ImageTextExtractionError -> {
                        Box(modifier = screenModifier) {
                            Text(
                                text = "Text recognition error",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    is Step.ReceiptCreation -> {
                        RecipeCreation(
                            state = step,
                            modifier = screenModifier,
                            onRetry = { /*TODO*/ },
                            onSubmit = viewModel::send
                        )
                    }

                    is Step.Finished -> {
                        Box(modifier = screenModifier) {
                            Text(text = "Recipe Created")
                        }
                    }
                }

                if (screenState.step is ProgressInfo) {
                    val stepInfo = screenState.step as ProgressInfo
                    FullParentProgressIndicator(
                        progressActive = stepInfo.progressActive
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun CreateReceiptPreview() {
    CreateReceipt(Modifier.fillMaxSize(), {})
}