package com.fangelo.kotlinrpg.ui.dialog

import com.fangelo.kotlinrpg.ui.screen.InGameScreen
import com.fangelo.libraries.ui.Dialog
import com.fangelo.libraries.ui.DialogResult
import com.fangelo.libraries.ui.ScreenManager

class LoadingGameDialog : Dialog("Loading") {

    //private World world;
    private val hidding: Boolean = false
    private val waitFramesBeforeBlocking = 5

    init {

        text("Loading..")

        //this.world = world;
    }/*World world*/

    override fun act(delta: Float) {
        //if (!hidding && world.getWorldGenerator().isTasksQueueEmpty()) {
        //	hidding = true;
        //	result(DialogResult.Ok);
        //	internalHide();
        //} else {
        //	waitFramesBeforeBlocking--;
        //	if (waitFramesBeforeBlocking < 0)
        //		world.getWorldGenerator().waitTasksQueueEmpty();
        //}

        finishLoadingGame()
    }


    private fun finishLoadingGame() {

        ScreenManager.show(InGameScreen())

        close(DialogResult.Ok)
    }
}
