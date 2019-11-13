package com.example.pauloandroidcourse

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.pauloandroidcourse.model.Contact

class RecyclerItem(var context:Context ,var data: ArrayList<Contact>) :
    RecyclerView.Adapter<RecyclerItem.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = data[position]
        holder.name.text = item.name
        holder.phone.text = item.phone

        holder.card.setOnClickListener {
            Toast.makeText(context,item.name,Toast.LENGTH_SHORT).show()
            var i = Intent(context,SecondActivity::class.java)
            i.putExtra("name",item.name)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_row, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(var item: View) : RecyclerView.ViewHolder(item) {
        var name: TextView = item.findViewById(R.id.name)
        var phone: TextView = item.findViewById(R.id.phone)
        var card: CardView = item.findViewById(R.id.card)
    }
}