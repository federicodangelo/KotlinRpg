package com.fangelo.kotlinrpg.ui.screen

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.fangelo.libraries.ui.Screen
import com.fangelo.libraries.ui.ScreenManager
import ktx.actors.onChange

class InGameScreen : Screen() {

    private val topLeftContainer: Table
    private val topRightContainer: Table
    private val bottomRightContainer: Table
    private val bottomLeftContainer: Table

    private val container: WidgetGroup


    init {

        container = WidgetGroup()
        add(container).fill()

        topLeftContainer = Table()
        topRightContainer = Table()
        bottomRightContainer = Table()
        bottomLeftContainer = Table()

        container.addActor(topLeftContainer)
        container.addActor(topRightContainer)
        container.addActor(bottomRightContainer)
        container.addActor(bottomLeftContainer)


        addExitButton()
    }

    override fun onLayout() {

        container.width = width
        container.height = height

        topLeftContainer.setPosition(
                (-container.width / 2 + topLeftContainer.prefWidth / 2).toInt().toFloat(),
                (container.height / 2 - topLeftContainer.prefHeight / 2).toInt().toFloat()
        )

        topRightContainer.setPosition(
                (container.width / 2 - topRightContainer.prefWidth / 2).toInt().toFloat(),
                (container.height / 2 - topRightContainer.prefHeight / 2).toInt().toFloat()
        )

        bottomLeftContainer.setPosition(
                (-container.width / 2 + bottomLeftContainer.prefWidth / 2).toInt().toFloat(),
                (-container.height / 2 + bottomLeftContainer.prefHeight / 2).toInt().toFloat()
        )

        bottomRightContainer.setPosition(
                (container.width / 2 - bottomRightContainer.prefWidth / 2).toInt().toFloat(),
                (-container.height / 2 + bottomRightContainer.prefHeight / 2).toInt().toFloat()
        )
    }



    private fun addExitButton() {
        val exitButton = TextButton("Exit", skin)

        exitButton.onChange {
            returnToMainScreen()
        }

        topRightContainer.add(exitButton).minWidth(75f).padTop(5f).padRight(5f)
    }

    private fun returnToMainScreen() {
        ScreenManager.show(MainMenuScreen())
    }
}