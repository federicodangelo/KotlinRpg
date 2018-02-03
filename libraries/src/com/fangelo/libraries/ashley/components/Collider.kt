package com.fangelo.libraries.ashley.components

import com.badlogic.ashley.core.Component

enum class ColliderShape {
    RECTANGLE,
    CIRCLE
}

class Collider(var size: Float = 1f, var shape: ColliderShape = ColliderShape.RECTANGLE) : Component {
    fun set(size: Float = 1f, shape: ColliderShape = ColliderShape.RECTANGLE) {
        this.size = size
        this.shape = shape
    }
}