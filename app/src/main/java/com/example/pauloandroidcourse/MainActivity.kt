package com.example.pauloandroidcourse

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.*
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var db = FirebaseFirestore.getInstance()
    var journalRef = db.document("journal/First thoughts")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        save_button.setOnClickListener {
            var title: EditText = findViewById(R.id.edit_text_title)
            var thought: EditText = findViewById(R.id.edit_text_thoughts)
            var data = HashMap<String, String>()
            data.put("title", title.text.toString())
            data.put("thought", thought.text.toString())

            db.collection("journal")
                .document("First thoughts")
                .set(data)
                .addOnSuccessListener {
                    Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this@MainActivity, "Faild", Toast.LENGTH_SHORT).show()
                }
        }

        show_data.setOnClickListener {
            journalRef.get().addOnCompleteListener {

                if (it.isSuccessful && it.isComplete) {
                    var doc = it.result
                    var title = doc?.getString("title")

                    var thought = doc?.getString("thought")
                    rec_title.text = "$title\n$thought"
                    Toast.makeText(this@MainActivity, "Result success", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener {
                Toast.makeText(this@MainActivity, "Result error", Toast.LENGTH_SHORT).show()
            }
        }

        update_data.setOnClickListener {
            var title = edit_text_title.text.toString()
            var thoughts = edit_text_thoughts.text.toString()
            var data = HashMap<String, Any>()
            data.put("title", title)
            data.put("thought", thoughts)

            journalRef.update(data)
                .addOnSuccessListener {
                    Toast.makeText(this@MainActivity, "success", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this@MainActivity, "faild", Toast.LENGTH_SHORT).show()
                }
        }

        delete_thought.setOnClickListener {
            //delete one
            journalRef.update("title",FieldValue.delete())
            //delete all
            //journalRef.delete()
        }


    }

    override fun onStart() {
        super.onStart()
        journalRef.addSnapshotListener(this) { doc, ex ->
            if (ex != null) {
                Toast.makeText(this@MainActivity, "Error on Start", Toast.LENGTH_SHORT).show()
            } else if(doc!!.exists()){
                var title = doc?.getString("title")

                var thought = doc?.getString("thought")
                rec_title.text = "$title\n$thought"

            }else{
                rec_title.text = "no thoughts "
            }
        }
    }


}
