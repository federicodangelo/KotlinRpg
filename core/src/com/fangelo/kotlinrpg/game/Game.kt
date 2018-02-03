package com.fangelo.kotlinrpg.game

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.fangelo.kotlinrpg.game.components.avatar.Avatar
import com.fangelo.kotlinrpg.game.components.avatar.MainAvatar
import com.fangelo.kotlinrpg.game.systems.ProcessAvatarInputSystem
import com.fangelo.kotlinrpg.game.systems.UpdateAvatarAnimationSystem
import com.fangelo.libraries.ashley.components.*
import com.fangelo.libraries.ashley.data.ColliderShape
import com.fangelo.libraries.ashley.data.Sprite
import com.fangelo.libraries.ashley.systems.*
import ktx.ashley.entity
import ktx.ashley.get
import ktx.assets.load
import ktx.collections.toGdxArray
import java.util.*
import kotlin.math.log2
import kotlin.math.pow
import kotlin.math.round

private const val REF_HEIGHT_IN_TILES = 32

class Game {

    private val engine = PooledEngine()
    private val camera: Camera
    private var player: Entity? = null
    private val assetManager = AssetManager()
    private var debugEnabled = false

    init {

        loadAssets()

        camera = addCamera()

        engine.addSystem(UpdatePhysicsSystem())
        engine.addSystem(UpdateCameraSystem())
        engine.addSystem(ProcessAvatarInputSystem())
        engine.addSystem(UpdateAvatarAnimationSystem())
        engine.addSystem(UpdateVisualAnimationSystem())
        engine.addSystem(VisualTilemapRenderSystem())
        engine.addSystem(VisualSpriteRenderSystem())
        engine.addSystem(VisualDebugCollidersSystem())

        resize(Gdx.graphics.width, Gdx.graphics.height)

        addTilemap()
        addPlayer()
        addItems()

        disableDebug()

        camera.followTransform = player?.get()
    }

    private fun disableDebug() {
        debugEnabled = false
        engine.getSystem(VisualDebugCollidersSystem::class.java).setProcessing(false)
    }

    private fun enableDebug() {
        debugEnabled = true
        engine.getSystem(VisualDebugCollidersSystem::class.java).setProcessing(true)
    }

    private fun loadAssets() {
        assetManager.load<TextureAtlas>("tiles/tiles.atlas", TextureAtlasLoader.TextureAtlasParameter(true))
        assetManager.load<TextureAtlas>("items/items.atlas", TextureAtlasLoader.TextureAtlasParameter(true))
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

    private fun addItems() {
        val itemsAtlas = assetManager.get<TextureAtlas>("items/items.atlas")

        addSimpleItem(itemsAtlas, "rock1", 14.5f, 16f)

        addSimpleItem(itemsAtlas, "rock2", 18.5f, 16f)

        addTree(itemsAtlas, 20.5f, 16f)

        //getRandomPositions().forEach { pos -> addTree(itemsAtlas, pos.x, pos.y) }
        //getRandomPositions().forEach { pos -> addSimpleItem(itemsAtlas, "rock1", pos.x, pos.y) }
        //getRandomPositions().forEach { pos -> addSimpleItem(itemsAtlas, "rock2", pos.x, pos.y) }
    }

    private fun getRandomPositions(amount: Int = 25): Array<Vector2> {
        val rnd = Random()
        return Array(amount, { _ -> Vector2(rnd.nextFloat() * 28f + 2f, rnd.nextFloat() * 28f + 2f) })
    }

    private fun addSimpleItem(itemsAtlas: TextureAtlas, name: String, x: Float, y: Float) {
        engine.entity {
            with<Transform> {
                set(x, y)
            }
            with<Collider> {
                height = 0.4f
                setAnchorBottom()
            }
            with<VisualSprite> {
                add(Sprite(itemsAtlas.findRegion(name)).setAnchorBottom())
            }
        }
    }

    private fun addTree(itemsAtlas: TextureAtlas, x: Float, y: Float) {
        engine.entity {
            with<Transform> {
                set(x, y)
            }
            with<Collider> {
                set(0.8f, 0.4f)
                setAnchorBottom()
            }
            with<VisualSprite> {
                add(Sprite(itemsAtlas.findRegion("tree-trunk"), 2.0f, 2.3f))
                sprites[0].setAnchorBottom()
                sprites[0].offsetY += 0.25f

                add(Sprite(itemsAtlas.findRegion("tree-leaves"), 3f, 2.5f, 0f, 0f))
                sprites[1].setAnchorBottom()
                sprites[1].offsetY -= 1.75f
            }
        }
    }


    private fun addPlayer() {

        val playersAtlas = assetManager.get<TextureAtlas>("players/players.atlas")
        val playerRegion = playersAtlas.findRegion("player-walk-south-0")
        val playerAnimations = buildPlayerAnimations("player", playersAtlas)

        this.player = engine.entity {
            with<Transform> {
                set(16.5f, 16f)
            }
            with<Rigidbody>()
            with<VisualSprite> {
                add(Sprite(playerRegion, 2f, 2f, 0f, -0.7f))
            }
            with<VisualAnimation> {
                set(playerAnimations, "walk-east")
            }
            with<Avatar>()
            with<MainAvatar>()
            with<Collider> {
                set(0.6f, 0.3f, 0f, 0.14f)
            }
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            if (debugEnabled)
                disableDebug()
            else
                enableDebug()
        }


        engine.update(deltaTime)
    }

    fun resize(width: Int, height: Int) {

        var scale = height.toDouble() / REF_HEIGHT_IN_TILES.toDouble()

        scale = roundToNearestPowOfTwo(scale)

        scale = 1.0 / scale

        camera.resize((width.toDouble() * scale).toInt(), (height.toDouble() * scale).toInt())
    }

    private fun roundToNearestPowOfTwo(scale: Double): Double {

        var n = log2(scale)

        n = round(n)

        n = 2.0.pow(n)

        return n
    }

    fun dispose() {
        assetManager.dispose()
    }
}