package com.tommannson.familycooking.ui.screens.recipe.create.state

import android.util.Log

//fun RecipeCreationState.restart(): RecipeCreationState {
//    return this.onTypeExecute<Restartable> { it.restart() }
//}

//inline fun <reified T> RecipeCreationState.onTypeExecute(
//    errorState: String = "InvalidType operationCanceled",
//    on: (T) -> RecipeCreationState,
//): RecipeCreationState {
//    return (this as? T)
//        ?.let { on(this) }
//        ?: this.also {
//            Log.d("Log", errorState)
//        }
//}