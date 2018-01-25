package com.fangelo.libraries.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.fangelo.kotlinrpg.ui.dialog.ConfirmDialog

abstract class Screen : Table() {

    init {
        setFillParent(true)
        skin = ScreenManager.skin
    }

    open fun onShow() {
        //Called when the screen is shown
    }

    open fun onHide() {
        //Called when the screen is hidden
    }

    open fun onResize(width: Int, height: Int) {
        //Called when the screen is resized
    }

    fun onBackButtonPressed() {
        if (ScreenManager.canPop()) {
            ScreenManager.pop()
        } else {
            ScreenManager.show(ConfirmDialog("Exit", "Exit game?")).addListener(object : DialogCloseListener() {
                override fun closed(event: DialogCloseListener.DialogCloseEvent, dialog: Dialog) {
                    if (event.result == DialogResult.Yes)
                        Gdx.app.exit()
                }
            })
        }
    }
}
