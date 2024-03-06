package com.tommannson.familycooking.ui.utils

import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.Dimension

fun ConstrainScope.fillInside(
    top: ConstraintLayoutBaseScope.HorizontalAnchor,
    bottom: ConstraintLayoutBaseScope.HorizontalAnchor,
    start: ConstraintLayoutBaseScope.VerticalAnchor,
    end: ConstraintLayoutBaseScope.VerticalAnchor
){
    linkTo(top, bottom)
    linkTo(start, end)
    width = Dimension.fillToConstraints
    height = Dimension.fillToConstraints
}