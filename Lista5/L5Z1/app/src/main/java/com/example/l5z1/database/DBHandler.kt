package com.example.l5z1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log

class DBHandler(val context : Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createToDoTable = "  CREATE TABLE $TABLE_GAME (" +
                "$COL_ID integer PRIMARY KEY," +
                "$COL_PLAYER1_LEFT float," +
                "$COL_PLAYER2_LEFT float," +
                "$COL_BALL_X float," +
                "$COL_BALL_Y float," +
                "$COL_BALL_SX float," +
                "$COL_BALL_SY float);"

        db.execSQL(createToDoTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun getSave(saveSlot: Int): Bundle? {
        var bundle: Bundle = Bundle()
        val db  = readableDatabase
        val queryResult = db.rawQuery("SELECT * FROM $TABLE_GAME WHERE $COL_ID=$saveSlot" , null)

        if(queryResult.moveToFirst())
        {
            bundle.putFloat(COL_PLAYER1_LEFT, queryResult.getFloat(queryResult.getColumnIndex(COL_PLAYER1_LEFT)))
            bundle.putFloat(COL_PLAYER2_LEFT, queryResult.getFloat(queryResult.getColumnIndex(COL_PLAYER2_LEFT)))
            bundle.putFloat(COL_BALL_X, queryResult.getFloat(queryResult.getColumnIndex(COL_BALL_X)))
            bundle.putFloat(COL_BALL_Y, queryResult.getFloat(queryResult.getColumnIndex(COL_BALL_Y)))
            bundle.putFloat(COL_BALL_SX, queryResult.getFloat(queryResult.getColumnIndex(COL_BALL_SX)))
            bundle.putFloat(COL_BALL_SY, queryResult.getFloat(queryResult.getColumnIndex(COL_BALL_SY)))
            return bundle
        }
        else
        {
            return null
        }
    }

    fun addSave(saveSlot: Int, bundle: Bundle): Boolean
    {

        var db = readableDatabase
        val queryResult = db.rawQuery("SELECT * FROM $TABLE_GAME WHERE $COL_ID=$saveSlot" , null)

        if(queryResult.moveToFirst())
        {
            updateSave(saveSlot, bundle)
            return true
        }
        else
        {
            db = writableDatabase
            val cv = ContentValues()
            cv.put(COL_ID, saveSlot)
            cv.put(COL_PLAYER1_LEFT, bundle.getFloat(COL_PLAYER1_LEFT))
            cv.put(COL_PLAYER2_LEFT, bundle.getFloat(COL_PLAYER2_LEFT))
            cv.put(COL_BALL_X, bundle.getFloat(COL_BALL_X))
            cv.put(COL_BALL_Y, bundle.getFloat(COL_BALL_Y))
            cv.put(COL_BALL_SX, bundle.getFloat(COL_BALL_SX))
            cv.put(COL_BALL_SY, bundle.getFloat(COL_BALL_SY))
            val result = db.insert(TABLE_GAME, null, cv)

            return result != (-1).toLong()
        }
    }

    fun updateSave(saveSlot: Int, bundle: Bundle) {
        val db = writableDatabase
        val cv = ContentValues()

        cv.put(COL_PLAYER1_LEFT, bundle.getFloat(COL_PLAYER1_LEFT))
        cv.put(COL_PLAYER2_LEFT, bundle.getFloat(COL_PLAYER2_LEFT))
        cv.put(COL_BALL_X, bundle.getFloat(COL_BALL_X))
        cv.put(COL_BALL_Y, bundle.getFloat(COL_BALL_Y))
        cv.put(COL_BALL_SX, bundle.getFloat(COL_BALL_SX))
        cv.put(COL_BALL_SY, bundle.getFloat(COL_BALL_SY))

        db.update(TABLE_GAME, cv, "$COL_ID=?", arrayOf(saveSlot.toString()))

    }
}