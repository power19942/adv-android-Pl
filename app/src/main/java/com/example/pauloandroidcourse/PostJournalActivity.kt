package com.example.pauloandroidcourse

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.pauloandroidcourse.model.Journal
import com.example.pauloandroidcourse.util.JournalApi
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_post_journal.*
import java.lang.Exception
import java.util.*

class PostJournalActivity : AppCompatActivity() {

    var firebaseAuth = FirebaseAuth.getInstance()

    lateinit var firebaseAuthListener: FirebaseAuth.AuthStateListener
    lateinit var user: FirebaseUser
    var currentUserId: String? = null
    var currentUsername: String? = null
    //firestore connection
    var db = FirebaseFirestore.getInstance()
    var collectionRefrence = db.collection("Journal")

    //storeage
    var storageReference = FirebaseStorage.getInstance().reference

    lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_journal)

        post_progressBar.visibility = View.INVISIBLE

        firebaseAuthListener = FirebaseAuth.AuthStateListener {
            user = firebaseAuth.currentUser!!
            if (user != null) {

            } else {


            }
        }

        if (JournalApi.getJournalInstance() != null) {
            currentUserId = JournalApi.getJournalInstance()?.userId
            currentUsername = JournalApi.getJournalInstance()?.username

            post_username_textview.text = currentUsername
        }


        post_save_journal_button.setOnClickListener {

        }

        postCameraButton.setOnClickListener {
            var galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, 321)
        }


//        var bundle = intent.extras
//        var username = bundle?.getString("username")
//        var userId = bundle?.getString("userId")
    }

    fun saveJournal() {
        var title = post_title_et.text.trim().toString()
        var thoughts = post_description_et.text.trim().toString()

        post_progressBar.visibility = View.VISIBLE

        if (title.isNotEmpty() && thoughts.isNotEmpty() && imageUri != null) {

            var filePath = storageReference!!.child("journal_images")
                .child("my_image_${Timestamp.now().seconds}")



            filePath?.putFile(imageUri).apply {

                addOnSuccessListener {
                    filePath.downloadUrl.addOnSuccessListener {
                        var journal = Journal(
                            title,
                            thoughts,
                            it.toString(),
                            currentUserId!!,
                            Timestamp(Date()),
                            currentUsername!!
                        )

                        collectionRefrence.add(journal)
                            .addOnFailureListener {
                                post_progressBar.visibility = View.INVISIBLE

                            }
                            .addOnFailureListener {
                                post_progressBar.visibility = View.INVISIBLE
                                startActivity(
                                    Intent(
                                        this@PostJournalActivity,
                                        JournalListActivity::class.java
                                    )
                                )
                                finish()
                            }
                    }


                }

                addOnFailureListener {
                    post_progressBar.visibility = View.INVISIBLE
                }
            }

        } else {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 321 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imageUri = data.data!!
                post_imageView.setImageURI(imageUri)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        try {

            user = firebaseAuth.currentUser!!

        } catch (e: Exception) {
            Toast.makeText(this@PostJournalActivity, "OnStart User Error", Toast.LENGTH_LONG).show()
        }

    }

    override fun onStop() {
        super.onStop()
        if (firebaseAuth != null)
            firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }
}
