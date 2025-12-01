package com.example.aplikasimanajemenkeuangankel3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(private val transactions: MutableList<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ptName: TextView = itemView.findViewById(R.id.tv_pt_name)
        val itemName: TextView = itemView.findViewById(R.id.tv_item_name)
        val price: TextView = itemView.findViewById(R.id.tv_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.ptName.text = transaction.ptName
        holder.itemName.text = transaction.itemName
        holder.price.text = "Rp. ${transaction.price}"
    }

    override fun getItemCount() = transactions.size

    fun addTransaction(transaction: Transaction) {
        transactions.add(0, transaction) // Add to the top of the list
        notifyItemInserted(0)
    }
}