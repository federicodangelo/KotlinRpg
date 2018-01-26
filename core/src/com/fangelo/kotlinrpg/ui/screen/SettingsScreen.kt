package com.fangelo.kotlinrpg.ui.screen

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.fangelo.libraries.debug.DebugSettings
import com.fangelo.libraries.ui.Screen
import ktx.actors.onChange

class SettingsScreen : Screen() {

    private val settingsContainer: Table

    init {

        settingsContainer = addSettingsContainer()

        addTitle("Settings")

        addCheckbox("Show FPS / Stats", DebugSettings.showFps, { button -> DebugSettings.showFps = button.isChecked })

        addCloseButton()
    }

    private fun addCheckbox(name: String, startingValue: Boolean, onChange: (button: CheckBox) -> Unit) {
        val button = CheckBox(name, skin)
        button.isChecked = startingValue
        button.onChange { onChange(button) }
        settingsContainer.add(button).pad(5f)
        settingsContainer.row()
    }

    private fun addCloseButton() {
        val closeButton = TextButton("Close", skin)
        closeButton.onChange { close() }
        settingsContainer.add(closeButton).padTop(20f)
    }

    private fun addTitle(title: String) {
        settingsContainer.add(title).top().padBottom(20f)
        settingsContainer.row()
    }

    private fun addSettingsContainer(): Table {
        val settingsContainer = Table(skin).padLeft(30f).padRight(30f).padBottom(5f).padTop(5f)
        settingsContainer.setBackground("panel-brown")
        add(settingsContainer).center()
        return settingsContainer
    }
}
