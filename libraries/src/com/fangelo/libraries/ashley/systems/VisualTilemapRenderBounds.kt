package com.fangelo.libraries.ashley.systems

data class VisualTilemapRenderBounds(
    var fromX: Int = 0,
    var toX: Int = 0,
    var fromY: Int = 0,
    var toY: Int = 0,
    var renderOffsetX: Int = 0,
    var renderOffsetY: Int = 0
)