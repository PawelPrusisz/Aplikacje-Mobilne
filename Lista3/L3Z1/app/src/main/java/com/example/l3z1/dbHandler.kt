package com.example.l3z1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.l3z1.DTO.ToDo
import com.example.l3z1.DTO.ToDoItem

class dbHandler(val context : Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createToDoTable = "  CREATE TABLE $TABLE_TODO (" +
                "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP," +
                "$COL_NAME varchar," +
                "$COL_IMG varchar," +
                "$COL_PRIORITY integer," +
                "$COL_NOTIFIED integer," +
                "$COL_TIME varchar);"
        val createToDoItemTable =
            "CREATE TABLE $TABLE_TODO_ITEM (" +
                    "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                    "$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP," +
                    "$COL_TODO_ID integer," +
                    "$COL_ITEM_NAME varchar," +
                    "$COL_ITEM_PRIORITY integer," +
                    "$COL_IS_COLPLETED integer);"

        db.execSQL(createToDoTable)
        db.execSQL(createToDoItemTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addToDo(toDo: ToDo): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, toDo.name)
        cv.put(COL_TIME, toDo.time)
        cv.put(COL_IMG, toDo.img)
        cv.put(COL_PRIORITY, toDo.priority)
        cv.put(COL_NOTIFIED, toDo.notified)
        val result = db.insert(TABLE_TODO, null, cv)
        return result != (-1).toLong()
    }

    fun updateToDo(toDo: ToDo) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, toDo.name)
        cv.put(COL_TIME, toDo.time)
        cv.put(COL_IMG, toDo.img)
        cv.put(COL_PRIORITY, toDo.priority)
        cv.put(COL_NOTIFIED, toDo.notified)
        db.update(TABLE_TODO, cv, "$COL_ID=?", arrayOf(toDo.id.toString()))

    }

    fun getToDos(order : Int): MutableList<ToDo>{
        val result: MutableList<ToDo> = ArrayList()
        val db  = readableDatabase
        val queryresult = db.rawQuery("SELECT * FROM $TABLE_TODO", null)
        if(queryresult.moveToFirst()){
            do {
                val todo = ToDo()
                todo.id = queryresult.getLong(queryresult.getColumnIndex(COL_ID))
                todo.name = queryresult.getString(queryresult.getColumnIndex(COL_NAME))
                todo.img = queryresult.getString(queryresult.getColumnIndex(COL_IMG))
                todo.time = queryresult.getString(queryresult.getColumnIndex(COL_TIME))
                todo.priority = queryresult.getInt(queryresult.getColumnIndex(COL_PRIORITY))
                todo.notified = queryresult.getInt(queryresult.getColumnIndex(COL_NOTIFIED))
                result.add(todo)
            }
            while (queryresult.moveToNext())
        }
        if(order == 4)
        {
            result.sortByDescending { selector4(it) }
        }
        else if(order == 3)
        {
            result.sortBy { selector3(it) }
        }
        else if(order == 2)
        {
            result.sortBy {selector2(it) }
        }
        else if(order == 1)
        {
            result.sortBy { selector1(it) }
        }
        else
        {
            result.sortBy { selector(it) }
        }
        queryresult.close()
        return result
    }
    fun selector4(t: ToDo): Int = t.priority
    fun selector3(t: ToDo): String = t.name
    fun selector2(t: ToDo): String = t.img
    fun selector1(t: ToDo): String = t.time
    fun selector(t: ToDo): Long = t.id

    fun addToDoItem(item: ToDoItem): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_ITEM_NAME, item.itemName)
        cv.put(COL_TODO_ID, item.toDoId)
        if (item.isCompleted)
            cv.put(COL_IS_COLPLETED, true)
        else
            cv.put(COL_IS_COLPLETED, false)

        val result = db.insert(TABLE_TODO_ITEM, null, cv)
        return result != (-1).toLong()
    }

    fun updateToDoItem(item: ToDoItem){
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_ITEM_NAME, item.itemName)
        cv.put(COL_TODO_ID, item.toDoId)

        if (item.isCompleted)
        {
            cv.put(COL_IS_COLPLETED, true)
        }
        else
        {
            cv.put(COL_IS_COLPLETED, false)
        }

        val res = db.update(TABLE_TODO_ITEM, cv, "$COL_ID=?", arrayOf(item.id.toString()))
    }

    fun getToDoItems(todoId: Long): MutableList<ToDoItem> {
        val result: MutableList<ToDoItem> = ArrayList()

        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * FROM $TABLE_TODO_ITEM WHERE $COL_TODO_ID=$todoId", null)

        if (queryResult.moveToFirst()) {
            do {
                val item = ToDoItem()
                item.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                item.toDoId = queryResult.getLong(queryResult.getColumnIndex(COL_TODO_ID))
                item.itemName = queryResult.getString(queryResult.getColumnIndex(COL_ITEM_NAME))
                item.isCompleted = queryResult.getInt(queryResult.getColumnIndex(COL_IS_COLPLETED)) == 1
                item.toDoId = todoId
                result.add(item)
            } while (queryResult.moveToNext())
        }

        queryResult.close()
        return result
    }

    fun deleteToDo(todoId: Long){
        val db = writableDatabase
        db.delete(TABLE_TODO_ITEM,"$COL_TODO_ID=?", arrayOf(todoId.toString()))
        db.delete(TABLE_TODO,"$COL_ID=?", arrayOf(todoId.toString()))
    }

    fun updateToDoItemCompletedStatus(todoId: Long, isCompleated: Boolean)
    {
        val db = writableDatabase
        val queryResult = db.rawQuery("SELECT * FROM $TABLE_TODO_ITEM WHERE $COL_TODO_ID=$todoId", null)

        if (queryResult.moveToFirst()) {
            do {
                val item = ToDoItem()
                item.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                item.toDoId = queryResult.getLong(queryResult.getColumnIndex(COL_TODO_ID))
                item.itemName = queryResult.getString(queryResult.getColumnIndex(COL_ITEM_NAME))
                item.isCompleted = isCompleated
                item.toDoId = todoId
                updateToDoItem(item)
            } while (queryResult.moveToNext())
        }
        queryResult.close()
    }

    fun deleteToDoItem(itemId : Long){
        val db = writableDatabase
        db.delete(TABLE_TODO_ITEM,"$COL_ID=?" , arrayOf(itemId.toString()))
    }
}