package com.fangelo.kotlinrpg.game.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.fangelo.kotlinrpg.game.components.Transform
import com.fangelo.kotlinrpg.game.components.Visual
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class RenderSystem : EntitySystem(), Camera {
    private lateinit var entities: ImmutableArray<Entity>

    private var cameraChanged: Boolean = true

    private val transform = mapperFor<Transform>()
    private val visual = mapperFor<Visual>()

    private var camera = OrthographicCamera()
    private val batch: SpriteBatch

    override var cameraPositionX: Float = 0f
        set(value) {
            field = value
            cameraChanged = true
        }

    override var cameraPositionY: Float = 0f
        set(value) {
            field = value
            cameraChanged = true
        }

    override var zoom: Float
        get() = camera.zoom
        set(value) {
            camera.zoom = value
            cameraChanged = true
        }

    init {
        camera = OrthographicCamera()
        camera.setToOrtho(true)
        camera.zoom = 0.25f
        camera.position.set(0f, 0f, 0f)

        batch = SpriteBatch()
        batch.projectionMatrix = camera.combined
    }

    override fun moveTo(x: Float, y: Float) {
        cameraPositionX = x
        cameraPositionY = y
    }

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(Transform::class, Visual::class).get())
    }

    override fun removedFromEngine(engine: Engine) {

    }

    fun resize(width: Int, height: Int) {
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        camera.update()
    }


    override fun update(deltaTime: Float) {

        beginRender()

        drawEntities()

        endRender()
    }

    private fun drawEntities() {
        var transform: Transform
        var visual: Visual

        for (e in entities) {

            transform = this.transform.get(e)
            visual = this.visual.get(e)

            batch.draw(visual.texture, transform.x - visual.width * 0.5f, transform.y - visual.height * 0.5f, visual.width, visual.height)
        }
    }

    private fun beginRender() {

        camera.update()

        if (cameraChanged) {
            batch.projectionMatrix = camera.combined
            cameraChanged = false
        }

        batch.begin()
    }

    private fun endRender() {
        batch.end()
    }
}