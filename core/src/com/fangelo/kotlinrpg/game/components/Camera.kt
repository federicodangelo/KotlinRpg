package com.fangelo.kotlinrpg.game.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Matrix4

class Camera : Component {

    private var cameraChanged: Boolean = true

    private var camera = OrthographicCamera()

    var x: Float = 0f
        set(value) {
            field = value
            camera.position.x = value
            cameraChanged = true
        }

    var y: Float = 0f
        set(value) {
            field = value
            camera.position.y = value
            cameraChanged = true
        }

    var zoom: Float
        get() = camera.zoom
        set(value) {
            camera.zoom = value
            cameraChanged = true
        }

    init {
        camera = OrthographicCamera()
        camera.setToOrtho(true)
        camera.zoom = 0.25f
        camera.position.set(0f, 0f, 0f)
    }

    fun moveTo(x: Float, y: Float) {
        this.x = x
        this.y = y
    }


    fun resize(width: Int, height: Int) {
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        camera.update()
    }

    val combined: Matrix4
        get() {
            if (cameraChanged) {
                cameraChanged = false
                camera.update()
            }
            return camera.combined
        }
}