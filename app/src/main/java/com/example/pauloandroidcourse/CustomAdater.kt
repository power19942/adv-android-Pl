package com.example.pauloandroidcourse

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pauloandroidcourse.data.Course

class CustomAdater(context: Context, var res: Int, var data: List<Course>) :
    ArrayAdapter<Course>(context, res, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var course = data.get(position)
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_item, null)
        view.findViewById<TextView>(R.id.textView).text = course.name
//        view.findViewById<ImageView>(R.id.imageView).setImageResource(course.imageSource)
        return view
    }

}
