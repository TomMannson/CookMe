package com.tommannson.familycooking.ui.screens.recipe.create.approuch4

import com.tommannson.familycooking.domain.operations.ProcessImageOperation
import com.tommannson.familycooking.infrastructure.api.recipeapi.Api
import com.tommannson.familycooking.infrastructure.api.recipeapi.RecipeDto
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class RecipeCreationHandler @Inject constructor(
    private val processImage: ProcessImageOperation,
) {
    val recipeCreationState = MutableStateFlow(Status.Idle)

    private val cancelledState = MutableStateFlow(false)

    suspend fun processImage(recipe: RecipeDto) {
        if (recipeCreationState.value == Status.Idle) {
            val service = Api.createService()

            val response = service.createRecipe(recipe)

            recipeCreationState.value = if (response.isSuccessful) Status.Success else Status.Error
        }
    }

    fun acceptResponse() {
        recipeCreationState.value = Status.Idle
    }

    fun reset() {
        cancelledState.value = false
    }

    enum class Status {
        Idle,
        Success,
        Error
    }
}
