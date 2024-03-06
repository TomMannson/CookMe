package com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tommannson.familycooking.R
import com.tommannson.paymentorganizer.components.constrans.centerToParent

@Composable
internal fun CreateReceiptImageLoadingError(
    modifier: Modifier = Modifier,
    onLoadImage: () -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (errorTextRef, imageLoadingButton) = createRefs()

        Text(
            text = "Image loading failed",
            modifier = Modifier.constrainAs(errorTextRef) {
                bottom.linkTo(imageLoadingButton.top, margin = 32.dp)
                centerHorizontallyTo(parent)
            }
        )

        Button(
            modifier = Modifier.constrainAs(imageLoadingButton) {
                centerToParent()
            },
            onClick = onLoadImage
        ) {
            Text(text = stringResource(R.string.btn_loadImageAgain))
        }
    }
}