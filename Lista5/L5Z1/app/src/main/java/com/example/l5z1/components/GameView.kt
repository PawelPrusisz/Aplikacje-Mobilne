package com.example.l5z1.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.l5z1.DBHandler
import com.example.l5z1.interfaces.GameLoop

class GameView(context: Context, private val difficulty: Int, private val saveSlot: Int): SurfaceView(context),
    SurfaceHolder.Callback,
    Runnable,
    GameLoop{
    override var updateRate: Int = 120
    override var timeToUpdate: Long = System.currentTimeMillis()

    private var mThread: Thread? = null
    lateinit var mCanvas: Canvas

    var mHolder: SurfaceHolder? = holder
    var db = DBHandler(context)
    private var bounds: Rect = Rect()
        set(value)
        {
            field = value
            setup()
        }

    var game: Game? = null

    init {
        if(mHolder != null)
        {
            mHolder?.addCallback(this)
        }
    }

    fun setup()
    {

        if(saveSlot == 0)
        {
            game = Game(this.context, bounds, null, difficulty, saveSlot)
        }
        else
        {
            val bundle: Bundle? = db.getSave(saveSlot)

            game = Game(this.context, bounds, bundle, difficulty, saveSlot)
        }


        start()
    }

    fun start()
    {
        game?.state = Game.STATE.STARTED
        mThread = Thread(this)
        timeToUpdate = System.currentTimeMillis()
        game?.ball!!.timeToUpdate = System.currentTimeMillis()
        mThread?.start()
    }


    fun stop()
    {
        game?.state = Game.STATE.END
        try {
            mThread?.join()
        }
        catch (e: Exception){}
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.i(this.toString(), "surface created")
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.i(this.toString(), "surface changed")
        bounds = Rect(50, height / 12, width - 50, height - height / 12)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.i(this.toString(), "surface destroyed")
        stop()
    }

    override fun run() {
        while(game?.state == Game.STATE.STARTED)
        {
            while(shouldUpdate)
            {
                update()
            }
            render()
        }
    }

    override fun update() {
        timeToUpdate = System.currentTimeMillis() + 1000L / updateRate
        game?.update()
    }

    override fun render(canvas: Canvas?) {
        if(mHolder!!.surface?.isValid == true)
        {
            mCanvas = mHolder!!.lockCanvas()
            mCanvas.drawColor((Color.WHITE))
            game?.render(mCanvas)
            mHolder!!.unlockCanvasAndPost(mCanvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        game?.processInput(event)
        return true
    }
}
