package com.example.pauloandroidcourse

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.ListFragment
import com.example.pauloandroidcourse.data.Course
import java.lang.Exception

class ListFragment : ListFragment(){

    var courses: ArrayList<Course>? = null
    var listener:CallBacks? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var courses = ArrayList<Course>()
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        courses.add(Course("a course", 0))
        var adapter = CustomAdater(activity!!.applicationContext, R.layout.item_item, courses)
        listAdapter = adapter
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {

        var course = courses?.get(position)
       listener?.onItemSelected(course!!)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is CallBacks){
            listener = context
        }else{
            throw Exception("Shit")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface CallBacks {
        fun onItemSelected(course: Course)
    }
}

