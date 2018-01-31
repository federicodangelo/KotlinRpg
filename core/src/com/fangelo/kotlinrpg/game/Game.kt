package com.fangelo.kotlinrpg.game

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.fangelo.kotlinrpg.game.components.*
import com.fangelo.kotlinrpg.game.systems.*
import ktx.assets.toInternalFile
import ktx.collections.toGdxArray
import java.util.*

class Game {

    val CAMERA_SCALE = 8

    private val engine = PooledEngine()
    private val camera: Camera
    private var player: Entity? = null

    init {

        camera = Camera()
        camera.moveTo(16.5f, 16.5f)

        engine.addSystem(MainAvatarInputSystem())
        engine.addSystem(MovementSystem())
        engine.addSystem(AvatarAnimationSystem())
        engine.addSystem(UpdateVisualAnimationSystem())
        engine.addSystem(VisualTilemapRenderSystem())
        engine.addSystem(VisualTextureRenderSystem())

        resize(Gdx.graphics.width, Gdx.graphics.height)

        addCamera()
        addTilemap()
        addPlayer()
    }

    private fun addTilemap() {
        val tilemapAtlas = TextureAtlas("tiles/tiles.atlas".toInternalFile(), true)

        val tilemapEntity = engine.createEntity()

        val rnd = Random()

        tilemapEntity.add(Transform(0f, 0f))
        tilemapEntity.add(Tilemap(32, 32, Array(32 * 32, { rnd.nextInt(4) })))
        tilemapEntity.add(VisualTilemap(arrayOf(tilemapAtlas.findRegion("grass-center-0"), tilemapAtlas.findRegion("grass-center-1"),
                tilemapAtlas.findRegion("grass-center-2"), tilemapAtlas.findRegion("grass-center-3"))))

        engine.addEntity(tilemapEntity)
    }

    private fun addCamera() {
        val mainCameraEntity = engine.createEntity()
        mainCameraEntity.add(camera)
        engine.addEntity(mainCameraEntity)
    }

    private fun addPlayer() {

        val playersAtlas = TextureAtlas("players/players.atlas".toInternalFile(), true)
        val playerRegion = playersAtlas.findRegion("player-walk-south-0")
        val playerAnimations = buildAnimations("player", playersAtlas)

        val player = engine.createEntity()
        player.add(Transform(16.5f, 16.5f))
        player.add(Movement(0f, 0f))
        player.add(VisualTexture(playerRegion, 2f, 2f))
        player.add(VisualAnimation(playerAnimations, "walk-east"))
        player.add(MainAvatar())
        engine.addEntity(player)

        this.player = player
    }

    private fun buildAnimations(playerName: String, playersAtlas: TextureAtlas): Map<String, Animation<TextureRegion>> {
        val animations = mutableMapOf<String, Animation<TextureRegion>>();

        addAnimations(animations, playersAtlas, playerName, "walk-north", 9, Animation.PlayMode.LOOP)
        addAnimations(animations, playersAtlas, playerName, "walk-west", 9, Animation.PlayMode.LOOP)
        addAnimations(animations, playersAtlas, playerName, "walk-south", 9, Animation.PlayMode.LOOP)
        addAnimations(animations, playersAtlas, playerName, "walk-east", 9, Animation.PlayMode.LOOP)

        return animations
    }

    private fun addAnimations(animations: MutableMap<String, Animation<TextureRegion>>, atlas: TextureAtlas, playerName: String, animationPrefix: String,
                              totalFrames: Int, playMode: Animation.PlayMode) {

        val frames: MutableList<TextureRegion> = mutableListOf()

        for (frameNumber in 0 until totalFrames) {
            val frame = atlas.findRegion("$playerName-$animationPrefix-$frameNumber")!!
            frames.add(frame)
        }

        val animation = Animation<TextureRegion>(0.1f, frames.toGdxArray(), playMode)

        animations[animationPrefix] = animation
    }

    fun update(deltaTime: Float) {
        engine.update(deltaTime)
    }

    fun resize(width: Int, height: Int) {
        camera.resize(width / CAMERA_SCALE, height / CAMERA_SCALE)
    }

}