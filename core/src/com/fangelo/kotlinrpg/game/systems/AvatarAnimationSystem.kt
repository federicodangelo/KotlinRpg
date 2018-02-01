package com.fangelo.kotlinrpg.game.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.fangelo.kotlinrpg.game.components.avatar.MainAvatar
import com.fangelo.libraries.ashley.components.Movement
import com.fangelo.libraries.ashley.components.VisualAnimation
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class AvatarAnimationSystem : IteratingSystem(allOf(Movement::class, MainAvatar::class, VisualAnimation::class).get()) {
    private val movement = mapperFor<Movement>()
    private val visualAnimation = mapperFor<VisualAnimation>()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val movement = movement.get(entity)
        val visualAnimation = visualAnimation.get(entity)

        when {
            movement.velocityX > 0 -> visualAnimation.playAnimation("walk-east")
            movement.velocityX < 0 -> visualAnimation.playAnimation("walk-west")
            movement.velocityY < 0 -> visualAnimation.playAnimation("walk-north")
            movement.velocityY > 0 -> visualAnimation.playAnimation("walk-south")
            else -> visualAnimation.stop()
        }
    }
}