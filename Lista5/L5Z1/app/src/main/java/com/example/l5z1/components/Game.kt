package com.example.l5z1.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.example.l5z1.*
import com.example.l5z1.interfaces.GameLoop
import com.example.l5z1.interfaces.GameStateListener
import java.lang.Exception

class Game(context: Context, bounds: Rect, var bundle: Bundle? = null, private val difficulty: Int, private val saveSlot: Int): GameLoop {
    var onGameStateChangeListener: GameStateListener? = null
    private var db = DBHandler(context)
    private var frame: Int = 0
    enum class STATE {
        END, PAUSED, STARTED
    }

    var state = STATE.PAUSED
        set(value)
        {
            try {
                if(field != value)
                {
                    onGameStateChangeListener?.stateChanged(value)
                }
            }
            catch (e: Exception){}
            field = value
            Log.i("Game State", "Game state changed to $value")
        }


    var bounds: Rect = bounds
    var ball: Ball = Ball(context, R.mipmap.ball_round)
    //players[0] - human
    //players[1] - bot
    private var players: Array<Player> = arrayOf(Player(context, R.drawable.player1),
                                                BotPlayer(context, R.drawable.player2, this, difficulty))

    init {
        if(bundle != null)
        {
            val player1left: Float = bundle!!.get(COL_PLAYER1_LEFT) as Float
            val player2left: Float = bundle!!.get(COL_PLAYER2_LEFT) as Float
            val ballX: Float = bundle!!.get(COL_BALL_X) as Float
            val ballY: Float = bundle!!.get(COL_BALL_Y) as Float
            val ballSX: Float = bundle!!.get(COL_BALL_SX) as Float
            val ballSY: Float = bundle!!.get(COL_BALL_SY) as Float
            players[0].location.offsetTo(player1left, bounds.bottom - players[0].location.height())
            players[1].location.offsetTo(player2left, bounds.top.toFloat() - 150)
            ball.location.offsetTo(ballX, ballY)
            ball.movVec.x = ballSX
            ball.movVec.y = ballSY
            (players[1] as BotPlayer).game = this
        }
        else
        {
            setDefaultState()
        }


        onGameStateChangeListener = context as GameStateListener
    }

    override var updateRate: Int = 60
    override var timeToUpdate: Long = 0L

    override fun render(canvas: Canvas?) {
        ball.render(canvas)
        for( p in players)p.render(canvas)
    }

    private fun setDefaultState()
    {
        players[0].location.offsetTo(bounds.exactCenterX() - players[0].location.width()/2,
            bounds.bottom - players[0].location.height())

        players[1].location.offsetTo(bounds.exactCenterX() - players[1].location.width()/2,
            bounds.top.toFloat() - 150)
        ball.location.offsetTo(players[1].location.centerX() - ball.location.centerX(),
            players[1].location.bottom)
        ball.movVec.set(3f, 4f)
    }
    override fun update() {
        ball.update()
        for(p in players)p.update()
        if(collide(bounds))
        {
            setDefaultState()
            commitState(true)
            state = STATE.END
        }
        for(p in players)collide(p)
        commitState(false)
    }

    private fun commitState(dump: Boolean)
    {
        if(frame == 0 || dump)
        {
            val bundle = Bundle()
            bundle.putFloat(COL_PLAYER1_LEFT, players[0].location.left)
            bundle.putFloat(COL_PLAYER2_LEFT, players[1].location.left)
            bundle.putFloat(COL_BALL_X, ball.location.left)
            bundle.putFloat(COL_BALL_Y, ball.location.top)
            bundle.putFloat(COL_BALL_SX, ball.movVec.x)
            bundle.putFloat(COL_BALL_SY, ball.movVec.y)
            Log.i("saving", "saving current gamestate")
            db.addSave(saveSlot,  bundle)
        }
        frame += 1
        frame %= 60

    }
    fun processInput(o: Any?)
    {
        if(o is MotionEvent)
        {
            players[0].location.offsetTo(o.x, players[0].location.top)
        }
    }

    private fun collide(o: Any?):Boolean
    {
        if(o is Rect)
        {
            if(ball.location.left <= 0 || ball.location.right >= o.right)
            {
                ball.movVec.x = -ball.movVec.x
                if(ball.location.left <= 0)
                {
                    ball.location.offset( -ball.location.left, 0f)
                }
                else
                {
                    ball.location.offset(o.right - ball.location.right, 0f)
                }
            }

            if(ball.location.top <= 0 || ball.location.bottom >= o.bottom)
            {
                if(ball.location.top <= 0)
                {
                    players[0].score++
                }
                else
                {
                    players[1].score++
                }
                return true
            }

        }
        else if(o is Player)
        {
            if(o.location.contains(ball.location.centerX(), ball.location.bottom))
            {
                ball.movVec.y = -ball.movVec.y
                ball.location.offset(0f, o.location.top - ball.location.bottom)
            }
            else if(o.location.contains(ball.location.centerX(), ball.location.top))
            {
                ball.movVec.y = -ball.movVec.y
                ball.location.offset(0f, o.location.bottom - ball.location.top)
            }
        }
        return false
    }

}