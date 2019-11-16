package com.example.pauloandroidcourse

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pauloandroidcourse.data.Course

class MainActivity : AppCompatActivity(), ListFragment.CallBacks {
    
    override fun onItemSelected(course: Course) {
        
        Toast.makeText(this@MainActivity,"clicked",Toast.LENGTH_SHORT).show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var manager = supportFragmentManager
//        var fragment = ListFragment()
//        manager.beginTransaction()
//            .add(R.id.frame,fragment)
//            .commit()



    }
}

