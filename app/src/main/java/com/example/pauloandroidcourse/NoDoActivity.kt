package com.example.pauloandroidcourse

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_no_do.*

class NoDoActivity : AppCompatActivity() {
    private val EXTRA_REPLY = "${packageName}.reply"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_do)

        button_save.setOnClickListener {
            var replyIntent = Intent()
            if (edit_nodo.text.isEmpty())
                setResult(Activity.RESULT_CANCELED, replyIntent)
            else {
                var nodoString = edit_nodo.text
                replyIntent.putExtra(EXTRA_REPLY, nodoString)
            }
            finish()
        }
    }
}
