package com.fangelo.libraries.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import ktx.log.error

abstract class Screen {

    protected val skin: Skin

    private val mainTable: CustomTable

    val width : Float
        get() = mainTable.width

    val height : Float
        get() = mainTable.height

    init {
        mainTable = CustomTable(this)
        mainTable.setFillParent(true)
        skin = ScreenManager.skin
        mainTable.skin = ScreenManager.skin
    }

    internal fun internalShow(stageTable: Table) {
        stageTable.addActor(mainTable)
        onShow()
    }

    protected open fun onShow() {
        //Called when the screen is shown
    }

    internal fun internalHide(stageTable: Table) {
        stageTable.removeActor(mainTable)
        onHide()
    }

    protected open fun onHide() {
        //Called when the screen is hidden
    }

    internal fun internalResize(width: Int, height: Int) {
        onResize(width, height)
    }

    protected open fun onResize(width: Int, height: Int) {
        //Called when the screen is resized
    }

    protected open fun onUpdate(deltaTime : Float) {

    }

    fun close() {
        if (ScreenManager.activeScreen != this) {
            error { "Cannot close non-top screen" }
            return
        }

        ScreenManager.pop()
    }

    protected fun <T : Actor> add(actor: T): Cell<T> {
        return mainTable.add(actor)
    }

    protected fun add(text: CharSequence): Cell<Label> {
        return mainTable.add(text)
    }

    protected fun setBackground(drawableName: String) {
        mainTable.setBackground(drawableName)
    }

    protected fun row() {
        mainTable.row()
    }

    protected open fun onLayout() {

    }

    private class CustomTable(val screen: Screen) : Table() {

        override fun layout() {
            super.layout()
            screen.onLayout()
        }

        override fun act(delta: Float) {
            super.act(delta)
            screen.onUpdate(delta)
        }
    }

}
