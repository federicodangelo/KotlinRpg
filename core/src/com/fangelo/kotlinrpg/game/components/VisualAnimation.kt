package com.fangelo.kotlinrpg.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class VisualAnimation(var animations: Map<String, Animation<TextureRegion>> = mapOf(), startingAnimation: String = "") : Component {

    var activeAnimation: Animation<TextureRegion>
        private set

    var animationTime: Float
    var playing: Boolean = true

    val isFinished
        get() = activeAnimation.isAnimationFinished(animationTime)

    init {
        activeAnimation = if (startingAnimation != "") animations[startingAnimation]!! else emptyAnimation()
        animationTime = 0f
    }

    private fun emptyAnimation() = Animation(0f, TextureRegion())

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

    fun set(animations: Map<String, Animation<TextureRegion>>, startingAnimation: String): VisualAnimation {
        this.animations = animations
        this.activeAnimation = if (startingAnimation != "") animations[startingAnimation]!! else emptyAnimation()
        return this
    }
}