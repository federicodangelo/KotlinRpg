package com.fangelo.kotlinrpg.ui.screen

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.fangelo.kotlinrpg.ui.dialog.TestDialog
import com.fangelo.libraries.ui.Screen
import com.fangelo.libraries.ui.ScreenManager

class MainMenuScreen : Screen() {

    private val settingsButton: Button
    private val playButton: Button
    private val aboutButton: Button
    private val testDialogButton: Button;

    init {
        setBackground("panel-brown")

        add("Kotlin RPG!!").padBottom(20f)
        row()

        playButton = TextButton("Play", skin)

        playButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                //ScreenManager.push(Screens.saveGameSelectorScreen);
            }
        })

        add(playButton).minWidth(150f).pad(10f)
        row()

        aboutButton = TextButton("About..", skin)

        aboutButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                ScreenManager.push(Screens.aboutScreen)
            }
        })

        add(aboutButton).minWidth(150f).pad(10f)
        row()

        settingsButton = TextButton("Settings", skin)

        settingsButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                ScreenManager.push(Screens.settingsScreen)
            }
        })

        add(settingsButton).minWidth(150f).pad(10f)
        row()

        testDialogButton = TextButton("Test Dialog", skin)

        testDialogButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                ScreenManager.show(TestDialog())
            }
        })

        add(testDialogButton).minWidth(150f).pad(10f)

    }
}
