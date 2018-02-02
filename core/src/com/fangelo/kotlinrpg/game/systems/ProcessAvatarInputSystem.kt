package com.fangelo.kotlinrpg.game.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.fangelo.kotlinrpg.game.components.avatar.Avatar
import com.fangelo.kotlinrpg.game.components.avatar.MainAvatar
import com.fangelo.libraries.ashley.components.Rigidbody
import ktx.ashley.allOf
import ktx.ashley.mapperFor
import ktx.math.vec2

class ProcessAvatarInputSystem : IteratingSystem(allOf(Rigidbody::class, Avatar::class, MainAvatar::class).get()) {
    private val movement = mapperFor<Rigidbody>()
    private val avatar = mapperFor<Avatar>()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val movement = movement.get(entity)
        val avatar = avatar.get(entity)

        val velocity = vec2()

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            velocity.x += avatar.walkSpeed

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            velocity.x += -avatar.walkSpeed

        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            velocity.y += -avatar.walkSpeed

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            velocity.y += avatar.walkSpeed

        if (!velocity.isZero)
            velocity.nor().scl(avatar.walkSpeed)

        movement.velocityX = velocity.x
        movement.velocityY = velocity.y
    }
}