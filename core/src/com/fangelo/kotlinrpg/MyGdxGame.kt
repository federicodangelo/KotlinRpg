package com.fangelo.kotlinrpg

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.GL20
import com.fangelo.kotlinrpg.ui.screen.Screens
import com.fangelo.libraries.glprofiler.GLProfilerSingleton
import com.fangelo.libraries.ui.ScreenManager
import ktx.app.KtxApplicationAdapter

class MyGdxGame : KtxApplicationAdapter {

    override fun create() {
        GLProfilerSingleton.profiler.enable()
        ScreenManager.init()
        Screens.init()

        val inputMultiplexer = InputMultiplexer()

        inputMultiplexer.addProcessor(ScreenManager.getStage())
        //inputMultiplexer.addProcessor(GestureDetector(worldInputHandler)) //gestures first
        //inputMultiplexer.addProcessor(worldInputHandler) //base later
        inputMultiplexer.addProcessor(object : InputAdapter() {
            override fun keyDown(keycode: Int): Boolean {
                if (keycode == Input.Keys.BACK) {
                    if (ScreenManager.getActiveScreen() != null)
                        ScreenManager.getActiveScreen().onBackButtonPressed()
                    return true
                }
                return false
            }
        })

        Gdx.input.isCatchBackKey = true

        Gdx.input.inputProcessor = inputMultiplexer

        ScreenManager.show(Screens.mainMenuScreen)
    }

    override fun resize(width: Int, height: Int) {

        ScreenManager.resize(width, height)

        //world.resize(width, height)
        //super.resize(width, height)
    }

    override fun render() {
        //Draw
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        //if (world.isInitialized()) {
        //    //Draw world
        //    world.draw()
        //}

        //Draw UI
        ScreenManager.updateAndDraw()
    }

    override fun dispose() {
        ScreenManager.dispose()
        Screens.dispose()
        PlatformAdapter.dispose()
    }
}
