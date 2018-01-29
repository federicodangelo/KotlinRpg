package com.fangelo.kotlinrpg.game

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.fangelo.kotlinrpg.game.components.Movement
import com.fangelo.kotlinrpg.game.components.Transform
import com.fangelo.kotlinrpg.game.components.Visual
import com.fangelo.kotlinrpg.game.systems.MovementSystem
import com.fangelo.kotlinrpg.game.systems.RenderSystem
import ktx.assets.toInternalFile


class Game {

    val CAMERA_SCALE = 16

    val engine = PooledEngine()
    val camera = OrthographicCamera(640f, 480f)
    val renderSystem: RenderSystem
    var player: Entity? = null

    init {

        renderSystem = RenderSystem()

        engine.addSystem(MovementSystem())
        engine.addSystem(renderSystem)

        resize(Gdx.graphics.width, Gdx.graphics.height)

        addPlayer()
    }

    private fun addPlayer() {

        val atlas = TextureAtlas("images/images.atlas".toInternalFile(), true)
        val playerRegion = atlas.findRegion("player")

        val player = engine.createEntity()
        player.add(Visual(playerRegion))
        player.add(Transform(0f, 0f))
        player.add(Movement(0f, 0f))
        engine.addEntity(player)

        this.player = player
    }

    fun update(deltaTime: Float) {
        engine.update(deltaTime)
    }

    fun resize(width: Int, height: Int) {
        renderSystem.resize(width / CAMERA_SCALE, height / CAMERA_SCALE)
    }

}