package com.fangelo.libraries.ashley.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.fangelo.libraries.ashley.components.Camera
import com.fangelo.libraries.ashley.components.Transform
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class UpdateCameraSystem : IteratingSystem(allOf(Transform::class, Camera::class).get()) {
    private val transform = mapperFor<Transform>()
    private val camera = mapperFor<Camera>()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = transform.get(entity)
        val camera = camera.get(entity)

        val followTransform = camera.followTransform

        if (followTransform != null) {
            transform.set(followTransform.x, followTransform.y)
        }

        camera.update(transform.x, transform.y)
    }
}