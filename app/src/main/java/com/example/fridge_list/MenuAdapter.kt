package com.example.fridge_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(private val listener: MenuAdapterListener) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private var data: ArrayList<String> = ArrayList<String>()

    fun setData(data: ArrayList<String> ) {
        this.data = data
        println(data.size)
        println(data)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val texte: TextView
        init{
            texte = view.findViewById(R.id.nomdeliste)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.setup_liste, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nomliste = data[position]
        holder.texte.text = nomliste
        holder.itemView.setOnClickListener { listener.onUserClicked(nomliste) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}