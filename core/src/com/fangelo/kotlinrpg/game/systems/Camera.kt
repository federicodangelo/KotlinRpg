package com.fangelo.kotlinrpg.game.systems

interface Camera {
    var cameraPositionX: Float
    var cameraPositionY: Float
    var zoom: Float
    fun moveTo(x: Float, y: Float)
}