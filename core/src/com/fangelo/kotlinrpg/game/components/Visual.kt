package com.fangelo.kotlinrpg.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Visual(var texture: TextureRegion, var width: Float = 1f, var height: Float = 1f) : Component {

    init {

    }

}