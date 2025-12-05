package com.example.aplikasimanajemenkeuangankel3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class PriorityAdapter(
    private var itemList: MutableList<PriorityItem>,
    private val onItemDeleted: (PriorityItem, Int) -> Unit,
    private val onItemEdited: (PriorityItem) -> Unit,
    private val onSetPriority: (PriorityItem) -> Unit
) :
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

        holder.itemImage.setImageResource(currentItem.imageUrl)
        holder.itemName.text = currentItem.name
        holder.itemPrice.text = "Rp ${"%, .0f".format(currentItem.price)}"
        holder.itemDescription.text = currentItem.description

        holder.optionsMenu.setOnClickListener { view ->
            val popup = PopupMenu(view.context, view)
            popup.inflate(R.menu.priority_item_options_menu)

            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.option_edit -> {
                        onItemEdited(currentItem)
                        true
                    }
                    R.id.option_delete -> {
                        onItemDeleted(currentItem, position)
                        true
                    }
                    R.id.option_set_priority -> {
                        onSetPriority(currentItem)
                        true
                    }
                    else -> false
                    }
                }
            popup.show()
            }
        }
    fun updateData(newList: List<PriorityItem>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }
    }