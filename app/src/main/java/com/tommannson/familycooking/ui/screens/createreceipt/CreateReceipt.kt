package com.tommannson.familycooking.ui.screens.createreceipt

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateReceipt(modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier) {
        val (toolbar, recipeTextRef, submitRecipeButtonRef) = createRefs()

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

        var recipe by remember { mutableStateOf("") }

        TextField(
            modifier = Modifier.constrainAs(recipeTextRef) {
                top.linkTo(toolbar.bottom, margin = 16.dp)
                start.linkTo(startGuildLine)
                end.linkTo(endGuildLine)
                width = Dimension.fillToConstraints
            },
            value = recipe,
            onValueChange = {
                recipe = it
            }
        )
        Button(
            modifier = Modifier.constrainAs(submitRecipeButtonRef) {
                top.linkTo(recipeTextRef.bottom, margin = 16.dp)
                start.linkTo(startGuildLine)
            },
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Zatwierdź przepis do przetworzenia")
        }
    }
}

@Preview
@Composable
fun CreateReceiptPreview() {
    CreateReceipt(Modifier.fillMaxSize())
}