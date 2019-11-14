package com.example.pauloandroidcourse

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.pauloandroidcourse.data.DatabaseHandler
import com.example.pauloandroidcourse.model.Item
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.popup.*

class Main2Activity : AppCompatActivity() {

    lateinit var babyItem: TextView
    lateinit var qty: TextView
    lateinit var color: TextView
    lateinit var size: TextView
    lateinit var saveBtn: Button
    lateinit var databaseHandler: DatabaseHandler
    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)
        databaseHandler = DatabaseHandler(applicationContext)

        if(databaseHandler.itemsCount > 0 ){
            startActivity(Intent(this@Main2Activity,ListActivity::class.java))
        }

        fab.setOnClickListener {
            createPopup()
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)//.show()
        }
    }

    fun createPopup() {
        var builder = AlertDialog.Builder(this@Main2Activity)
        var view = layoutInflater.inflate(R.layout.popup, null)
        babyItem = view.findViewById<EditText>(R.id.babyItem)
        qty = view.findViewById<EditText>(R.id.qty)
        color = view.findViewById<EditText>(R.id.color)
        size = view.findViewById<EditText>(R.id.size)
        saveBtn = view.findViewById(R.id.save)
        saveBtn.setOnClickListener {
            if (babyItem.text.toString().isNotEmpty()
                && color.text.toString().isNotEmpty()
                && qty.text.toString().isNotEmpty()
                && size.text.toString().isNotEmpty()
            ) {
                saveItem(it)
            } else {
                Snackbar.make(it, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                    .show();
            }
        }
        builder.setView(view)
        dialog = builder.create()
        dialog.show()
    }

    fun saveItem(view: View) {
        var newItem = babyItem.text.trim().toString()
        var newColor = color.text.trim().toString()
        var newQty = Integer.parseInt(qty.text.trim().toString())
        var newSize = Integer.parseInt(size.text.trim().toString())

        databaseHandler.addItem(
            Item(
                0,
                newItem,
                newColor,
                newQty,
                newSize,
                System.currentTimeMillis().toString()
            )
        )

        Snackbar.make(view, "item added", Snackbar.LENGTH_SHORT).show()
        Handler().postDelayed({
            dialog.dismiss()
            startActivity(Intent(this@Main2Activity,ListActivity::class.java))
        }, 1200)
    }
}

