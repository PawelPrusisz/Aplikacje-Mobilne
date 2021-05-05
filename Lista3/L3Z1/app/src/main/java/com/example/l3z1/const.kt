package com.example.l3z1

import android.os.FileObserver.CREATE

const val DB_NAME = "ToDoList"
const val DB_VERSION = 1
const val TABLE_TODO = "ToDo"
const val COL_ID = "id"
const val COL_CREATED_AT = "createdAt"
const val COL_NAME = "name"
const val COL_IMG = "img"
const val COL_TIME = "time"
const val COL_PRIORITY = "priority"
const val COL_NOTIFIED = "notified"

const val TABLE_TODO_ITEM = "ToDoItem"
const val COL_TODO_ID = "toDoId"
const val COL_ITEM_NAME = "itemName"
const val COL_IS_COLPLETED = "isCompleted"
const val COL_ITEM_PRIORITY = "itemPriority"

const val INTENT_TODO_ID = "TodoId"
const val INTENT_TODO_NAME = "TodoName"