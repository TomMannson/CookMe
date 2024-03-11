package com.tommannson.familycooking.domain.operations


import android.net.Uri
import com.tommannson.familycooking.domain.operations.base.StatefulOperation
import com.tommannson.familycooking.domain.operations.base.StatefulOperation.Action
import com.tommannson.familycooking.infrastructure.textRecognition.TextRecognizer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProcessImageOperation @Inject constructor(
    private val textRecognition: TextRecognizer,
) : StatefulOperation<Uri, String?>() {
    override fun recognize(state: State<String?>, trigger: Uri): Action<String?>? {
        if (state.started) {
            return null
        }

        return Action { textRecognition.processRecipe(trigger) }
    }
}