package com.fangelo.kotlinrpg.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.fangelo.kotlinrpg.MyGdxGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()

        //config.width = 1366 //landscape!
        //config.height = 768

        config.width = 768 //portrait!
        config.height = 1366

        DesktopPlatformAdapter()
        LwjglApplication(MyGdxGame(), config)
    }
}
