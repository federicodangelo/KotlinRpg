package com.fangelo.libraries.ui

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import kotlinx.event.Event

abstract class Dialog(title: String) {

    val onClosed = Event<DialogResult>()
    var fadeIn = true
    var fadeOut = false

    private val gdxDialog: CustomGdxDialog

    init {
        gdxDialog = CustomGdxDialog(this, title, ScreenManager.skin, "dialog")
        //Nicer default button size, easier to click on mobile devices
        gdxDialog.buttonTable.defaults().minWidth(100f)
        gdxDialog.isMovable = false
    }

    protected fun text(text: String): Dialog {
        gdxDialog.text(text)
        return this
    }

    protected fun button(text: String): Dialog {
        gdxDialog.button(text)
        return this
    }

    protected fun button(text: String, result: DialogResult): Dialog {
        gdxDialog.button(text, result)
        return this
    }

    fun close(result : DialogResult) {
        if (!gdxDialog.closing) {
            ScreenManager.internalDialogClosed(this)
            onClosed(result)
            gdxDialog.hide()
        }
    }

    open fun act(delta: Float) {

    }

    internal fun internalShow(manager: ScreenManager) {

        if (fadeIn) {
            gdxDialog.show(manager.stage)
        } else {
            gdxDialog.show(manager.stage, null)
            gdxDialog.setPosition(Math.round((ScreenManager.stage.width - gdxDialog.width) / 2).toFloat(), Math.round((ScreenManager.stage.height - gdxDialog.height) / 2).toFloat())
        }
    }

    private class CustomGdxDialog(val dialog: Dialog, title: String, skin: Skin, windowStyleName: String) :
            com.badlogic.gdx.scenes.scene2d.ui.Dialog(title, skin, windowStyleName) {

        var closing = false

        override fun result(res: Any?) {
            closing = true
            ScreenManager.internalDialogClosed(dialog)
            when (res) {
                is DialogResult -> dialog.onClosed(res)
                else -> dialog.onClosed(DialogResult.None)
            }
        }

        override fun hide() {
            if (dialog.fadeOut)
                super.hide()
            else
                super.hide(null)
        }

        override fun act(delta: Float) {
            super.act(delta)
            if (!closing)
                dialog.act(delta)
        }
    }
}
