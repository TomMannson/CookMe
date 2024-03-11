package com.tommannson.familycooking.domain.operations

import android.net.Uri
import com.tommannson.familycooking.domain.operations.base.StatefulOperation
import com.tommannson.familycooking.domain.operations.base.StatefulOperation.Action
import com.tommannson.familycooking.infrastructure.cameraPicker.CameraPicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelectImageOperation @Inject constructor(private val cameraPicker: CameraPicker) :
    StatefulOperation<Unit, Uri?>() {
    override fun recognize(state: State<Uri?>, trigger: Unit): Action<Uri?>? {
        if (state.started) return null
        return Action { withContext(Dispatchers.Main) { cameraPicker.launchCamera() } }
    }
}