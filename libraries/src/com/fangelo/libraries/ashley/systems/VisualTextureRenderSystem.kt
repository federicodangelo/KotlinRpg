package com.fangelo.libraries.ashley.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.fangelo.libraries.ashley.components.Camera
import com.fangelo.libraries.ashley.components.Transform
import com.fangelo.libraries.ashley.components.VisualSprite
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class VisualTextureRenderSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>
    private lateinit var cameras: ImmutableArray<Entity>
    private var sortedEntities = mutableListOf<Entity>()

    private val transform = mapperFor<Transform>()
    private val visual = mapperFor<VisualSprite>()
    private val camera = mapperFor<Camera>()

    private val batch: SpriteBatch

    init {
        batch = SpriteBatch()
    }

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(Transform::class, VisualSprite::class).get())
        cameras = engine.getEntitiesFor(allOf(Camera::class).get())
    }

    override fun removedFromEngine(engine: Engine) {
    }

    override fun update(deltaTime: Float) {

        sortEntities()

        var camera: Camera

        for (c in cameras) {
            camera = this.camera.get(c)

            beginRender(camera)

            drawEntities()

            endRender()
        }
    }

    private fun sortEntities() {

        sortedEntities.clear()
        sortedEntities.addAll(entities)
        sortedEntities.sortBy { transform.get(it).y }


    }

    private fun drawEntities() {
        var transform: Transform
        var visualSprite: VisualSprite

        for (e in sortedEntities) {

            transform = this.transform.get(e)
            visualSprite = this.visual.get(e)

            for (item in visualSprite.sprites) {

                val texture = item.texture

                var targetX = transform.x - item.width * 0.5f + item.offsetX
                var targetY = transform.y - item.height * 0.5f + item.offsetY
                var width = item.width
                var height = item.height

                if (texture is TextureAtlas.AtlasRegion) {

                    width *= texture.packedWidth.toFloat() / texture.originalWidth.toFloat()
                    height *= texture.packedHeight.toFloat() / texture.originalHeight.toFloat()

                    targetX += texture.offsetX / texture.originalWidth.toFloat()
                    targetY += texture.offsetY / texture.originalHeight.toFloat()

                }

                batch.draw(texture, targetX, targetY, width, height)
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