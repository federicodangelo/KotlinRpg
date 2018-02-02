package com.fangelo.libraries.ashley.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion

data class VisualTextureItem(var texture: TextureRegion, var width: Float, var height: Float, var offsetX: Float, var offsetY: Float) {
    fun setAnchorBottom() {
        offsetY = -height * 0.5f
    }

}

class VisualTexture(texture: TextureRegion = TextureRegion(), width: Float = 1f, height: Float = 1f,
                    offsetX: Float = 0f, offsetY: Float = 0f) :
        Component {

    val items = mutableListOf<VisualTextureItem>()

    val mainItem: VisualTextureItem
        get() = items[0]

    init {
        items.add(VisualTextureItem(texture, width, height, offsetX, offsetY))
    }

    fun set(texture: TextureRegion, width: Float = 1f, height: Float = 1f, offsetX: Float = 0f, offsetY: Float = 0f):
            VisualTexture {

        items[0].texture = texture
        items[0].width = width
        items[0].height = height
        items[0].offsetX = offsetX
        items[0].offsetY = offsetY
        return this
    }

    fun add(texture: TextureRegion, width: Float = 1f, height: Float = 1f, offsetX: Float = 0f, offsetY: Float = 0f):
            VisualTexture {
        items.add(VisualTextureItem(texture, width, height, offsetX, offsetY))
        return this
    }

}