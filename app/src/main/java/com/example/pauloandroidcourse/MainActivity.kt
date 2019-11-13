package com.example.pauloandroidcourse

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pauloandroidcourse.data.DatabaseHandler
import com.example.pauloandroidcourse.model.Contact
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var contactArrayList = ArrayList<Contact>()
        val db = DatabaseHandler(this@MainActivity)


//
//        db.addContact( Contact(0,"James","213986"))
//        db.addContact( Contact(0,"Greg","098765"))
//        db.addContact( Contact(0,"Helena","40678765"))
//        db.addContact( Contact(0,"Carimo","768345"))
//        db.addContact(Contact(0,"Silo","3445"))
//        db.addContact( Contact(0,"Santos","6665"))
//        db.addContact( Contact(0,"Litos","5344"))
//        db.addContact( Contact(0,"Karate","96534"))
//        db.addContact( Contact(0,"Guerra","158285"))
//        db.addContact( Contact(0,"Gema","78130"))
        var contactsList = db.getAllContacts()
        for (i in 0 until contactsList.size) {
            contactArrayList.add(contactsList[i])
            contactArrayList.add(contactsList[i])
            Log.d("548DB", contactsList[i].name)
        }

        var adapter = RecyclerItem(this@MainActivity,contactArrayList)
        list.adapter = adapter
        list.layoutManager = GridLayoutManager(applicationContext,2) //LinearLayoutManager(applicationContext)
    }
}


