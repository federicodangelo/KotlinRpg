package com.fangelo.kotlinrpg

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.GL20
import com.fangelo.kotlinrpg.ui.dialog.ConfirmDialog
import com.fangelo.kotlinrpg.ui.screen.Screens
import com.fangelo.libraries.ui.DialogResult
import com.fangelo.libraries.ui.ScreenManager
import ktx.app.KtxApplicationAdapter

class MyGdxGame : KtxApplicationAdapter {

    override fun create() {
        initInput()
        showFirstScreen()
    }

    private fun showFirstScreen() {
        ScreenManager.show(Screens.mainMenuScreen)
    }

    private fun initInput() {
        Gdx.input.isCatchBackKey = true
        Gdx.input.inputProcessor = buildInputProcessor()
    }

    private fun buildInputProcessor(): InputProcessor {
        val inputMultiplexer = InputMultiplexer()

        inputMultiplexer.addProcessor(ScreenManager.stage)
        //inputMultiplexer.addProcessor(GestureDetector(worldInputHandler)) //gestures first
        //inputMultiplexer.addProcessor(worldInputHandler) //base later
        inputMultiplexer.addProcessor(object : InputAdapter() {
            override fun keyDown(keycode: Int): Boolean {
                if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
                    if (ScreenManager.canPop()) {
                        ScreenManager.pop()
                    } else {
                        ScreenManager.show(ConfirmDialog("Exit", "Exit game?")).onClosed += { res ->
                            if (res == DialogResult.Yes) {
                                Gdx.app.exit()
                            }
                        }
                    }
                    return true
                }
                return false
            }
        })
        return inputMultiplexer
    }

    override fun resize(width: Int, height: Int) {

        ScreenManager.resize(width, height)

        //world.internalResize(width, height)
        //super.internalResize(width, height)
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
        PlatformAdapter.dispose()
    }
}
