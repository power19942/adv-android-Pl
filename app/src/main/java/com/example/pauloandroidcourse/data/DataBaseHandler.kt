package com.example.pauloandroidcourse.data

import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.pauloandroidcourse.model.Item
import com.example.pauloandroidcourse.util.Constants
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList


class DatabaseHandler(private val context: Context) :
    SQLiteOpenHelper(context, Constants.DB_NAME, null, Constants.DB_VERSION) {

    //Get all Items
    //convert Timestamp to something readable
    // Feb 23, 2020
    //Add to arraylist
    val allItems: ArrayList<Item>
        get() {
            val db = this.readableDatabase

            var itemList = ArrayList<Item>()

            val cursor = db.query(
                Constants.TABLE_NAME,
                arrayOf(
                    Constants.KEY_ID,
                    Constants.KEY_BABY_ITEM,
                    Constants.KEY_COLOR,
                    Constants.KEY_QTY_NUMBER,
                    Constants.KEY_ITEM_SIZE,
                    Constants.KEY_DATE_NAME
                ), null, null, null, null,
                Constants.KEY_DATE_NAME + " DESC"
            )

            if (cursor.moveToFirst()) {
                do {

                    var id =
                        (Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))))
                    var name = (cursor.getString(cursor.getColumnIndex(Constants.KEY_BABY_ITEM)))
                    var color = (cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)))
                    var qty = (cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)))
                    var size = (cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE)))
                    val dateFormat = DateFormat.getDateInstance()
                    val formattedDate = dateFormat.format(
                        Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME)))
                            .getTime()
                    )
                    var dateItemAdded = (formattedDate)
                    var item = Item(id,name,color,qty,size,dateItemAdded)
                    itemList.add(item)
                } while (cursor.moveToNext())
            }
            return itemList

        }

    //Todo: getItemCount
    val itemsCount: Int
        get() {
            val countQuery = "SELECT * FROM " + Constants.TABLE_NAME
            val db = this.readableDatabase

            val cursor = db.rawQuery(countQuery, null)

            return cursor.count

        }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_BABY_TABLE = ("CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_BABY_ITEM + " INTEGER,"
                + Constants.KEY_COLOR + " TEXT,"
                + Constants.KEY_QTY_NUMBER + " INTEGER,"
                + Constants.KEY_ITEM_SIZE + " INTEGER,"
                + Constants.KEY_DATE_NAME + " LONG);")

        db.execSQL(CREATE_BABY_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME)

        onCreate(db)
    }

    // CRUD operations
    fun addItem(item: Item) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(Constants.KEY_BABY_ITEM, item.name)
        values.put(Constants.KEY_COLOR, item.color)
        values.put(Constants.KEY_QTY_NUMBER, item.qty)
        values.put(Constants.KEY_ITEM_SIZE, item.size)
        values.put(
            Constants.KEY_DATE_NAME,
            System.currentTimeMillis()
        )//timestamp of the system

        //Inset the row
        db.insert(Constants.TABLE_NAME, null, values)

        Log.d("DBHandler", "added Item: ")
    }

    //Get an Item
    fun getItem(id: Int): Item {
        val db = this.readableDatabase

        val cursor = db.query(
            Constants.TABLE_NAME,
            arrayOf(
                Constants.KEY_ID,
                Constants.KEY_BABY_ITEM,
                Constants.KEY_COLOR,
                Constants.KEY_QTY_NUMBER,
                Constants.KEY_ITEM_SIZE,
                Constants.KEY_DATE_NAME
            ),
            Constants.KEY_ID + "=?",
            arrayOf(id.toString()), null, null, null, null
        )

        cursor?.moveToFirst()

        lateinit var item: Item
        if (cursor != null) {
            var id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID)))
            var name = cursor.getColumnIndex(Constants.KEY_BABY_ITEM).toString()
            var color = cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR))
            var qty = cursor.getInt(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER))
            var size = cursor.getInt(cursor.getColumnIndex(Constants.KEY_ITEM_SIZE))

            //convert Timestamp to something readable
            val dateFormat = DateFormat.getDateInstance()
            val formattedDate = dateFormat.format(
                Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME)))
                    .time
            ) // Feb 23, 2020

            var dateItemAdded = formattedDate
            item = Item(id, name, color, qty, size, dateItemAdded)

        }

        return item
    }

    //Todo: Add updateItem
    fun updateItem(item: Item): Int {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(Constants.KEY_BABY_ITEM, item.name)
        values.put(Constants.KEY_COLOR, item.color)
        values.put(Constants.KEY_QTY_NUMBER, item.qty)
        values.put(Constants.KEY_ITEM_SIZE, item.size)
        values.put(
            Constants.KEY_DATE_NAME,
            java.lang.System.currentTimeMillis()
        )//timestamp of the system

        //update row
        return db.update(
            Constants.TABLE_NAME, values,
            Constants.KEY_ID + "=?",
            arrayOf(item.id.toString())
        )

    }

    //Todo: Add Delete Item
    fun deleteItem(id: Int) {
        val db = this.writableDatabase
        db.delete(
            Constants.TABLE_NAME,
            Constants.KEY_ID + "=?",
            arrayOf(id.toString())
        )

        //close
        db.close()
        Log.d("DBHandler", "delete Item: ")
    }

}