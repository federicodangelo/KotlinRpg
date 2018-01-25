package com.fangelo.libraries.ui

import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.EventListener

abstract class DialogCloseListener : EventListener {
    override fun handle(event: Event): Boolean {
        if (event !is DialogCloseEvent) return false
        closed(event, event.getTarget() as Dialog)
        return false
    }

    /** @param actor The event target, which is the actor that emitted the change event.
     */
    abstract fun closed(event: DialogCloseEvent, dialog: Dialog)

    /** Fired when something in an actor has changed. This is a generic event, exactly what changed in an actor will vary.
     * @author Nathan Sweet
     */
    class DialogCloseEvent : Event() {
        var result: DialogResult? = null
    }
}
