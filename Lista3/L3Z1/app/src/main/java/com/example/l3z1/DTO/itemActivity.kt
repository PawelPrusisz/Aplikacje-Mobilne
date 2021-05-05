package com.example.l3z1.DTO

import android.content.Context
import android.content.DialogInterface
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.l3z1.INTENT_TODO_ID
import com.example.l3z1.INTENT_TODO_NAME
import com.example.l3z1.R
import com.example.l3z1.dbHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton

class itemActivity : AppCompatActivity() {

    lateinit var dbHandler: dbHandler
    var todoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        setSupportActionBar(findViewById(R.id.item_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = intent.getStringExtra(INTENT_TODO_NAME)
        dbHandler = dbHandler(this)
        todoId = intent.getLongExtra(INTENT_TODO_ID, -1)

        val rv_item = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_item)
        rv_item.layoutManager = LinearLayoutManager(this)
        val floatButton = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab_item)
        //+ button
        floatButton.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.new_subtask, null)
            val toDoName = view.findViewById<EditText>(R.id.et_todoitem)
            dialog.setView(view)
            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                if (toDoName.text.isNotEmpty()) {
                    val item = ToDoItem()
                    item.itemName = toDoName.text.toString()
                    item.toDoId = todoId
                    item.isCompleted = false
                    dbHandler.addToDoItem(item)
                    refreshList()
                }
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

            }
            dialog.show()
        }
    }

    fun updateItem(item : ToDoItem){
        val dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.new_subtask, null)
        val toDoName = view.findViewById<EditText>(R.id.et_todoitem)
        toDoName.setText(item.itemName)
        dialog.setView(view)
        dialog.setPositiveButton("Uppdate") { _: DialogInterface, _: Int ->
            if (toDoName.text.isNotEmpty()) {
                item.itemName = toDoName.text.toString()
                item.toDoId = todoId
                item.isCompleted = false
                dbHandler.updateToDoItem(item)
                refreshList()
            }
        }
        dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

        }
        dialog.show()
    }

    override fun onResume() {
        refreshList()
        super.onResume()
    }

    //val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
    //        recyclerView.adapter = DashboardAdapter(this,dbHandler.getToDos(sortingTarget))
    private fun refreshList() {
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_item)
        recyclerView.adapter = ItemAdapter(this, dbHandler.getToDoItems(todoId))
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item?.itemId == android.R.id.home)
        {
            finish()
            true
        }
        else
        {
            super.onOptionsItemSelected(item)
        }
    }

    class ItemAdapter(val activity: itemActivity, val list: MutableList<ToDoItem>) :
        RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_child_item, p0, false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
            holder.itemName.text = list[p1].itemName
            holder.itemName.isChecked = list[p1].isCompleted
            holder.itemName.setOnClickListener {
                list[p1].isCompleted = !list[p1].isCompleted
                activity.dbHandler.updateToDoItem(list[p1])
            }
            holder.delete.setOnClickListener{
                val dialog = AlertDialog.Builder(activity)
                dialog.setTitle("Czy na pewno?")
                dialog.setMessage("Czy na pewno chcesz usunąć to zadanie?")
                dialog.setPositiveButton("Tak") { _: DialogInterface, _: Int ->
                    activity.dbHandler.deleteToDoItem(list[p1].id)
                    activity.refreshList()
                }
                dialog.setNegativeButton("Nie") { _: DialogInterface, _: Int ->

                }
                dialog.show()
            }
            holder.edit.setOnClickListener{
                activity.updateItem(list[p1])
            }
        }

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val itemName: CheckBox = v.findViewById(R.id.cb_item)
            val edit : ImageView = v.findViewById(R.id.iv_edit)
            val delete : ImageView = v.findViewById(R.id.iv_delete)
        }
    }

}