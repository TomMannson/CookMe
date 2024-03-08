package com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tommannson.familycooking.R
import com.tommannson.familycooking.ui.screens.recipe.create.state.RecipeCreationState

@Composable
fun CreateReceiptImageLoaded(
    modifier: Modifier = Modifier,
    loadedImageUri: Uri,
    onLoadImage: () -> Unit,
    onTextProcessing: () -> Unit,
) {
    BoxWithConstraints(modifier) {
        val isTablet = if (maxWidth > 400.dp) 100.dp else 0.dp
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (loadedImageRef, imageLoadingButton, titleRef) = createRefs()

            Column(
                modifier = Modifier
                    .constrainAs(titleRef) {
                        top.linkTo(parent.top)
                        width = Dimension.matchParent
                    }
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Below you can see loaded image that will be processed by AI",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Box(modifier = Modifier
                .constrainAs(loadedImageRef) {
                    linkTo(parent.top, parent.bottom)
                    width = Dimension.fillToConstraints
                }) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(loadedImageUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.aspectRatio(4 / 3f)
                )
            }

            Column(
                modifier = Modifier.constrainAs(imageLoadingButton) {
                    linkTo(parent.start, parent.end)
                    bottom.linkTo(parent.bottom, 16.dp)
                    width = Dimension.matchParent
                },

                ) {
                ElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onLoadImage
                ) {
                    Text(text = stringResource(R.string.btn_loadImageAgain))
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onTextProcessing
                ) {
                    Text(text = stringResource(R.string.btn_confirmLoadedImage))
                }
            }
        }
    }
}

@Composable
fun CreateReceiptImageLoaded2(
    state: RecipeCreationState.RecipeExtractionStep,
    modifier: Modifier = Modifier,
    onLoadImage: () -> Unit,
    onTextProcessing: () -> Unit,
) {
    BoxWithConstraints(modifier) {
        val isTablet = if (maxWidth > 400.dp) 100.dp else 0.dp
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (loadedImageRef, imageLoadingButton, titleRef) = createRefs()

            Column(
                modifier = Modifier
                    .constrainAs(titleRef) {
                        top.linkTo(parent.top)
                        width = Dimension.matchParent
                    }
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "Below you can see loaded image that will be processed by AI",
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Box(modifier = Modifier
                .constrainAs(loadedImageRef) {
                    linkTo(parent.top, parent.bottom)
                    width = Dimension.fillToConstraints
                }) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(state.data.image?.imageLocation)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.aspectRatio(4 / 3f)
                )
            }

            Column(
                modifier = Modifier.constrainAs(imageLoadingButton) {
                    linkTo(parent.start, parent.end)
                    bottom.linkTo(parent.bottom, 16.dp)
                    width = Dimension.matchParent
                },

                ) {
                ElevatedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = state::loadImageAgain
                ) {
                    Text(text = stringResource(R.string.btn_loadImageAgain))
                }
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = state::acceptImage
                ) {
                    Text(text = stringResource(R.string.btn_confirmLoadedImage))
                }
            }
        }
    }
}