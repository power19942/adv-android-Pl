package com.example.pauloandroidcourse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

    lateinit var firebaseAuth : FirebaseAuth
    lateinit var firebaseAuthListener: FirebaseAuth.AuthStateListener
    lateinit var currentUser: FirebaseUser

    //firestore connection
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        create_acct_button_login.setOnClickListener {
            startActivity(Intent(this@LoginActivity,CreateAccountActivity::class.java))
        }


    }
}
