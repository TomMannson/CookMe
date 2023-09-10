package com.tommannson.familycooking.recipedetails.content

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface RecipeState {

    @Immutable
    data class SimpleReceipt(
        val id: Long?,
        val receiptName: String,
        val numberOfPortions: Int,
        val receiptSteps: RecipeStepsState,
        val ingredientsText: IngredientsState,
        val nutrition: NutritionState = NutritionState.NoInfo,
    ) : RecipeState
}

@Stable
sealed interface RecipeStepsState {
    data class TextSteps(val content: String)
}

@Stable
sealed interface IngredientsState {
    data class TextIngredients(val content: String)
}

@Stable
sealed interface NutritionState {
    object NoInfo : NutritionState
}