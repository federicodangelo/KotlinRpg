package com.fangelo.libraries.ui

import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.EventListener

abstract class DialogCloseListener : EventListener {
    final override fun handle(event: Event): Boolean {
        if (event !is DialogCloseEvent) return false
        closed(event, event.getTarget() as Dialog)
        return false
    }

    abstract fun closed(event: DialogCloseEvent, dialog: Dialog)

    class DialogCloseEvent : Event() {
        var result: DialogResult = DialogResult.None
    }
}
