package com.tommannson.familycooking.domain.operations


import com.tommannson.familycooking.domain.operations.base.StatefulOperation
import com.tommannson.familycooking.domain.operations.base.StatefulOperation.Action
import com.tommannson.familycooking.infrastructure.textRecognition.RecipeInfoSplitter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeCreatingOperation @Inject constructor() :
    StatefulOperation<String, RecipeInfoSplitter.SplittedContent>() {

    private val recipeSplitter = RecipeInfoSplitter()

    override fun recognize(
        state: State<RecipeInfoSplitter.SplittedContent>,
        trigger: String
    ): Action<RecipeInfoSplitter.SplittedContent>? {
        if (state.started) {
            return null
        }

        return Action { recipeSplitter.splitRecipeInfo(trigger) }
    }
}