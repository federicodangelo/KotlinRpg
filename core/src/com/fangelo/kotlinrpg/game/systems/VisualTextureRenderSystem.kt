package com.fangelo.kotlinrpg.game.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.fangelo.kotlinrpg.game.components.Camera
import com.fangelo.kotlinrpg.game.components.Transform
import com.fangelo.kotlinrpg.game.components.VisualTexture
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class VisualTextureRenderSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>
    private lateinit var cameras: ImmutableArray<Entity>

    private val transform = mapperFor<Transform>()
    private val visual = mapperFor<VisualTexture>()
    private val camera = mapperFor<Camera>()

    private val batch: SpriteBatch

    init {
        batch = SpriteBatch()
    }

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(Transform::class, VisualTexture::class).get())
        cameras = engine.getEntitiesFor(allOf(Camera::class).get())
    }


    override fun removedFromEngine(engine: Engine) {
    }


    override fun update(deltaTime: Float) {

        var camera: Camera

        for (c in cameras) {
            camera = this.camera.get(c)

            beginRender(camera)

            drawEntities()

            endRender()
        }
    }

    private fun drawEntities() {
        var transform: Transform
        var visualTexture: VisualTexture

        for (e in entities) {

            transform = this.transform.get(e)
            visualTexture = this.visual.get(e)
            val texture = visualTexture.texture;

            var targetX = transform.x - visualTexture.width * 0.5f
            var targetY = transform.y - visualTexture.height * 0.5f
            var width = visualTexture.width
            var height = visualTexture.height

            if (texture is TextureAtlas.AtlasRegion) {

                width *= texture.packedWidth.toFloat() / texture.originalWidth.toFloat()
                height *= texture.packedHeight.toFloat() / texture.originalHeight.toFloat()

                targetX += texture.offsetX / texture.originalWidth.toFloat()
                targetY += texture.offsetY / texture.originalHeight.toFloat()

            }

            batch.draw(texture, targetX, targetY, width, height)
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