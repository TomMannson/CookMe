package com.tommannson.familycooking.ui.state

data class UiAction(
    val state: State,
    val availability: Availability = Availability.Blocked,
) {

    sealed interface State {
        data object Idle : State
        data object InProgress : State
        interface Finished : State {
            data object Success : State
            data class Error(val errorInfo: Int) : State
        }
    }

    sealed interface Availability {
        data object Blocked : Availability
        data object Hidden : Availability
        data object Available : Availability
    }

    fun canBeExecuted(allowParallel: Boolean = false): Boolean {
        return (state != State.InProgress || allowParallel)
                && availability == Availability.Available
    }

    fun isWaitingForResult(): Boolean {
        return state == State.InProgress
    }


    companion object {
        fun blockedByDefault() = UiAction(State.Idle, Availability.Blocked)
        fun activeByDefault() = UiAction(State.Idle, Availability.Available)
    }
}

fun UiAction.startAction(hide: Boolean = false): UiAction {
    val incommingAvailablility =
        if (hide) UiAction.Availability.Hidden else UiAction.Availability.Blocked
    return UiAction(
        state = UiAction.State.InProgress,
        availability = incommingAvailablility
    )
}

fun UiAction.finishSuccessfully(): UiAction {
    return UiAction(
        state = UiAction.State.Finished.Success,
        availability = UiAction.Availability.Blocked
    )
}

fun UiAction.finishErrorRestartable(errorId: Int): UiAction {
    return UiAction(
        state = UiAction.State.Finished.Error(errorId),
        availability = UiAction.Availability.Blocked
    )
}


