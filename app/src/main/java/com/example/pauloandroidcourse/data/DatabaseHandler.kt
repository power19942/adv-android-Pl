package com.example.pauloandroidcourse.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.pauloandroidcourse.util.*
import com.example.pauloandroidcourse.model.Contact
import android.R



class DatabaseHandler(
    var context: Context
//    name: String,
//    //factory not used at all
//    factory: SQLiteDatabase.CursorFactory,
//    version: Int
) : SQLiteOpenHelper(context, Util.DATABASE_NAME, null /*factory*/, Util.DATABASE_VERSION) {

    //create db table
    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_CONTACT_TABLE = "create table ${Util.TABLE_NAME} " +
                "(${Util.KEY_ID} integer primary key," +
                "${Util.KEY_NAME} text," +
                "${Util.KEY_PHONE} text)"
        db?.execSQL(CREATE_CONTACT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        var DROP_TABLE: String = context.getString(com.example.pauloandroidcourse.R.string.drop_table)
        db?.execSQL(DROP_TABLE, arrayOf(Util.DATABASE_NAME))

        onCreate(db)
    }

    fun addContact(contact: Contact) {
        var db = this.writableDatabase
        var values = ContentValues()
        values.put(Util.KEY_NAME, contact.name)
        values.put(Util.KEY_PHONE, contact.phone)
        db.insert(Util.TABLE_NAME, null, values)
        db.close()
    }

    fun getContact(id: Int): Contact {
        var db = this.readableDatabase
        //cursor is an object to iterate throw table rows
        var cursor = db.query(
            Util.TABLE_NAME,
            arrayOf(Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE),
            Util.KEY_ID + "=?",
            arrayOf(
                id.toString()
            ),
            null, null, null
        )

        if (cursor != null)
            cursor.moveToFirst()

        var contact = Contact(cursor.getString(0).toInt(), cursor.getString(1), cursor.getString(2))
        return contact
    }

    fun getAllContacts(): ArrayList<Contact> {
        var db = this.readableDatabase
        var contactsList = ArrayList<Contact>()
        //cursor is an object to iterate throw table rows
        var cursor = db.rawQuery("SELECT * FROM " + Util.TABLE_NAME, null)

        if (cursor.moveToFirst()) {
            do {
                var contact =
                    Contact(cursor.getString(0).toInt(), cursor.getString(1), cursor.getString(2))
                contactsList.add(contact)
            } while (cursor.moveToNext())
        }

        return contactsList
    }

    //Update contact
    fun updateContact(contact: Contact): Int {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(Util.KEY_NAME, contact.name)
        values.put(Util.KEY_PHONE, contact.phone)

        //update the row
        //update(tablename, values, where id = 43)
        return db.update(
            Util.TABLE_NAME, values, Util.KEY_ID + "=?",
            arrayOf(contact.id.toString())
        )
    }

    //Delete single contact
    fun deleteContact(contact: Contact) {
        val db = this.writableDatabase

        db.delete(
            Util.TABLE_NAME, Util.KEY_ID + "=?",
            arrayOf(contact.id.toString())
        )

        db.close()
    }

    //Get contacts count
    fun getCount(): Int {
        val countQuery = "SELECT * FROM " + Util.TABLE_NAME
        val db = this.readableDatabase
        val cursor = db.rawQuery(countQuery, null)

        return cursor.count

    }
}