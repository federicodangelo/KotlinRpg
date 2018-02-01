package com.fangelo.libraries.ashley.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion

class VisualTileset(tilesTextures: Array<TextureRegion> = arrayOf()) : Component {
    var tilesTextures: Array<TextureRegion> = tilesTextures
        private set

    fun getTileTexture(tile: Int): TextureRegion = tilesTextures[tile]

    fun set(tilesTextures: Array<TextureRegion>): VisualTileset {
        this.tilesTextures = tilesTextures
        return this
    }
}