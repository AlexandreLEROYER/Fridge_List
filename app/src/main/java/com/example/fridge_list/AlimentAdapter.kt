package com.example.fridge_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AlimentAdapter(private val listener: AlimentAdapterListener) : RecyclerView.Adapter<AlimentAdapter.ViewHolder>() {
    private var data: ArrayList<Aliment> = ArrayList()
    fun setData(data: ArrayList<Aliment> ) {
        this.data = data
        println(data.size)
        println(data)
        notifyDataSetChanged()
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val texte: TextView
        init{
            texte = view.findViewById(R.id.alimentListe)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return ViewHolder(view)
        //return ViewHolder(
        //        LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        //)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val aliment = data[position]
        holder.texte.text = aliment.infos
        //holder.tvName.text = contact.name.capitalize(Locale.getDefault())
        holder.itemView.setOnClickListener { listener.onUserClicked(aliment) }
    }
    override fun getItemCount(): Int {
        return data.size
    }

}