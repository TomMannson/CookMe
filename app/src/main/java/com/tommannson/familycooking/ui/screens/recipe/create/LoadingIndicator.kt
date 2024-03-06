package com.tommannson.familycooking.ui.screens.recipe.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension

@Composable
fun ConstraintLayoutScope.FullParentProgressIndicator(
    progressActive: Boolean
) {
    val progressRef = createRef()

    if (progressActive) {
        Box(modifier = Modifier
            .clickable { }
            .constrainAs(progressRef) {
                width = Dimension.matchParent
                height = Dimension.matchParent
            }) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}