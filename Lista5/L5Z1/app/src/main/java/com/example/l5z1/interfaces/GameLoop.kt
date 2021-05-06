package com.example.l5z1.interfaces

import android.graphics.Canvas

interface GameLoop {
    var updateRate: Int
    var timeToUpdate: Long
    val shouldUpdate:Boolean
        get() = (System.currentTimeMillis() >= timeToUpdate)

    fun render(canvas: Canvas? = null)
    fun update()
}