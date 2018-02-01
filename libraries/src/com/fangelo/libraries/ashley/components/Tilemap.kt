package com.fangelo.libraries.ashley.components

import com.badlogic.ashley.core.Component

class Tilemap(sizeX: Int = 0, sizeY: Int = 0, tiles: Array<Int> = arrayOf()) : Component {
    var sizeX: Int = sizeX
        private set

    var sizeY: Int = sizeY
        private set

    var tiles: Array<Int> = tiles
        private set

    fun getTile(x: Int, y: Int) = tiles[y * sizeX + x]

    fun set(sizeX: Int, sizeY: Int, tiles: Array<Int>): Tilemap {
        this.sizeX = sizeX
        this.sizeY = sizeY
        this.tiles = tiles
        if (tiles.size != sizeX * sizeY)
            throw Exception("Invalid tiles array size, it's ${tiles.size} and should be ${sizeX * sizeY}")

        return this
    }

    init {
        if (tiles.size != sizeX * sizeY)
            throw Exception("Invalid tiles array size, it's ${tiles.size} and should be ${sizeX * sizeY}")
    }
}