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
        super.act(delta)
        //if (!hidding && world.getWorldGenerator().isTasksQueueEmpty()) {
        //	hidding = true;
        //	result(DialogResult.Ok);
        //	hide();
        //} else {
        //	waitFramesBeforeBlocking--;
        //	if (waitFramesBeforeBlocking < 0)
        //		world.getWorldGenerator().waitTasksQueueEmpty();
        //}
        result(DialogResult.Ok)
    }
}
