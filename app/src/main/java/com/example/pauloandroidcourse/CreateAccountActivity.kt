package com.example.pauloandroidcourse

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {

    var firebaseAuth = FirebaseAuth.getInstance()
    lateinit var firebaseAuthListener: FirebaseAuth.AuthStateListener
    lateinit var currentUser: FirebaseUser

    //firestore connection
    var db = FirebaseFirestore.getInstance()


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
            //createUserEmailAccount(email, password, username)
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
                    if (it.isSuccessful){
                        currentUser = firebaseAuth.currentUser!!
                        var currentUserId = currentUser.uid

                    }else{

                    }
                }.addOnFailureListener {

                }

        } else {

        }
    }

    override fun onStart() {
        super.onStart()
        currentUser = firebaseAuth.currentUser!!

    }
}
