package com.example.pauloandroidcourse.ui

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pauloandroidcourse.R
import com.example.pauloandroidcourse.model.NoDo

class NoDoListAdapter(var context: Context) :
    RecyclerView.Adapter<NoDoListAdapter.NoDoViewHolder>() {

    var data: List<NoDo>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoDoViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent)
        return NoDoViewHolder(view)
    }

    override fun getItemCount(): Int {
            return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: NoDoViewHolder, position: Int) {
            holder.text.text = data?.get(position)?.noDo ?: "No Data"
    }

    fun setNoDos(nodos: List<NoDo>) {
        data = nodos
        notifyDataSetChanged()
    }


    class NoDoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var text: TextView = view.findViewById(R.id.textView)
    }
}