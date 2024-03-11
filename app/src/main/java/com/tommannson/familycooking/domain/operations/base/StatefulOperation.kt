package com.tommannson.familycooking.domain.operations.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class StatefulOperation<P, T> {

    protected open val initialState: T? = null
    protected val _state = MutableStateFlow(State<T>(data = initialState))
    protected val scope = CoroutineScope(SupervisorJob())
    abstract fun recognize(state: State<T>, trigger: P): Action<T>?

    val state: StateFlow<State<T>> = _state.asStateFlow()
    fun invoke(data: P): Flow<State<T>> {
        return synchronized(this) {
            val currentValue = state.value

            recognize(currentValue, data)
                ?.let { action ->
                    scope.launch {
                        _state.update { it.copy(started = true) }
                        val result = action.invoke()
                        _state.update { it.copy(started = false, finished = true, data = result) }
                    }
                }
            state
        }
    }

    fun interface Action<T> : suspend () -> T

    data class State<T>(
        val started: Boolean = false,
        val finished: Boolean = false,
        val data: T? = null
    ) {

        fun getOrElse(supplier: () -> T): T {
            return if (finished) data!! else supplier()
        }
    }
}