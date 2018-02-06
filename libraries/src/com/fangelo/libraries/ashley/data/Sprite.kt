package com.fangelo.libraries.ashley.data

import com.badlogic.gdx.graphics.g2d.TextureRegion

data class Sprite(var texture: TextureRegion, var width: Float = 1f, var height: Float = 1f, var offsetX: Float = 0f, var offsetY: Float = 0f) {
    fun setAnchorBottom(): Sprite {
        offsetY = -height * 0.5f
        return this
    }
}
