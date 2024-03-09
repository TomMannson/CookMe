package com.tommannson.familycooking.ui.screens.recipe.create.state.steps

import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.tommannson.familycooking.ui.screens.recipe.create.state.RecipeCreationState
import com.tommannson.familycooking.ui.screens.recipe.create.state.UIEffect
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.CollectedData
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.TextExtraction
import com.tommannson.familycooking.ui.state.ScreenProperties
import com.tommannson.familycooking.ui.state.UiAction
import com.tommannson.familycooking.ui.state.finishErrorRestartable
import com.tommannson.familycooking.ui.state.finishSuccessfully
import com.tommannson.familycooking.ui.state.startAction

interface AcceptingImageStep {

    fun acceptImageForProcessing(): List<AcceptingUiEffect>
    fun notifyProcessingResult(result: Result<String>): AcceptingUiEffect?
}

interface Restartable {

    fun restart(): List<AcceptingUiEffect>
}

@Stable
class RecipeExtractionStep(
    dataConfig: CollectedData,
    override val screenInfo: ScreenProperties = ScreenProperties.initial(),
    textRecognitionActionConfig: UiAction,
    tryAgainActionConfig: UiAction,
) : RecipeCreationState, Restartable, AcceptingImageStep {

    override var data by mutableStateOf(dataConfig)
        private set
    var textRecognitionAction by mutableStateOf(textRecognitionActionConfig)
        private set
    var tryAgainAction by mutableStateOf(tryAgainActionConfig)
        private set

    override val progressActive: Boolean by derivedStateOf {
        textRecognitionAction.state is UiAction.State.InProgress
            || tryAgainAction.state is UiAction.State.InProgress
    }

    //        override fun restart() {
    //            manager.dispatchEffects(listOf(RecipeExtractionEffects.Restarted))
    //        }
    //
    //        fun loadImageAgain() {
    //            manager.dispatchEffects(listOf(RecipeExtractionEffects.Restarted))
    //        }
    //
    //        fun acceptImage() {
    //            manager.dispatchEffects(listOf(RecipeExtractionEffects.Restarted))
    //        }

    override fun restart(): List<AcceptingUiEffect> {
        return buildList {
            if (textRecognitionAction.canBeExecuted()) {
                add(AcceptingUiEffect.Restarted)
            }
        }
    }

    override fun acceptImageForProcessing(): List<AcceptingUiEffect> {
        return buildList {
//            if (textRecognitionAction.canBeExecuted()) {
                textRecognitionAction = textRecognitionAction.startAction()
                add(AcceptingUiEffect.AcceptanceTrigger)
//            }
        }
    }

    override fun notifyProcessingResult(result: Result<String>): AcceptingUiEffect? {
        if (textRecognitionAction.isWaitingForResult()) {
            result.onSuccess { loadedRecipeText ->
                val extractedTextInfo = data.extractedText?.copy(loadedRecipeText) ?: TextExtraction(loadedRecipeText)
                textRecognitionAction.finishSuccessfully()
                data = data.copy(extractedText = extractedTextInfo)
                return AcceptingUiEffect.AcceptanceFinishedCorrectly
            }.onFailure {
                textRecognitionAction.finishErrorRestartable(15)
            }
        }
        return null
    }
}

sealed interface AcceptingUiEffect : UIEffect {

    data object Restarted : AcceptingUiEffect
    data object AcceptanceTrigger : AcceptingUiEffect
    data object AcceptanceFinishedCorrectly : AcceptingUiEffect
}