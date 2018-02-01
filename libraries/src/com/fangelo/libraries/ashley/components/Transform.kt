package com.fangelo.libraries.ashley.components

import com.badlogic.ashley.core.Component

class Transform(var x: Float = 0f, var y: Float = 0f) : Component {
    fun set(x: Float, y: Float): Transform {
        this.x = x
        this.y = y
        return this
    }
}