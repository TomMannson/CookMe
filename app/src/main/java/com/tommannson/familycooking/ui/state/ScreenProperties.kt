package com.tommannson.familycooking.ui.state

class ScreenProperties private constructor(
    val contentState: ContentPresenceState = ContentPresenceState.NotPresent,
    val progressState: ProgressPresenceState = ProgressPresenceState.Idle,
    val errorState: ErrorPresenceState = ErrorPresenceState.NoError,
) {
    companion object {
        fun initial() = ScreenProperties()
    }
}

sealed interface ContentPresenceState {

    data object NotPresent : ContentPresenceState
    data object PartiallyLoaded : ContentPresenceState
    data object FullyLoaded : ContentPresenceState
}

sealed interface ErrorPresenceState {
    data object NoError : ErrorPresenceState
    data object Error : ErrorPresenceState
}

sealed interface ProgressPresenceState {
    data object Idle : ProgressPresenceState
    data object ActiveProgress : ProgressPresenceState
}