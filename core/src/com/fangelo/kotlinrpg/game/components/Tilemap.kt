package com.fangelo.kotlinrpg.game.components

import com.badlogic.ashley.core.Component

class Tilemap(val sizeX: Int, val sizeY: Int, val tiles: Array<Int>) : Component {
    init {
        if (tiles.size != sizeX * sizeY)
            throw Exception("Invalid tiles array size, it's ${tiles.size} and should be ${sizeX * sizeY}")

    }

    inline fun getTile(x: Int, y: Int) = tiles[y * sizeX + x]
}