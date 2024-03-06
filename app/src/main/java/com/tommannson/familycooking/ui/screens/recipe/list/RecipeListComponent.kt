package com.tommannson.familycooking.ui.screens.recipe.list

import android.R.drawable.ic_menu_add
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.tommannson.familycooking.ui.screens.recipe.create.FullParentProgressIndicator
import com.tommannson.familycooking.ui.utils.fillInside
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun RecipeListComponent(
    modifier: Modifier = Modifier,
    getState: () -> StateFlow<RecipeListState>,
    onOpenCreator: () -> Unit,
    onContentLoad: () -> Unit
) {
    val screenState by getState().collectAsState()

    LaunchedEffect(key1 = onContentLoad) {
        onContentLoad()
    }

    ScreenContent(
        modifier = modifier,
        screenState = screenState,
        onOpenCreator = onOpenCreator
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ScreenContent(
    modifier: Modifier,
    screenState: RecipeListState,
    onOpenCreator: () -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (toolbar, childScreenRef, newRecipeFabRef) = createRefs()

        TopAppBar(
            modifier = Modifier.constrainAs(toolbar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            title = { Text(text = "Recipes") }
        )

        val startGuildLine = createGuidelineFromStart(16.dp)
        val endGuildLine = createGuidelineFromEnd(16.dp)

        val screenModifier = remember {
            Modifier.constrainAs(childScreenRef) {
                fillInside(toolbar.bottom, parent.bottom, startGuildLine, endGuildLine)
            }
        }

        when (screenState.state) {
            StateResult.SUCCESS -> {
                Box(modifier = screenModifier, Alignment.Center) {
                    Text(text = "Loaded ${screenState.loadedRecipes.size} Items", modifier)
                }
            }

            StateResult.ERROR -> {
                Box(modifier = screenModifier, Alignment.Center) {
                    Text(text = "Error")
                }
            }

            else -> {}
        }

        FullParentProgressIndicator(
            progressActive = screenState.progressActive
        )

        FloatingActionButton(
            modifier = Modifier.constrainAs(newRecipeFabRef) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
            },
            onClick = onOpenCreator
        ) {
            Icon(
                painter = painterResource(id = ic_menu_add),
                contentDescription = null
            )
        }
    }
}


@Preview
@Composable
fun CreateReceiptPreview() {
    RecipeListComponent(
        modifier = Modifier.fillMaxSize(),
        getState = {
            MutableStateFlow(
                RecipeListState(emptyList())
            )
        },
        onOpenCreator = {},
        onContentLoad = {},
    )
}