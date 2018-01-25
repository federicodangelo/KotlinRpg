package com.fangelo.kotlinrpg.ui.dialog

import com.badlogic.gdx.Gdx
import com.fangelo.libraries.ui.Dialog

class TestDialog : Dialog("Nice dialog") {
    init {

        text("This is a nice dialog")

        button("Button 1")
        button("Button 2")
        button("Button 3")
    }

    override fun result(obj: Any?) {
        Gdx.app.log("NANA", "Dialog closed!")
    }
}