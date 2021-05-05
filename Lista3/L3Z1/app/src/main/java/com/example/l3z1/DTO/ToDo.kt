package com.example.l3z1.DTO

class ToDo {

    var id: Long = -1
    var name = ""
    var img = ""
    var time = ""
    var priority: Int = 0
    var createdAt = ""
    var notified: Int = 0
    var compleated: Boolean = false
    var items: MutableList<ToDoItem> = ArrayList()

}