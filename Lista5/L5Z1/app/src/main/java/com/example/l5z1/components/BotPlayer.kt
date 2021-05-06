package com.example.l5z1.components

import android.content.Context
import android.util.Log

class BotPlayer(context: Context, shapeId: Int?, game: Game?, private var difficulty: Int):
    Player(context, shapeId){
    var game: Game?

    enum class TYPE
    {
        DUMB, NORMAL, SMART
    }
    private var type: TYPE? = null

    init
    {
        Log.i("Difficulty", "$difficulty")
        when(difficulty)
        {
            0 -> {type = TYPE.DUMB}

            1 -> {type = TYPE.NORMAL}

            2 -> {type = TYPE.SMART}
        }
        when(type)
        {
            TYPE.DUMB-> {
                Log.i("Bot Player", "I'm DUMB")
                movVec.x = 3f
                updateRate = 80
                location.right = (game!!.bounds.width() / 2f)
            }

            TYPE.NORMAL -> {
                Log.i("Bot Player", "I'm NORMAL")
                movVec.x = 5.5f
                updateRate = 80

            }

            TYPE.SMART -> {
                Log.i("Bot Player", "I'm SMART, good luck")
                movVec.x = 8f
                updateRate = 100

            }
        }
        this.game = game
    }

    override fun update() {
        if(shouldUpdate)
        {
            super.update()
            if(game!!.ball.movVec.y > 0 && location.centerX().toInt() == game!!.bounds.centerX())
            {
                return
            }
            location.offset(movVec.x, movVec.y)
            if(game?.ball?.location!!.centerX() > location.right ||
                    game?.ball?.location!!.centerX() < location.left)
            {
                if( (location.centerX() - game?.ball?.location!!.centerX()) * movVec.x > 0 )
                {
                    movVec.x = -movVec.x
                }
            }
            else if(location.left <= 0 || location.right >= game!!.bounds.width())
            {
                movVec.x = - movVec.x
            }
        }
    }
}

