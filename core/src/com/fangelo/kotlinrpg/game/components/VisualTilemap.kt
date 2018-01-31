package com.fangelo.kotlinrpg.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion

class VisualTilemap(val tilesTextures: Array<TextureRegion>) : Component {
    inline fun getTileTexture(tile: Int): TextureRegion = tilesTextures[tile]
}