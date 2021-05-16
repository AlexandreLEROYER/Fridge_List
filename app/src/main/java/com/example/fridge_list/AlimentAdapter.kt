package com.example.fridge_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.controler.BDD
import com.example.model.Item
import com.google.api.LogDescriptor

class AlimentAdapter(private val listener: AlimentAdapterListener) : RecyclerView.Adapter<AlimentAdapter.ViewHolder>() {
    private var data: ArrayList<Item> = ArrayList<Item>()
    fun setData(data: ArrayList<Item> ) {
        this.data = data
        Log.d("DATA",""+data)
        println(data.size)
        println(data)
        notifyDataSetChanged()
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val texte: TextView
        val quantite : TextView
        init{
            texte = view.findViewById(R.id.alimentListe)
            quantite = view.findViewById(R.id.alimentListeInfo)
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
        val item = data[position]
        var entretemps = BDD.findName(item.id)
        Log.d("MDR", ""+BDD.listeIngredientAll)
        holder.quantite.text = item.qt.toString()
        holder.texte.text = entretemps.nom
        //holder.tvName.text = contact.name.capitalize(Locale.getDefault())
        holder.itemView.setOnClickListener { listener.onUserClicked(item) }
    }
    override fun getItemCount(): Int {
        return data.size
    }

}