package com.tommannson.familycooking.infrastructure.textRecognition

typealias RecipeContent = String
typealias IngredientsContent = String
class RecipeInfoSplitter {
    fun splitRecipeInfo(input: String): SplittedContent {
        val recipeText = input.extractExtBetween("[Przepis]:", "[Składniki]:")
        val ingredientsText = input.extractExtBetween("[Składniki]:", "[Narzędzia]:")

        return SplittedContent(
            recipeContent = recipeText,
            ingredientsContent = ingredientsText
        )
    }

    data class SplittedContent(
        val recipeContent: RecipeContent,
        val ingredientsContent: IngredientsContent
    )

}

private fun String.extractExtBetween(startingTag: String, finishingTag: String): String {
    val indexAfterStartingTag = indexOfAfter(startingTag)
    val indexBeforeFinishingTag = indexOf(finishingTag)

    return substring(indexAfterStartingTag, indexBeforeFinishingTag)
}

private fun String.indexOfAfter(string: String): Int {
    val indexOfFirstChar = indexOf(string)
    if (indexOfFirstChar == -1) return indexOfFirstChar

    return indexOfFirstChar + string.length
}