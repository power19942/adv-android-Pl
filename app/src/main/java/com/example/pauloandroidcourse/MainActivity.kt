package com.example.pauloandroidcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var db = FirebaseFirestore.getInstance()
    var journalRef = db.document("journal/First thoughts")
    var collectionRef = db.collection("journal")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        save_button.setOnClickListener {
            addThought()
        }

        getThoughts()

        show_data.setOnClickListener {
            journalRef.get().addOnCompleteListener {

                if (it.isSuccessful && it.isComplete) {
//                    var doc = it.result
//                    var title = doc?.getString("title")
//
//                    var thought = doc?.getString("thought")
//                    rec_title.text = "$title\n$thought"
                    //getThoughts()
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
            journalRef.update("title", FieldValue.delete())
            //delete all
            //journalRef.delete()
        }


    }

    fun addThought() {
        var title: EditText = findViewById(R.id.edit_text_title)
        var thought: EditText = findViewById(R.id.edit_text_thoughts)
        var j = Journal(title.text.toString(), thought.text.toString())

        collectionRef.add(j)
            .addOnSuccessListener {
                Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this@MainActivity, "Faild", Toast.LENGTH_SHORT).show()
            }
    }

    fun getThoughts(){
        var res = ""
        collectionRef.get()
            .apply {
                addOnSuccessListener {

                    for (item in it){

                        var title = item?.getString("title")

                        var thought = item?.getString("thought")
                        res += "$title\n$thought"
                    }

                    rec_title.text = res
                }

                addOnFailureListener {

                }
            }
    }

    override fun onStart() {
        super.onStart()
        var res = ""
        collectionRef.addSnapshotListener(this) { snapshots, ex ->
            if (ex != null) {
                Toast.makeText(this@MainActivity, "Error on Start", Toast.LENGTH_SHORT).show()
            }

            for (item in snapshots!!.iterator()){

                var title = item?.getString("title")

                var thought = item?.getString("thought")
                res += "$title\n$thought"
            }

            rec_title.text = res

        }
    }


}
