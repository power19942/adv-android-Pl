package com.example.pauloandroidcourse

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    lateinit var babyItem: TextView
    lateinit var qty: TextView
    lateinit var color: TextView
    lateinit var size: TextView
    lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            createPopup()
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
        }
    }

    fun createPopup() {
        var builder = AlertDialog.Builder(this@Main2Activity)
        var view = layoutInflater.inflate(R.layout.popup, null)
        babyItem = view.findViewById<EditText>(R.id.babyItem)
        qty = view.findViewById<EditText>(R.id.qty)
        color = view.findViewById<EditText>(R.id.color)
        size = view.findViewById<EditText>(R.id.size)
        saveBtn = view.findViewById<Button>(R.id.save)
        builder.setView(view)
        var dialog = builder.create()
        dialog.show()
    }
}
