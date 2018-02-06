package com.fangelo.libraries.ashley.systems

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.utils.ImmutableArray
import com.fangelo.libraries.ashley.components.Collider
import com.fangelo.libraries.ashley.components.Rigidbody
import com.fangelo.libraries.ashley.components.Transform
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class UpdatePhysicsSystem : EntitySystem() {

    private lateinit var rigidbodies: ImmutableArray<Entity>
    private lateinit var colliders: ImmutableArray<Entity>

    private val transform = mapperFor<Transform>()
    private val rigidbody = mapperFor<Rigidbody>()
    private val collider = mapperFor<Collider>()

    override fun addedToEngine(engine: Engine) {
        rigidbodies = engine.getEntitiesFor(allOf(Transform::class, Rigidbody::class).get())
        colliders = engine.getEntitiesFor(allOf(Transform::class, Collider::class).get())
    }

    override fun update(deltaTime: Float) {
        for (re in rigidbodies) {
            processEntity(re, deltaTime)
        }
    }

    private fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = transform.get(entity)
        val rigidbody = rigidbody.get(entity)
        val collider: Collider? = collider.get(entity)

        val dx = rigidbody.velocityX * deltaTime
        val dy = rigidbody.velocityY * deltaTime

        val oldX = transform.x
        val oldY = transform.y

        var newX = oldX + dx
        var newY = oldY + dy

        if (collider != null) {
            if (checkAgainstColliders(collider, newX, newY)) {
                if (!checkAgainstColliders(collider, oldX, newY)) {
                    newX = oldX
                } else if (!checkAgainstColliders(collider, newX, oldY)) {
                    newY = oldY
                } else {
                    newX = oldX
                    newY = oldY
                }
            }
        }

        transform.x = newX
        transform.y = newY
    }

    private fun checkAgainstColliders(collider: Collider, newX: Float, newY: Float): Boolean {
        for (ce in colliders) {

            val otherTransform = this.transform.get(ce)
            val otherCollider = this.collider.get(ce)

            if (otherCollider == collider)
                continue

            if (checkCollision(
                    collider, newX + collider.offsetX, newY + collider.offsetY,
                    otherCollider, otherTransform.x + otherCollider.offsetX, otherTransform.y + otherCollider.offsetY
                )
            )
                return true
        }

        return false
    }

    private fun checkCollision(c1: Collider, x1: Float, y1: Float, c2: Collider, x2: Float, y2: Float): Boolean {

        val half1width = c1.width * 0.5f
        val half1height = c1.height * 0.5f

        val rect1Left = x1 - half1width
        val rect1Right = x1 + half1width
        val rect1Top = y1 - half1height
        val rect1Bottom = y1 + half1height

        val half2width = c2.width * 0.5f
        val half2height = c2.height * 0.5f

        val rect2Left = x2 - half2width
        val rect2Right = x2 + half2width
        val rect2Top = y2 - half2height
        val rect2Bottom = y2 + half2height

        return rect1Left < rect2Right && rect1Right > rect2Left &&
                rect1Top < rect2Bottom && rect1Bottom > rect2Top
    }
}