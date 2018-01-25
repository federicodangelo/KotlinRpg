package com.fangelo.kotlinrpg.ui.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.fangelo.kotlinrpg.PlatformAdapter
import com.fangelo.libraries.ui.Screen

class AboutScreen : Screen() {

    private val closeButton: Button

    init {
        setBackground("panel-brown")

        add("Crating and Dungeons v" + PlatformAdapter.instance!!.version).pad(10f)
        row()
        addLinkButton("Developed using libgdx", "https://libgdx.badlogicgames.com/").pad(10f)
        row()
        addLinkButton("Using free assets from Kenney", "http://kenney.nl/").pad(10f)
        row()
        addLinkButton("Using icons from Game-icons.net", "http://game-icons.net/").pad(10f)
        row()

        closeButton = TextButton("Close", skin)

        closeButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                onBackButtonPressed()
            }
        })

        add(closeButton).padTop(20f)
    }

    private fun addLinkButton(text: String, link: String): Cell<TextButton> {
        val button = TextButton("$text\n($link)", skin)
        button.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                if (Gdx.net != null) {
                    Gdx.net.openURI(link)
                }
            }
        })

        return add(button)
    }
}
