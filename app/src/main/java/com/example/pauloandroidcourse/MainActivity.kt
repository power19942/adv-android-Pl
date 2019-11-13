package com.example.pauloandroidcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pauloandroidcourse.data.DatabaseHandler
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import android.widget.ArrayAdapter
import com.example.pauloandroidcourse.model.Contact
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




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
        for (i in 0 until contactsList.size){
            contactArrayList.add(contactsList[i])
            Log.d("548DB",contactsList[i].name)
        }

        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,contactArrayList)
        list.adapter = adapter
    }
}
