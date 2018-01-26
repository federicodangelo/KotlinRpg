package com.fangelo.kotlinrpg.ui.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.fangelo.kotlinrpg.PlatformAdapter
import com.fangelo.libraries.ui.Screen
import ktx.actors.onChange

class AboutScreen : Screen() {

    init {
        setBackground("panel-brown")

        addTitle("Crating and Dungeons v" + PlatformAdapter.instance.version)

        addLinkButton("Developed using libgdx", "https://libgdx.badlogicgames.com/")
        addLinkButton("Using free assets from Kenney", "http://kenney.nl/")
        addLinkButton("Using icons from Game-icons.net", "http://game-icons.net/")

        addCloseButton()
    }

    private fun addCloseButton() {
        val closeButton = TextButton("Close", skin)

        closeButton.onChange {
            close()
        }

        add(closeButton).padTop(20f)
    }

    private fun addTitle(title: String) {
        add(title).pad(10f)
        row()
    }

    private fun addLinkButton(text: String, link: String) {
        val button = TextButton("$text\n($link)", skin)
        button.onChange {
            if (Gdx.net != null) {
                Gdx.net.openURI(link)
            }
        }

        add(button).pad(10f)
        row()
    }
}
