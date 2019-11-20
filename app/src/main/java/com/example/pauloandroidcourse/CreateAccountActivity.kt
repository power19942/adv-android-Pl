package com.example.pauloandroidcourse

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.pauloandroidcourse.util.JournalApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_account.*
import java.lang.Exception

class CreateAccountActivity : AppCompatActivity() {

    var firebaseAuth = FirebaseAuth.getInstance()

    lateinit var firebaseAuthListener: FirebaseAuth.AuthStateListener
    lateinit var currentUser: FirebaseUser

    //firestore connection
    var db = FirebaseFirestore.getInstance()
    var collectionRefrence = db.collection("Users")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        firebaseAuthListener = FirebaseAuth.AuthStateListener {
            currentUser = firebaseAuth.currentUser!!

            if (currentUser != null) {

            } else {

            }

        }

        create_acct_button.setOnClickListener {
            if (email_account.text.toString().isNotEmpty()
                && password_account.text.toString().isNotEmpty()
                && username_account.text.toString().isNotEmpty()
            )
                createUserEmailAccount(
                    email_account.text.toString(),
                    password_account.text.toString(),
                    username_account.text.toString()
                )
            else
                Toast.makeText(
                    this@CreateAccountActivity,
                    "Empty failds not allowed",
                    Toast.LENGTH_SHORT
                ).show()

        }

        create_acct_progress

        email_account

        password_account

        username_account
    }

    private fun createUserEmailAccount(email: String, password: String, username: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
            create_acct_progress.visibility = View.VISIBLE
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        currentUser = firebaseAuth.currentUser!!
                        assert(currentUser != null)
                        var currentUserId = currentUser.uid

                        var userObject = HashMap<String, String>()
                        userObject.put("userId", currentUserId)
                        userObject.put("username", username)

                        collectionRefrence.add(userObject).apply {
                            addOnSuccessListener { docuemntRefrece ->

                                docuemntRefrece.get().apply {
                                    addOnCompleteListener { task ->
                                        if (task.result!!.exists()) {
                                            create_acct_progress.visibility = View.INVISIBLE
                                            var name = task.getResult()?.getString("username")
                                            var journalApi = JournalApi.getJournalInstance()
                                            journalApi?.userId = currentUserId
                                            journalApi?.username = name!!
                                            var intent = Intent(
                                                this@CreateAccountActivity,
                                                PostJournalActivity::class.java
                                            )
                                            intent.putExtra("username", name)
                                            intent.putExtra("userId", currentUserId)
                                            startActivity(intent)
                                        } else {
                                            create_acct_progress.visibility = View.INVISIBLE
                                        }
                                    }
                                }

                            }

                            addOnFailureListener {

                            }
                        }


                    } else {

                    }
                }.addOnFailureListener {

                }

        } else {

        }
    }

    override fun onStart() {
        super.onStart()
        try {
            currentUser = firebaseAuth.currentUser!!
        }catch (e:Exception){
            Toast.makeText(this@CreateAccountActivity,"current user error",Toast.LENGTH_SHORT).show()
        }
        

    }
}
