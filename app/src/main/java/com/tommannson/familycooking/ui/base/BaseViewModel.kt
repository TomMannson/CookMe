package com.tommannson.familycooking.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    fun <T> Flow<T>.stateInViewModel(
        initialState: T,
        started: SharingStarted = SharingStarted.WhileSubscribed(5000),
    ): StateFlow<T> {
        return stateIn(viewModelScope, started, initialState)
    }

     fun <T> T?.logInvalidCommendExecution(currentState: Any, event: Any): T? {
        return also {
            if (it == null) {
                Timber.v("Action impossible: ${event} ${currentState}")
            }
        }
    }
}