package com.fangelo.libraries.glprofiler

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.profiling.GLProfiler

object GLProfiler : GLProfiler(Gdx.graphics) {
    init {
        enable()
    }
}