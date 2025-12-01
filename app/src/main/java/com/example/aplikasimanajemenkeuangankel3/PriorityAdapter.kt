package com.example.aplikasimanajemenkeuangankel3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class PriorityAdapter(private val itemList: List<PriorityItem>) :
    RecyclerView.Adapter<PriorityAdapter.PriorityViewHolder>() {


    class PriorityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
        val itemDescription: TextView = itemView.findViewById(R.id.item_description)
        val optionsMenu: ImageView = itemView.findViewById(R.id.item_options_menu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriorityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.priority_item_layout, parent, false)
        return PriorityViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: PriorityViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.itemName.text = currentItem.name
        holder.itemPrice.text = "Rp ${"%, .0f".format(currentItem.price)}"
        holder.itemDescription.text = currentItem.description

        holder.optionsMenu.setOnClickListener {
            //popup menu nantiii...ok
        }
    }



}