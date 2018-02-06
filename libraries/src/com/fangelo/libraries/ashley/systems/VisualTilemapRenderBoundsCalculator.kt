package com.fangelo.libraries.ashley.systems

import com.fangelo.libraries.ashley.components.Camera
import com.fangelo.libraries.ashley.components.Tilemap
import com.fangelo.libraries.ashley.components.Transform

class VisualTilemapRenderBoundsCalculator {

    private val EXTRA_TILES_TO_DRAW = 2

    fun calculate(
        camera: Camera, cameraTransform: Transform, tilemap: Tilemap, tilemapTransform: Transform, toReturn: VisualTilemapRenderBounds? = null
    ): VisualTilemapRenderBounds {

        val viewPortWidth = camera.viewportWidth * camera.zoom + EXTRA_TILES_TO_DRAW * 2
        val viewPortHeight = camera.viewportHeight * camera.zoom + EXTRA_TILES_TO_DRAW * 2
        val cameraPositionX = cameraTransform.x - EXTRA_TILES_TO_DRAW
        val cameraPositionY = cameraTransform.y + EXTRA_TILES_TO_DRAW

        val offsetX = tilemapTransform.x.toInt()
        val offsetY = tilemapTransform.y.toInt()

        val offsetXscreen = offsetX.toFloat()
        val offsetYscreen = offsetY.toFloat()

        val width = tilemap.width
        val height = tilemap.height

        val viewBoundsX = cameraPositionX - offsetXscreen - viewPortWidth / 2
        val viewBoundsY = cameraPositionY - offsetYscreen - viewPortHeight / 2
        val viewBoundsWidth = viewPortWidth
        val viewBoundsHeight = viewPortHeight

        val fromX = Math.max(0, viewBoundsX.toInt())
        val toX = Math.min(width, (viewBoundsX + viewBoundsWidth + 1f).toInt())

        val fromY = Math.max(0, viewBoundsY.toInt())
        val toY = Math.min(height, (viewBoundsY + viewBoundsHeight + 1f).toInt())

        val renderOffsetX = fromX + offsetX
        val renderOffsetY = fromY + offsetY

        if (toReturn != null) {
            toReturn.fromX = fromX
            toReturn.toX = toX
            toReturn.fromY = fromY
            toReturn.toY = toY
            toReturn.renderOffsetX = renderOffsetX
            toReturn.renderOffsetY = renderOffsetY
            return toReturn
        }

        return VisualTilemapRenderBounds(fromX, toX, fromY, toY, renderOffsetX, renderOffsetY)
    }
}