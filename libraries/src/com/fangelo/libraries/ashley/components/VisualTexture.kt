package com.fangelo.libraries.ashley.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion

class VisualTexture(var texture: TextureRegion = TextureRegion(), var width: Float = 1f, var height: Float = 1f) : Component {

    fun set(texture: TextureRegion, width: Float, height: Float): VisualTexture {
        this.texture = texture
        this.width = width
        this.height = height
        return this
    }
}