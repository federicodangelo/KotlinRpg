package com.fangelo.kotlinrpg.game.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.fangelo.kotlinrpg.game.components.MainAvatar
import com.fangelo.kotlinrpg.game.components.Movement
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class MainAvatarInputSystem : IteratingSystem(allOf(Movement::class, MainAvatar::class).get()) {
    private val movement = mapperFor<Movement>()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val movement = movement.get(entity)

        movement.velocityX = 0f
        movement.velocityY = 0f

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            movement.velocityX += 1.0f

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            movement.velocityX += -1.0f

        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            movement.velocityY += -1.0f

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            movement.velocityY += 1.0f
    }
}