package com.fangelo.kotlinrpg.game

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.fangelo.kotlinrpg.game.components.avatar.Avatar
import com.fangelo.kotlinrpg.game.components.avatar.MainAvatar
import com.fangelo.kotlinrpg.game.systems.AvatarAnimationSystem
import com.fangelo.kotlinrpg.game.systems.MainAvatarInputSystem
import com.fangelo.libraries.ashley.components.*
import com.fangelo.libraries.ashley.systems.*
import ktx.ashley.entity
import ktx.ashley.get
import ktx.assets.load
import ktx.collections.toGdxArray
import java.util.*

private const val REF_HEIGHT_IN_TILES = 32

class Game {

    private val engine = PooledEngine()
    private val camera: Camera
    private var player: Entity? = null
    private val assetManager = AssetManager()

    init {

        loadAssets()

        camera = addCamera()

        engine.addSystem(UpdateCameraSystem())
        engine.addSystem(MainAvatarInputSystem())
        engine.addSystem(MovementSystem())
        engine.addSystem(AvatarAnimationSystem())
        engine.addSystem(UpdateVisualAnimationSystem())
        engine.addSystem(VisualTilemapRenderSystem())
        engine.addSystem(VisualTextureRenderSystem())

        resize(Gdx.graphics.width, Gdx.graphics.height)

        addTilemap()
        addPlayer()

        camera.followTransform = player?.get()
    }

    private fun loadAssets() {
        assetManager.load<TextureAtlas>("tiles/tiles.atlas", TextureAtlasLoader.TextureAtlasParameter(true))
        assetManager.load<TextureAtlas>("players/players.atlas", TextureAtlasLoader.TextureAtlasParameter(true))
        assetManager.finishLoading()
    }

    private fun addTilemap() {
        val tilesetAtlas = assetManager.get<TextureAtlas>("tiles/tiles.atlas")
        val rnd = Random()
        val tileset = buildTileset(tilesetAtlas)
        val sizeX = 32
        val sizeY = 32
        val tiles = Array(sizeX * sizeY, { rnd.nextInt(tileset.size) })

        engine.entity {
            with<Transform>()
            with<Tilemap> {
                set(sizeX, sizeY, tiles)
            }
            with<VisualTileset> {
                set(tileset)
            }
        }
    }

    private fun buildTileset(tilesetAtlas: TextureAtlas): Array<TextureRegion> {
        return arrayOf(
                tilesetAtlas.findRegion("grass-center-0"), tilesetAtlas.findRegion("grass-center-1"),
                tilesetAtlas.findRegion("grass-center-2"), tilesetAtlas.findRegion("grass-center-3"))
    }

    private fun addCamera(): Camera {

        val cameraEntity = engine.entity {
            with<Transform> {
                set(16.5f, 16.5f)
            }
            with<Camera>()
        }

        return cameraEntity.get()!!
    }

    private fun addPlayer() {

        val playersAtlas = assetManager.get<TextureAtlas>("players/players.atlas")
        val playerRegion = playersAtlas.findRegion("player-walk-south-0")
        val playerAnimations = buildPlayerAnimations("player", playersAtlas)

        this.player = engine.entity {
            with<Transform> {
                set(16.5f, 16.5f)
            }
            with<Movement>()
            with<VisualTexture> {
                set(playerRegion, 2f, 2f)
            }
            with<VisualAnimation> {
                set(playerAnimations, "walk-east")
            }
            with<Avatar>()
            with<MainAvatar>()
        }
    }

    private fun buildPlayerAnimations(playerName: String, playersAtlas: TextureAtlas): Map<String, Animation<TextureRegion>> {
        val animations = mutableMapOf<String, Animation<TextureRegion>>()

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

        val scale = REF_HEIGHT_IN_TILES.toFloat() / height.toFloat()

        camera.resize((width.toFloat() * scale).toInt(), (height.toFloat() * scale).toInt())
    }

    fun dispose() {
        assetManager.dispose()
    }
}