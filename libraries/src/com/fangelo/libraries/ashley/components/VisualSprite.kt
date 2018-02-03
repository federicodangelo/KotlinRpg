package com.fangelo.libraries.ashley.components

import com.badlogic.ashley.core.Component
import com.fangelo.libraries.ashley.data.Sprite

class VisualSprite(sprite: Sprite? = null) : Component {

    val sprites = mutableListOf<Sprite>()

    val mainSprite: Sprite?
        get() = if (!sprites.isEmpty()) sprites[0] else null

    init {
        if (sprite != null)
            sprites.add(sprite)
    }

    fun add(sprite: Sprite): VisualSprite {
        sprites.add(sprite)
        return this
    }

}