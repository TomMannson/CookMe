package com.tommannson.recipe.data

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.tommannson.recipe.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object RecipeDataSourceSerializer : Serializer<Recipe> {
    override val defaultValue: Recipe = Recipe.build { }

    override suspend fun readFrom(input: InputStream): Recipe {
        return withContext(Dispatchers.IO) {
            if (input.available() != 0) try {
                Recipe.ADAPTER.decode(input)
            } catch (exception: IOException) {
                throw CorruptionException("Cannot read proto", exception)
            } else {
                defaultValue
            }
        }
    }

    override suspend fun writeTo(t: Recipe, output: OutputStream) {
        Recipe.ADAPTER.encode(output, t)
    }
}

val Context.recipeDataStore: DataStore<Recipe> by dataStore(
    fileName = "recipes.pb",
    serializer = RecipeDataSourceSerializer
)