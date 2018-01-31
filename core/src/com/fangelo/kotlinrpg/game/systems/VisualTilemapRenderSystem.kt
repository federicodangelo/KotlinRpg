package com.fangelo.kotlinrpg.game.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.fangelo.kotlinrpg.game.components.Camera
import com.fangelo.kotlinrpg.game.components.Tilemap
import com.fangelo.kotlinrpg.game.components.Transform
import com.fangelo.kotlinrpg.game.components.VisualTilemap
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class VisualTilemapRenderSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>
    private lateinit var cameras: ImmutableArray<Entity>

    private val transform = mapperFor<Transform>()
    private val visualTilemap = mapperFor<VisualTilemap>()
    private val tilemap = mapperFor<Tilemap>()
    private val camera = mapperFor<Camera>()

    private val batch: SpriteBatch

    init {
        batch = SpriteBatch()
    }

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(Transform::class, VisualTilemap::class, Tilemap::class).get())
        cameras = engine.getEntitiesFor(allOf(Camera::class).get())
    }


    override fun removedFromEngine(engine: Engine) {
    }


    override fun update(deltaTime: Float) {

        var camera: Camera

        for (c in cameras) {
            camera = this.camera.get(c)

            beginRender(camera)

            drawTilemaps()

            endRender()
        }
    }

    private fun drawTilemaps() {
        var transform: Transform
        var tilemap: Tilemap
        var visualTilemap: VisualTilemap

        for (e in entities) {

            transform = this.transform.get(e)
            tilemap = this.tilemap.get(e)
            visualTilemap = this.visualTilemap.get(e)

            val offsetX = transform.x
            val offsetY = transform.y

            for (y in 0 until tilemap.sizeY) {
                for (x in 0 until tilemap.sizeX) {
                    val tile = tilemap.getTile(x, y)
                    val tileTexture = visualTilemap.getTileTexture(tile)

                    batch.draw(tileTexture, offsetX + x, offsetY + y, 1f, 1f)
                }
            }
        }
    }

    private fun beginRender(camera: Camera) {
        batch.projectionMatrix = camera.combined
        batch.begin()
    }

    private fun endRender() {
        batch.end()
    }
}