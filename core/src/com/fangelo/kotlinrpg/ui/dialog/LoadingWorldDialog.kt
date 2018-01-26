package com.fangelo.kotlinrpg.ui.dialog

import com.fangelo.libraries.ui.Dialog
import com.fangelo.libraries.ui.DialogResult

class LoadingWorldDialog : Dialog("Loading") {

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
        close(DialogResult.Ok)
    }
}
