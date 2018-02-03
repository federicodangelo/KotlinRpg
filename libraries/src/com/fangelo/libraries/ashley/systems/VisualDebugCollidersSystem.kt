package com.fangelo.libraries.ashley.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.fangelo.libraries.ashley.components.Camera
import com.fangelo.libraries.ashley.components.Collider
import com.fangelo.libraries.ashley.components.Transform
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class VisualDebugCollidersSystem : EntitySystem() {
    private lateinit var entities: ImmutableArray<Entity>
    private lateinit var cameras: ImmutableArray<Entity>

    private val transform = mapperFor<Transform>()
    private val collider = mapperFor<Collider>()
    private val camera = mapperFor<Camera>()

    private val shapeRenderer: ShapeRenderer = ShapeRenderer()

    init {
        shapeRenderer.color = Color.RED
        shapeRenderer.setAutoShapeType(true)
    }

    override fun addedToEngine(engine: Engine) {
        entities = engine.getEntitiesFor(allOf(Transform::class, Collider::class).get())
        cameras = engine.getEntitiesFor(allOf(Camera::class).get())
    }

    override fun removedFromEngine(engine: Engine) {
    }

    override fun update(deltaTime: Float) {
        var camera: Camera
        for (ec in cameras) {
            camera = this.camera.get(ec)
            beginRender(camera)
            drawDebug()
            endRender()
        }
    }

    private fun drawDebug() {
        var transform: Transform
        var collider: Collider

        for (e in entities) {
            transform = this.transform.get(e)
            collider = this.collider.get(e)
            drawCollider(collider, transform.x + collider.offsetX, transform.y + collider.offsetY)
        }
    }

    private fun drawCollider(collider: Collider, x: Float, y: Float) {
        shapeRenderer.rect(x - collider.width * 0.5f, y - collider.height * 0.5f, collider.width, collider.height)
    }

    private fun beginRender(camera: Camera) {
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.begin()
    }

    private fun endRender() {
        shapeRenderer.end()
    }
}