package com.fangelo.kotlinrpg.ui.screen

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.fangelo.kotlinrpg.settings.Settings
import com.fangelo.libraries.ui.Screen

class SettingsScreen : Screen() {

    private val settingsContainer: Table

    private val showFpsButton: Button

    private val closeButton: Button

    init {

        settingsContainer = Table(skin).padLeft(30f).padRight(30f).padBottom(5f).padTop(5f)
        settingsContainer.setBackground("panel-brown")

        add(settingsContainer).center()

        settingsContainer.add("Settings").top().padBottom(20f)
        settingsContainer.row()

        showFpsButton = CheckBox("Show FPS / Stats", skin)
        showFpsButton.isChecked = Settings.showFps
        showFpsButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                Settings.showFps = showFpsButton.isChecked
            }
        })
        settingsContainer.add<Button>(showFpsButton).pad(5f)

        settingsContainer.row()

        closeButton = TextButton("Close", skin)

        closeButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeListener.ChangeEvent, actor: Actor) {
                onBackButtonPressed()
            }
        })

        settingsContainer.add<Button>(closeButton).padTop(20f)
    }
}
