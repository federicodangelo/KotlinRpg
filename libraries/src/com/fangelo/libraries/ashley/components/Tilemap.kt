package com.fangelo.libraries.ashley.components

import com.badlogic.ashley.core.Component

class Tilemap(width: Int = 0, height: Int = 0, tiles: Array<Int> = arrayOf()) : Component {
    var width: Int = width
        private set

    var height: Int = height
        private set

    var tiles: Array<Int> = tiles
        private set

    fun getTile(x: Int, y: Int) = tiles[y * width + x]

    fun set(width: Int, height: Int, tiles: Array<Int>): Tilemap {
        this.width = width
        this.height = height
        this.tiles = tiles
        if (tiles.size != width * height)
            throw Exception("Invalid tiles array size, it's ${tiles.size} and should be ${width * height}")

        return this
    }

    init {
        if (tiles.size != width * height)
            throw Exception("Invalid tiles array size, it's ${tiles.size} and should be ${width * height}")
    }
}