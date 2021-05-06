package com.example.l5z1.components

import android.content.Context

open class Player(context: Context, shapeId: Int?): Sprite(context, shapeId) {
    var health = 100
    var score = 0

    override var updateRate: Int = 1
    override var timeToUpdate: Long = System.currentTimeMillis()

    override fun update() {
        if(shouldUpdate)
        {
            timeToUpdate = System.currentTimeMillis() + 1000L / updateRate
        }
    }


}