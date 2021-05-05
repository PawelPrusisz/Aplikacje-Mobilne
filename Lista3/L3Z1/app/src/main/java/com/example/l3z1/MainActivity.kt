package com.example.l3z1

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.l3z1.DTO.ToDo
import com.example.l3z1.DTO.ToDoItem
import com.example.l3z1.DTO.itemActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var dbHandler: dbHandler
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChanel: NotificationChannel
    lateinit var builder: Notification.Builder
    private var chanelId = "com.example.l3z1"

    var pic: String = ""
    var date = "01/01/2000";
    var priority = 0;
    //0 - by id -- default
    //1 - by time
    //2 - by group
    //3 - by title
    //4 - by priority
    var sortingTarget = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = "TODO"
        dbHandler = dbHandler(this)
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val floatButton = findViewById<FloatingActionButton>(R.id.fab_dashboard)
        //+ button
        floatButton.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.new_todo, null)
            //priority
            view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.appCompatButton1).setOnClickListener()
            {
                this.priority = 0
            }
            view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.appCompatButton2).setOnClickListener()
            {
                this.priority = 1
            }
            view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.appCompatButton3).setOnClickListener()
            {
                this.priority = 2
            }
            //priority
            //calendar
            view.findViewById<DatePicker>(R.id.calendar).setOnDateChangedListener()
            { _: DatePicker, year: Int, mon: Int, day: Int ->
                val month = mon+1

                this.date = "" + year + "/" + ((month)/10) + month%10+ "/" + (day/10) + day%10
            }
            val toDoName = view.findViewById<EditText>(R.id.et_todo)
            val today = Calendar.getInstance()
            view.findViewById<DatePicker>(R.id.calendar).updateDate(today.get(Calendar.YEAR),
                today.get(
                    Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH))
            //calendar
            dialog.setView(view)

            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                if (toDoName.text.isNotEmpty() && toDoName.length() <= 18) {

                    val toDo = ToDo()
                    toDo.name = toDoName.text.toString()
                    toDo.img = this.pic
                    toDo.time = this.date
                    toDo.priority = this.priority
                    toDo.notified = 0
                    Log.i("Priority", this.priority.toString())
                    dbHandler.addToDo(toDo)
                    refreshList()
                }
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

            }
            dialog.show()
        }

    }
    fun notifier(toDo: ToDo)
    {
        val today = Calendar.getInstance()
        today.add(Calendar.DAY_OF_MONTH, 1)
        val year = today.get(Calendar.YEAR).toInt()
        val month = today.get(Calendar.MONTH).toInt() + 1
        val day = today.get(Calendar.DAY_OF_MONTH)
        val thisDay: String = "" + year + "/" + ((month)/10) + month%10+ "/" + (day/10) + day%10
        if(toDo.time.equals(thisDay))
        {
            Log.i("deadline tomorrow", toDo.name)
        }
        if(toDo.time.equals(thisDay) && toDo.notified == 0)
        {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            toDo.notified = 1;
            dbHandler.updateToDo(toDo)
            notificationChanel = NotificationChannel(chanelId,
                toDo.name,
                NotificationManager.IMPORTANCE_HIGH)
            notificationChanel.enableLights(true)
            notificationChanel.lightColor = Color.GREEN
            notificationChanel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChanel)

            builder = Notification.Builder(this, chanelId)
                .setContentTitle("Termin zadania mija jutro!")
                .setContentText("Termin zadania: " + toDo.name + " mija jutro, nie zapomnij go wykonać!")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,
                    R.drawable.ic_launcher_foreground))
                .setContentIntent(pendingIntent)
            notificationManager.notify(123, builder.build())

        }
    }

    fun allDone(toDo: ToDo): Boolean
    {
        val list = dbHandler.getToDoItems(toDo.id)
        var done: Boolean = true
        if(list.size < 1)return false
        for(item: ToDoItem in list)
        {
            if(!item.isCompleted)
            {
                done = false
            }
        }
        if(done)toDo.notified = 1
        return done
    }
    fun updateToDo(toDo: ToDo)
    {
        val dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.new_todo, null)
        //priority
        view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.appCompatButton1).setOnClickListener()
        {
            this.priority = 0
        }
        view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.appCompatButton2).setOnClickListener()
        {
            this.priority = 1
        }
        view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.appCompatButton3).setOnClickListener()
        {
            this.priority = 2
        }
        //priority
        //calendar
        view.findViewById<DatePicker>(R.id.calendar).setOnDateChangedListener()
        { _: DatePicker, year: Int, mon: Int, day: Int ->
            val month = mon+1

            this.date = "" + year + "/" + ((month)/10) + month%10+ "/" + (day/10) + day%10
        }
        val toDoName = view.findViewById<EditText>(R.id.et_todo)
        toDoName.setText(toDo.name)
        //val today = Calendar.getInstance()
        //view.findViewById<DatePicker>(R.id.calendar).updateDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH))
        //calendar
        dialog.setView(view)

        dialog.setPositiveButton("Update") { _: DialogInterface, _: Int ->
            if (toDoName.text.isNotEmpty() && toDoName.length() <= 18) {

                toDo.name = toDoName.text.toString()
                toDo.img = this.pic
                toDo.time = this.date
                toDo.priority = this.priority
                Log.i("Priority", this.priority.toString())
                dbHandler.updateToDo(toDo)
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

    private fun refreshList(){
        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        val list: MutableList<ToDo> = dbHandler.getToDos(sortingTarget)
        for (i in 0..(list.size - 1))
        {
            notifier(list[i])
            if(allDone(list[i]))
            {
                list[i].compleated = true
            }
        }
        recyclerView.adapter = DashboardAdapter(this, list)
    }


    class DashboardAdapter(val activity: MainActivity, val list: MutableList<ToDo>) :
        RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.layout_listitem,
                p0,
                false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
            holder.toDoName.text = list[p1].name
            if(list[p1].compleated)
            {
                holder.toDoName.setPaintFlags(holder.toDoName.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            }
            val res = list[p1].img
            if(res == "Type3")
            {
                holder.src.setImageResource(R.mipmap.type3)
            }
            else if(res == "Type2")
            {
                holder.src.setImageResource(R.mipmap.type2)
            }
            else
            {
                holder.src.setImageResource(R.mipmap.type1)
            }
            val important = list[p1].priority
            if(important == 2)
            {
                holder.priority.setImageResource(R.mipmap.importanthigh)
            }
            else if(important == 1)
            {
                holder.priority.setImageResource(R.mipmap.importantmid)
            }
            else
            {
                holder.priority.setImageResource(R.mipmap.importantlow)
            }
            holder.taskDate.text = list[p1].time
            holder.itemView.setOnClickListener()
            {
                val intent = Intent(activity, itemActivity::class.java)
                intent.putExtra(INTENT_TODO_ID, list[p1].id)
                intent.putExtra(INTENT_TODO_NAME, list[p1].name)
                activity.startActivity(intent)
            }
            holder.itemView.setOnLongClickListener() {
                val popup = PopupMenu(activity, holder.itemView)
                popup.inflate(R.menu.dashboard_child)
                popup.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_edit -> {
                            activity.updateToDo(list[p1])
                        }
                        R.id.menu_delete -> {
                            val dialog = AlertDialog.Builder(activity)
                            dialog.setTitle("Czy na pewno?")
                            dialog.setMessage("Czy na pewno chcesz usunąć to zadanie?")
                            dialog.setPositiveButton("Tak") { _: DialogInterface, _: Int ->
                                activity.dbHandler.deleteToDo(list[p1].id)
                                activity.refreshList()
                            }
                            dialog.setNegativeButton("Nie") { _: DialogInterface, _: Int ->

                            }
                            dialog.show()
                        }
                        R.id.menu_mark_as_completed -> {
                            activity.dbHandler.updateToDoItemCompletedStatus(list[p1].id, true)
                            activity.refreshList()
                        }
                        R.id.menu_reset -> {
                            list[p1].notified = 0
                            activity.dbHandler.updateToDo(list[p1])
                            activity.dbHandler.updateToDoItemCompletedStatus(list[p1].id, false)
                            activity.refreshList()
                        }
                    }
                    true
                }

                popup.show()

                true
            }

        }

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val toDoName: TextView = v.findViewById(R.id.Title)
            val src: de.hdodenhof.circleimageview.CircleImageView =v.findViewById(R.id.image)
            val taskDate: TextView = v.findViewById(R.id.Date)
            //val menu : RelativeLayout = v.findViewById(R.id.parent_layout)
            val priority : ImageView = v.findViewById(R.id.important)
        }
    }

    fun button1(view: View){
        this.pic = "Type1"
    }
    fun button2(view: View){
        this.pic = "Type2"
    }
    fun button3(view: View){
        this.pic = "Type3"
    }

    fun sortingMenu(view: View) {
        val dots = findViewById<View>(R.id.sorting)
        val popup = PopupMenu(this, dots)
        popup.inflate(R.menu.sorting_menu)
        popup.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_sort1 -> {
                    this.sortingTarget = 1
                    refreshList()
                }
                R.id.menu_sort2 -> {
                    this.sortingTarget = 2
                    refreshList()
                }
                R.id.menu_sort3 -> {
                    this.sortingTarget = 3
                    refreshList()

                }
                R.id.menu_sort4 -> {
                    this.sortingTarget = 4
                    refreshList()
                }
            }
            true
        }
        popup.show()
    }
}

