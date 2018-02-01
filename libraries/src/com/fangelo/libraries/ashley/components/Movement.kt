package com.fangelo.libraries.ashley.components

import com.badlogic.ashley.core.Component

class Movement(var velocityX: Float = 0f, var velocityY: Float = 0f) : Component {
    fun set(velocityX: Float, velocityY: Float): Movement {
        this.velocityX = velocityX
        this.velocityY = velocityY
        return this
    }

}