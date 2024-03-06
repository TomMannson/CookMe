package com.tommannson.familycooking.ui.screens.recipe.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommannson.familycooking.ui.screens.recipe.create.Api
import com.tommannson.familycooking.ui.screens.recipe.create.ProgressInfo
import com.tommannson.familycooking.ui.screens.recipe.create.RecipeDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListReceiptViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(RecipeListState(listOf()))
    val state = _state.asStateFlow()

    fun loadRecipes() {
        showLoadingProgress()
        viewModelScope.launch(Dispatchers.IO) {
            val service = Api.createService()

            val response = service.getAllRecipes()

            _state.update {
                RecipeListState(
                    loadedRecipes = if (response.isSuccessful) response.body()!!.content else emptyList(),
                    state = if (response.isSuccessful) StateResult.SUCCESS else StateResult.ERROR
                )
            }
        }
    }

    private fun showLoadingProgress() {
        _state.update {
            RecipeListState(
                loadedRecipes = emptyList(),
                state = StateResult.LOADING
            )
        }
    }
}

data class RecipeListState(
    val loadedRecipes: List<RecipeDto>,
    val state: StateResult = StateResult.IDLE,
) : ProgressInfo {
    override val progressActive: Boolean
        get() = state == StateResult.LOADING
}

enum class StateResult { IDLE, LOADING, SUCCESS, ERROR }


