package com.example.pauloandroidcourse

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pauloandroidcourse.data.DatabaseHandler
import com.example.pauloandroidcourse.model.Item
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : AppCompatActivity() {

    lateinit var babyItem: TextView
    lateinit var qty: TextView
    lateinit var color: TextView
    lateinit var size: TextView
    lateinit var saveBtn: Button
    lateinit var databaseHandler: DatabaseHandler
    lateinit var dialog: AlertDialog
    lateinit var adapter: RecyclerViewAdapter
    lateinit var data: ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        databaseHandler = DatabaseHandler(applicationContext)
        data = DatabaseHandler(applicationContext).allItems
        adapter = RecyclerViewAdapter(data, databaseHandler, this@ListActivity)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(applicationContext)

        fab.setOnClickListener {
            createDialog()
        }
    }

    fun createDialog() {
        var builder = AlertDialog.Builder(this@ListActivity)
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
        }, 500)
        data.clear()
        data.addAll(databaseHandler.allItems)
        adapter.notifyDataSetChanged()
    }
}


class RecyclerViewAdapter(
    var data: ArrayList<Item>,
    var databaseHandler: DatabaseHandler,
    var context: Context
) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder>() {
    lateinit var builder: AlertDialog.Builder
    lateinit var dialog: AlertDialog


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, null)
        return RecyclerViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        var currentItem = data[position]
        holder.name.text = currentItem.name
        holder.color.text = currentItem.color
        holder.size.text = currentItem.size.toString()
        holder.qty.text = currentItem.qty.toString()
        holder.date.text = currentItem.dateItemAdded

        holder.edit.setOnClickListener {

        }

        holder.delete.setOnClickListener {
            builder = AlertDialog.Builder(context)
            var view = LayoutInflater.from(context).inflate(R.layout.confirmation_popup, null)
            var yesBtn: Button = view.findViewById(R.id.conf_yes_button)
            var noBtn: Button = view.findViewById(R.id.conf_no_button)
            builder.setView(view)
            dialog = builder.create()

            dialog.show()
            yesBtn.setOnClickListener {
                dialog.dismiss()
                databaseHandler.deleteItem(currentItem.id)
                data.remove(data[position])
                notifyItemRemoved(position)
            }

            noBtn.setOnClickListener {
                dialog.dismiss()
            }

        }

        holder.edit.setOnClickListener {
            builder = AlertDialog.Builder(context)
            val view = LayoutInflater.from(context).inflate(R.layout.popup, null)

            val saveButton: Button
            val babyItem: EditText
            val itemQuantity: EditText
            val itemColor: EditText
            val itemSize: EditText
            val title: TextView

            babyItem = view.findViewById(R.id.babyItem)
            itemQuantity = view.findViewById(R.id.qty)
            itemColor = view.findViewById(R.id.color)
            itemSize = view.findViewById(R.id.size)
            saveButton = view.findViewById(R.id.save)
            saveButton.text = "Update"
            title = view.findViewById(R.id.title)

            title.text = "Edit"
            babyItem.text = Editable.Factory.getInstance().newEditable(currentItem.name)
            itemQuantity.text =
                Editable.Factory.getInstance().newEditable(currentItem.qty.toString())
            itemColor.text =
                Editable.Factory.getInstance().newEditable(currentItem.color)
            itemSize.text = Editable.Factory.getInstance().newEditable(currentItem.size.toString())


            builder.setView(view)
            dialog = builder.create()
            dialog.show()

            saveButton.setOnClickListener {
                //update our item
                val databaseHandler = DatabaseHandler(context)

                //update items
                currentItem.name = (babyItem.text.toString())
                currentItem.color = (itemColor.text.toString())
                currentItem.qty = (Integer.parseInt(itemQuantity.text.toString()))
                currentItem.size = (Integer.parseInt(itemSize.text.toString()))

                if (!babyItem.text.toString().isEmpty()
                    && !itemColor.text.toString().isEmpty()
                    && !itemQuantity.text.toString().isEmpty()
                    && !itemSize.text.toString().isEmpty()
                ) {

                    databaseHandler.updateItem(currentItem)
                    notifyItemChanged(position, currentItem) //important!


                } else {
                    Snackbar.make(
                        view, "Fields Empty",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }

                dialog.dismiss()
            }
        }
    }

    class RecyclerViewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.item_name)
        var color: TextView = view.findViewById(R.id.item_color)
        var size: TextView = view.findViewById(R.id.item_size)
        var qty: TextView = view.findViewById(R.id.item_quantity)
        var date: TextView = view.findViewById(R.id.item_date)
        var edit: Button = view.findViewById(R.id.editButton)
        var delete: Button = view.findViewById(R.id.deleteButton)
    }
}