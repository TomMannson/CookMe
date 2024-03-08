package com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tommannson.familycooking.R
import com.tommannson.familycooking.ui.screens.recipe.create.state.RecipeCreationState
import com.tommannson.familycooking.ui.state.UiAction

@Composable
internal fun CreateReceiptInitialization(
    modifier: Modifier = Modifier,
    onLoadImage: () -> Unit,
    onManualCreation: () -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (imageLoadingButton, manualCreationRef, infographicRef, titleRef) = createRefs()

        Image(
            modifier = Modifier
                .aspectRatio(1f)
                .constrainAs(infographicRef) {
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                    width = Dimension.matchParent
                },
            painter = painterResource(id = R.drawable.theme_imaga),
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 48.dp)
                .constrainAs(titleRef) {
                    top.linkTo(infographicRef.bottom)
                }) {
            Text(
                text = "Rewrite recipes from your cook book 10 times faster with AI",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Using our AI assistant we will load favorite recipes directly form your cooking books"
            )
        }

        ElevatedButton(
            modifier = Modifier.constrainAs(manualCreationRef) {
                bottom.linkTo(imageLoadingButton.top)
                linkTo(parent.start, parent.end)
                width = Dimension.fillToConstraints
            },
            onClick = onManualCreation
        ) {
            Text(text = stringResource(R.string.btn_createManually))
        }
        Button(
            modifier = Modifier.constrainAs(imageLoadingButton) {
                bottom.linkTo(parent.bottom)
                linkTo(parent.start, parent.end)
                width = Dimension.fillToConstraints
            },
            onClick = onLoadImage
        ) {
            Text(text = stringResource(R.string.btn_loadImage))
        }
    }
}

@Composable
internal fun CreateReceiptInitialization2(
    state: RecipeCreationState.InitializationStep,
    modifier: Modifier = Modifier,
    onLoadImage: () -> Unit,
    onManualCreation: () -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (imageLoadingButton, manualCreationRef, infographicRef, titleRef) = createRefs()

        Image(
            modifier = Modifier
                .aspectRatio(1f)
                .constrainAs(infographicRef) {
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                    width = Dimension.matchParent
                },
            painter = painterResource(id = R.drawable.theme_imaga),
            contentDescription = null
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 48.dp)
                .constrainAs(titleRef) {
                    top.linkTo(infographicRef.bottom)
                }) {
            Text(
                text = "Rewrite recipes from your cook book 10 times faster with AI",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Using our AI assistant we will load favorite recipes directly form your cooking books"
            )
        }

        ElevatedButton(
            modifier = Modifier.constrainAs(manualCreationRef) {
                bottom.linkTo(imageLoadingButton.top)
                linkTo(parent.start, parent.end)
                width = Dimension.fillToConstraints
            },
            enabled = state.manualRecipeCreation.availability == UiAction.Availability.Available,
            onClick = onManualCreation
        ) {
            Text(text = stringResource(R.string.btn_createManually))
        }
        Button(
            modifier = Modifier.constrainAs(imageLoadingButton) {
                bottom.linkTo(parent.bottom)
                linkTo(parent.start, parent.end)
                width = Dimension.fillToConstraints
            },
            enabled = state.imageLoadingAction.availability == UiAction.Availability.Available,
            onClick = state::loadImage
        ) {
            Text(text = stringResource(R.string.btn_loadImage))
        }
    }
}


@Preview
@Composable
fun CreateReceiptInitializationPreview() {
    CreateReceiptInitialization(
        modifier = Modifier.fillMaxSize(),
        onLoadImage = {},
        onManualCreation = {}
    )
}