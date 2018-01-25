package com.fangelo.libraries.ui

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable

object UIUtils {

    val DEFAULT_IMAGE_BUTTON_SIZE = 56

    fun createCustomImageButton(iconName: String): ImageButton {

        return createCustomImageButton(ScreenManager.textureAtlas.findRegion(iconName))
    }

    fun createCustomImageButton(icon: TextureRegion): ImageButton {

        val defaultButtonStyle = ScreenManager.skin.get("default", ButtonStyle::class.java)

        val imageButtonStyle = ImageButtonStyle(defaultButtonStyle.up, defaultButtonStyle.down, defaultButtonStyle.checked, null, null, null)
        imageButtonStyle.imageChecked = TextureRegionDrawable(icon)
        imageButtonStyle.imageDown = imageButtonStyle.imageChecked
        imageButtonStyle.imageUp = imageButtonStyle.imageDown

        return ImageButton(imageButtonStyle)
    }
}
