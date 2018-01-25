package com.fangelo.kotlinrpg

open class PlatformAdapter {

    var version = ""
        protected set

    init {
        instance = this
    }

    companion object {

        var instance: PlatformAdapter? = null
            private set

        fun dispose() {
            instance = null
        }
    }
}
