package com.fangelo.libraries.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.fangelo.libraries.debug.DebugSettings
import com.fangelo.libraries.glprofiler.GLProfiler
import ktx.assets.file
import ktx.log.info
import java.util.*

object ScreenManager {
    val stage: Stage

    val textureAtlas: TextureAtlas

    val skin: Skin

    var activeScreen: Screen? = null
        private set

    var activeDialog: Dialog? = null
        private set

    private val spriteBatch: SpriteBatch

    private val stageViewport: ScalingViewport
    private val stageTable: Table

    private val stageNotifications: Stage
    private val stageNotificationsTable: Table

    private val stageHints: Stage
    private val stageHintsTable: Table

    private val stageDebug: Stage
    private val stageDebugTable: Table

    private val screensStack: ArrayDeque<Screen> = ArrayDeque()

    private val statsLabel: Label
    private val statsBuilder = StringBuilder()

    private var statsUpdateTime: Float = 0.0f
    private var statsUpdateTimeFrames: Int = 0

    init {

        textureAtlas = TextureAtlas(file("ui/ui.atlas"));
        skin = Skin(file("ui/ui.json"), textureAtlas)
        stageViewport = ScalingViewport(Scaling.stretch, (Gdx.graphics.width / 2).toFloat(), (Gdx.graphics.height / 2).toFloat())
        spriteBatch = SpriteBatch()

        stageTable = Table(skin)
        stageTable.setFillParent(true)
        stage = Stage(stageViewport, spriteBatch)
        stage.addActor(stageTable)

        stageNotificationsTable = Table(skin)
        stageNotificationsTable.setFillParent(true)
        stageNotifications = Stage(stageViewport, spriteBatch)
        stageNotifications.addActor(stageNotificationsTable)

        stageHintsTable = Table(skin)
        stageHintsTable.setFillParent(true)
        stageHints = Stage(stageViewport, spriteBatch)
        stageHints.addActor(stageHintsTable)

        stageDebugTable = Table(skin)
        stageDebugTable.setFillParent(true)
        stageDebug = Stage(stageViewport, spriteBatch)
        stageDebug.addActor(stageDebugTable)

        statsLabel = Label("", skin)
        stageDebugTable.add<Label>(statsLabel).expand().left().top().pad(5f)
    }

    fun dispose() {
        stage.dispose()

        stageNotifications.dispose()

        stageHints.dispose()

        stageDebug.dispose()

        spriteBatch.dispose()

        skin.dispose()
    }

    fun resize(width: Int, height: Int) {
        stageViewport.setWorldSize((width / 2).toFloat(), (height / 2).toFloat())
        stageViewport.update(width, height, true)

        activeScreen?.internalResize(width, height)
    }

    fun updateAndDraw() {
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()

        stageHints.act(Gdx.graphics.deltaTime)
        stageHints.draw()

        stageNotifications.act(Gdx.graphics.deltaTime)
        stageNotifications.draw()

        if (DebugSettings.showFps) {
            updateDebug(Gdx.graphics.deltaTime)
            stageDebug.act(Gdx.graphics.deltaTime)
            stageDebug.draw()
        }

        //stageTable.drawDebug(shapeRenderer); // This is optional, but enables debug lines for tables.
    }

    private fun updateDebug(delta: Float) {
        statsUpdateTime += delta
        statsUpdateTimeFrames++

        if (statsUpdateTime > 1.0f) {

            statsBuilder.setLength(0)
            statsBuilder.append("fps: ")
            statsBuilder.append(Gdx.graphics.framesPerSecond)

            if (GLProfiler.isEnabled) {
                statsBuilder.append(" dc: ")
                statsBuilder.append(GLProfiler.drawCalls / statsUpdateTimeFrames)
                statsBuilder.append(" vc: ")
                statsBuilder.append((GLProfiler.vertexCount.total / statsUpdateTimeFrames).toInt())
                GLProfiler.reset()
            }

            statsBuilder.append("\nmem: ")
            statsBuilder.append(Gdx.app.nativeHeap / 1024)
            statsBuilder.append("K (nat) ")
            statsBuilder.append(Gdx.app.javaHeap / 1024)
            statsBuilder.append("K (java)")

            statsLabel.setText(statsBuilder.toString())

            statsUpdateTimeFrames = 0
            statsUpdateTime = 0.0f
        }
    }

    fun show(screen: Screen) {

        val currentScreen = this.activeScreen

        if (currentScreen != null) {
            currentScreen.internalHide(stageTable)
        }

        screensStack.clear()

        activeScreen = screen
        screen.internalShow(stageTable)
    }

    fun push(screen: Screen) {

        val currentScreen = this.activeScreen

        if (currentScreen != null) {
            currentScreen.internalHide(stageTable)
            screensStack.push(currentScreen)
        }

        activeScreen = screen
        screen.internalShow(stageTable)
    }

    fun canPop(): Boolean {
        return screensStack.size > 0
    }

    fun pop() {

        if (!canPop())
            return

        activeScreen?.internalHide(stageTable)

        val newActiveScreen = screensStack.pop()

        newActiveScreen?.internalShow(stageTable)

        activeScreen = newActiveScreen
    }

    fun showNotification(message: String) {

        info("NOTIFICATION") { message }

        val notification = Table(skin)
        notification.setBackground("button-brown")
        notification.add(message)
        notification.addAction(Actions.sequence(Actions.fadeIn(0.25f), Actions.delay(3.0f), Actions.fadeOut(0.25f), Actions.removeActor()))

        stageNotificationsTable.add(notification).fillX().expandX().pad(0f, 5f, 0f, 5f)
    }

    fun showHint(message: String) {

        //Remove existing tips
        stageHintsTable.clear()

        val hint = Table(skin)
        hint.setBackground("button-brown")
        hint.add(message)
        hint.addAction(Actions.sequence(Actions.fadeIn(0.25f), Actions.delay(2.0f), Actions.fadeOut(0.25f), Actions.removeActor()))

        stageHintsTable.add(hint).top().expand().pad(5f)
    }

    fun show(dialog: Dialog): Dialog {
        activeDialog = dialog
        dialog.internalShow(this)
        return dialog
    }

    internal fun internalDialogClosed(dialog: Dialog) {
        if (activeDialog === dialog) {
            activeDialog = null
        }
    }
}
