package com.example.pauloandroidcourse

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pauloandroidcourse.model.NoDoViewModel
import com.example.pauloandroidcourse.ui.NoDoListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.example.pauloandroidcourse.model.NoDo
import android.widget.Toast
import androidx.lifecycle.Observer


class MainActivity : AppCompatActivity() {

    lateinit var noDoViewModel: NoDoViewModel
    lateinit var adapter: NoDoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var data = null
        adapter = NoDoListAdapter(this@MainActivity)
        recycler_list.adapter = adapter
        recycler_list.layoutManager = LinearLayoutManager(this@MainActivity)
        noDoViewModel = ViewModelProviders.of(this)
            .get(NoDoViewModel::class.java)
        noDoViewModel.allNoDos.observe(this, object : Observer<List<NoDo>> {

            override fun onChanged(nodos: List<NoDo>) {
                adapter.setNoDos(nodos)
            }

        })

        fab.setOnClickListener {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 333 && resultCode == Activity.RESULT_OK) {
            var nodo = NoDo(0, data!!.getStringExtra("${packageName}.reply"))
            noDoViewModel.insert(nodo)
        } else
            Toast.makeText(this@MainActivity, "Error in Result", Toast.LENGTH_SHORT).show()
    }
}
