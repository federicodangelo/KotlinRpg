package com.fangelo.libraries.ashley.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.fangelo.libraries.ashley.components.Movement
import com.fangelo.libraries.ashley.components.Transform
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class MovementSystem : IteratingSystem(allOf(Transform::class, Movement::class).get()) {
    private val transform = mapperFor<Transform>()
    private val movement = mapperFor<Movement>()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = transform.get(entity)
        val movement = movement.get(entity)

        transform.x += movement.velocityX * deltaTime
        transform.y += movement.velocityY * deltaTime
    }
}