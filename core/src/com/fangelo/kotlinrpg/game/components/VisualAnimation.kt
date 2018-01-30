package com.fangelo.kotlinrpg.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class VisualAnimation(val animations: Map<String, Animation<TextureRegion>>, startingAnimation: String) : Component {

    var activeAnimation: Animation<TextureRegion>
    var animationTime: Float
    var playing: Boolean = true
    val isFinished
        get() = activeAnimation.isAnimationFinished(animationTime)

    init {
        activeAnimation = animations[startingAnimation]!!
        animationTime = 0f
    }

    fun playAnimation(name: String) {

        if (!animations.containsKey(name)) {
            ktx.log.error { "Unknown animation $name" }
            return
        }

        playing = true

        val newAnimation = animations[name]!!
        if (newAnimation == activeAnimation)
            return

        activeAnimation = newAnimation
        animationTime = 0f
    }

    fun stop() {
        animationTime = 0f
        playing = false
    }
}