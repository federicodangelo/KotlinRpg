package com.fangelo.libraries.ashley.components

import com.badlogic.ashley.core.Component

class Collider(var width: Float = 1f, var height: Float = 1f, var offsetX: Float = 0f, var offsetY: Float = 0f) : Component {

    fun set(width: Float = 1f, height: Float = 1f, offsetX: Float = 0f, offsetY: Float = 0f) {
        this.width = width
        this.height = height
        this.offsetX = offsetX
        this.offsetY = offsetY
    }

    fun setAnchorBottom(): Collider {
        offsetY = -height * 0.5f
        return this
    }
}