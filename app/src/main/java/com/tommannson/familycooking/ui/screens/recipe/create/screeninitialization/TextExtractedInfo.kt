package com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.RecipeTextFixingStep

@Composable
internal fun TextExtractedInfo(
    extractedText: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    onSubmit: () -> Unit,
) {
    ConstraintLayout(modifier = modifier) {
        val (bodyRef, buttonBarRef) = createRefs()
        Column(
            modifier = Modifier
                .constrainAs(bodyRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(buttonBarRef.top)
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                }
                .verticalScroll(state = rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text("Below you can see result of Receipt Extraction ", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))
            Text(extractedText)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Column(
            modifier = Modifier.constrainAs(buttonBarRef) {
                linkTo(parent.start, parent.end)
                bottom.linkTo(parent.bottom, 16.dp)
                width = Dimension.matchParent
            },

            ) {
            ElevatedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onRetry
            ) {
                Text(text = "Retry")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSubmit
            ) {
                Text(text = "Submit")
            }
        }
    }
}

@Composable
internal fun TextExtractedInfo2(
    state: RecipeTextFixingStep,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    onSubmit: () -> Unit,
) {
    ConstraintLayout(modifier = modifier) {
        val (bodyRef, buttonBarRef) = createRefs()
        Column(
            modifier = Modifier
                .constrainAs(bodyRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(buttonBarRef.top)
                    width = Dimension.matchParent
                    height = Dimension.fillToConstraints
                }
                .verticalScroll(state = rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text("Below you can see result of Receipt Extraction ", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))
            Text(state.data.extractedText?.extractedText ?: "")
            Spacer(modifier = Modifier.height(8.dp))
        }
        Column(
            modifier = Modifier.constrainAs(buttonBarRef) {
                linkTo(parent.start, parent.end)
                bottom.linkTo(parent.bottom, 16.dp)
                width = Dimension.matchParent
            },

            ) {
            ElevatedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onRetry
            ) {
                Text(text = "Retry")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSubmit
            ) {
                Text(text = "Submit")
            }
        }
    }
}