package com.fangelo.libraries.ashley.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.fangelo.libraries.ashley.components.VisualTexture
import com.fangelo.libraries.ashley.components.VisualAnimation
import ktx.ashley.allOf
import ktx.ashley.mapperFor

class UpdateVisualAnimationSystem : IteratingSystem(allOf(VisualTexture::class, VisualAnimation::class).get()) {
    private val visual = mapperFor<VisualTexture>()
    private val animation = mapperFor<VisualAnimation>()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val visual = visual.get(entity)
        val animation = animation.get(entity)

        if (animation.playing)
            animation.animationTime += deltaTime
        visual.mainItem.texture = animation.activeAnimation.getKeyFrame(animation.animationTime)
    }
}