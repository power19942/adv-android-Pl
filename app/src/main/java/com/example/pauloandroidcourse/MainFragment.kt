package com.example.pauloandroidcourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.ListFragment
import com.example.pauloandroidcourse.data.Course

class MainFragment : ListFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(activity,"",Toast.LENGTH_SHORT).show()
        var courses = ArrayList<Course>()
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        courses.add(Course("a course",0))
        var adapter = CustomAdater(activity!!.applicationContext,R.layout.item_item,courses)
        listAdapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container,false)
    }
}