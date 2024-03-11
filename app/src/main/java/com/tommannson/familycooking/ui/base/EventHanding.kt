package com.tommannson.familycooking.ui.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID


abstract class UIEvent() {
    val id: UUID = UUID.randomUUID()
    private var handled: Boolean = false
    internal lateinit var handler: UIEventAcknowledgeHandler

    fun handle() {
        handler.submitProcessing(this)
        handled = true
    }

    override fun toString(): String {
        return "${this::class.java.name} $id handled:${handled}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UIEvent

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

interface UIEventAcknowledgeHandler {

    fun submitProcessing(event: UIEvent)
}

interface UIEventHolder {

    val events: StateFlow<List<UIEvent>>
    fun sendEvent(event: UIEvent)

    companion object {
        fun getDefault(): UIEventHolder = DefaultUIEventHolder()
    }
}

internal class DefaultUIEventHolder : UIEventHolder, UIEventAcknowledgeHandler {

    private val _events = MutableStateFlow<List<UIEvent>>(emptyList())
    override val events: StateFlow<List<UIEvent>> = _events.asStateFlow()
    override fun sendEvent(event: UIEvent) {
        _events.update {
            it.toMutableList()
                .also {
                    it.add(event)
                    event.handler = this
                }
                .toList()
        }
    }

    override fun submitProcessing(event: UIEvent) {
        _events.update {
            it.toMutableList()
                .also { it.add(event) }
                .toList()
        }
    }
}