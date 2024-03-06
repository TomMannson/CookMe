package com.tommannson.familycooking.ui.screens.recipe.create.screeninitialization

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.tommannson.familycooking.ui.screens.recipe.create.Step

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecipeCreation(
    state: Step.ReceiptCreation,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    onSubmit: (String) -> Unit,
) {
    val (name, setName) = remember { mutableStateOf(state.receiptName) }
    val (receiptContent, setReceiptContent) = remember { mutableStateOf(state.recipeContent) }
    val (ingredientText, setIngredientText) = remember { mutableStateOf(state.ingredientText) }

    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current

    val onSubmitData = remember(name, onSubmit) {
        { onSubmit(name) }
    }

    ConstraintLayout(modifier = modifier) {
        val (buttonBarRef) = createRefs()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize() // fill the entire window
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Button,
                    onClick = {
                        focusManager.clearFocus()
                    }
                )
                .onKeyEvent { keyEvent ->
                    return@onKeyEvent keyEvent.key == Key.Enter
                },
            content = {
                this.item {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(text = "Recipe Name", style = MaterialTheme.typography.labelMedium)
                    TextField(
                        value = name,
                        onValueChange = setName,
                        modifier = Modifier
                            .fillMaxWidth()
//                    .focusRequester(focusRequester)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Recipe Content", style = MaterialTheme.typography.labelMedium)
                    TextField(
                        value = receiptContent,
                        onValueChange = setReceiptContent,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Ingredients", style = MaterialTheme.typography.labelMedium)
                    TextField(
                        value = ingredientText,
                        onValueChange = setIngredientText,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        )

        Column(
            modifier = Modifier
                .constrainAs(buttonBarRef) {
                    linkTo(parent.start, parent.end)
                    bottom.linkTo(parent.bottom, 16.dp)
                    width = Dimension.matchParent

                }
//                .navigationBarsPadding() // padding for navigation bar
//                .imePadding(),
        ) {
            ElevatedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onRetry
            ) {
                Text(text = "Retry")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSubmitData
            ) {
                Text(text = "Submit")
            }
        }
    }
}