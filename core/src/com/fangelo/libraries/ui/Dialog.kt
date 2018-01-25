package com.fangelo.libraries.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Pools

abstract class Dialog(title: String) : com.badlogic.gdx.scenes.scene2d.ui.Dialog(title, ScreenManager.skin, "dialog") {

    init {
        //Nicer default button size, easier to click on mobile devices
        buttonTable.defaults().minWidth(100f)
        isMovable = false
    }

    override fun result(obj: Any?) {

        val closeEvent = Pools.obtain(DialogCloseListener.DialogCloseEvent::class.java)

        if (obj is DialogResult) {
            closeEvent.result = obj
        } else {
            Gdx.app.error("DIALOG", "Expected DialogResult, but received $obj")
            closeEvent.result = DialogResult.None
        }
        fire(closeEvent)

        Pools.free(closeEvent)
    }
}
