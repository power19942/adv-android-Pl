package com.example.pauloandroidcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var posts =ArrayList<Todo>()

        setContentView(R.layout.activity_main)
        var req  = JsonArrayRequest(Request.Method.GET,"https://jsonplaceholder.typicode.com/todos",null,
            Response.Listener<JSONArray> {
                for (i in 0 until it.length())
                    Log.d("JSON",it.getJSONObject(i).getString("title"))
            },
            Response.ErrorListener {
                Toast.makeText(applicationContext,"error ${it.message}",Toast.LENGTH_LONG).show()
            })

        var queue = MySingleton(applicationContext).requestQueue
        queue.add(req)
        queue.start()
    }
}

class Todo(var title:String){}
