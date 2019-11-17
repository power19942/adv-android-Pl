package com.example.pauloandroidcourse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var db = FirebaseFirestore.getInstance()
    var journalRef = db.document("journal/First thoughts")
    var collectionRef = db.collection("journal")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        startButton.setOnClickListener {
            startActivity(Intent(this@MainActivity,LoginActivity::class.java))
        }


    }
}
