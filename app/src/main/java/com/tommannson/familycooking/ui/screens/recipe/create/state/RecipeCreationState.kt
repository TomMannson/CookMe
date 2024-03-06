package com.tommannson.familycooking.ui.screens.recipe.create.state


import com.tommannson.familycooking.ui.screens.recipe.create.state.data.CollectedData

sealed interface RecipeCreationState {

    sealed interface Initialization: RecipeCreationState  {
        data class NotInitialized(
            override val data: CollectedData
        ) : Initialization, ProgressIndication

        data class InitializationFailed(
            override val data: CollectedData,
        ) : Initialization, ErrorIndication

        data class CorrectInitialization(
            override val data: CollectedData,
        ) : Initialization
    }

    val data: CollectedData
}

interface ProgressIndication
interface ErrorIndication


