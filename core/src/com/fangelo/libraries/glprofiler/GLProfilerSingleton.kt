package com.fangelo.libraries.glprofiler

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.profiling.GLProfiler

object GLProfilerSingleton {

    val profiler : GLProfiler

    init {
        profiler = GLProfiler(Gdx.graphics)
    }
}