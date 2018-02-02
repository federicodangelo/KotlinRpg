package com.fangelo.libraries.ashley.components

import com.badlogic.ashley.core.Component

class Rigidbody(var velocityX: Float = 0f, var velocityY: Float = 0f) : Component {
    fun set(velocityX: Float, velocityY: Float): Rigidbody {
        this.velocityX = velocityX
        this.velocityY = velocityY
        return this
    }
}