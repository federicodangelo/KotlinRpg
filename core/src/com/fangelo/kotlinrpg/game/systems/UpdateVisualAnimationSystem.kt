package com.fangelo.kotlinrpg.game.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.fangelo.kotlinrpg.game.components.Movement
import com.fangelo.kotlinrpg.game.components.Transform
import com.fangelo.kotlinrpg.game.components.Visual
import com.fangelo.kotlinrpg.game.components.VisualAnimation
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class UpdateVisualAnimationSystem : IteratingSystem(allOf(Visual::class, VisualAnimation::class).get()) {
    private val visual = mapperFor<Visual>()
    private val animation = mapperFor<VisualAnimation>()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val visual = visual.get(entity)
        val animation = animation.get(entity)

        if (animation.playing)
            animation.animationTime += deltaTime
        visual.texture = animation.activeAnimation.getKeyFrame(animation.animationTime)
    }
}