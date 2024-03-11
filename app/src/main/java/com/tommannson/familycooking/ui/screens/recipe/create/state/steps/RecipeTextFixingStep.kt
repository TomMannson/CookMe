package com.tommannson.familycooking.ui.screens.recipe.create.state.steps

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.tommannson.familycooking.ui.screens.recipe.create.ProgressInfo
import com.tommannson.familycooking.ui.screens.recipe.create.state.RecipeCreationState
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.CollectedData
import com.tommannson.familycooking.ui.state.ScreenProperties
import com.tommannson.familycooking.ui.state.UiAction

@Stable
class RecipeTextFixingStep(
    dataConfig: CollectedData,
    override val screenInfo: ScreenProperties = ScreenProperties.initial(),
    val textRecognitionAction: UiAction,
) : RecipeCreationState, ProgressInfo {

    override val progressActive: Boolean = false

    override var data by mutableStateOf(dataConfig)
        private set
    //    var textRecognitionAction by mutableStateOf(textRecognitionActionConfig)
    //        private set
    //    var tryAgainAction by mutableStateOf(tryAgainActionConfig)
    //        private set

}