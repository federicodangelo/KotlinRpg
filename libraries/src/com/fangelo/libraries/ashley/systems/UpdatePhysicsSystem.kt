package com.fangelo.libraries.ashley.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.fangelo.libraries.ashley.components.Collider
import com.fangelo.libraries.ashley.components.Rigidbody
import com.fangelo.libraries.ashley.components.Transform
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class UpdatePhysicsSystem : IteratingSystem(allOf(Transform::class, Rigidbody::class).get()) {
    private val transform = mapperFor<Transform>()
    private val rigidbody = mapperFor<Rigidbody>()
    private val collider = mapperFor<Collider>()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = transform.get(entity)
        val rigidbody = rigidbody.get(entity)
        val collider : Collider? = collider.get(entity)

        val dx = rigidbody.velocityX * deltaTime
        val dy = rigidbody.velocityY * deltaTime

        val oldX = transform.x
        val oldY = transform.y

        val newX = oldX + dx
        val newY = oldY + dy

        transform.x = newX
        transform.y = newY
    }
}