package com.fangelo.kotlinrpg

import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()

        config.useAccelerometer = false
        config.useCompass = false
        config.useImmersiveMode = true

        AndroidPlatformAdapter(this)
        initialize(MyGdxGame(), config)
    }
}
