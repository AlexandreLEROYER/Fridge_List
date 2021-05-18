package com.example.fridge_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.model.Ingredient



class IngredientAdapter(private val listener: IngredientAdapterListener) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {
    private var data: ArrayList<Ingredient> = ArrayList<Ingredient>()
    fun setData(data: ArrayList<Ingredient>) {
        this.data = data
        Log.d("DATA", "" + data)
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

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = data[position]
        holder.texte.text = ingredient.nom
        holder.itemView.setOnClickListener { listener.onUserClicked(ingredient) }
    }
    override fun getItemCount(): Int {
        return data.size
    }

}