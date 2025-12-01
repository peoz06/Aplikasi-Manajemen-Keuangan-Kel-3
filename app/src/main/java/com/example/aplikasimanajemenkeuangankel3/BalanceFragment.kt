package com.example.aplikasimanajemenkeuangankel3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.NumberFormat
import java.util.Locale

class BalanceFragment : Fragment() {

    private lateinit var transactionAdapter: TransactionAdapter
    private val transactions = mutableListOf<Transaction>()

    private lateinit var tvIncome: TextView
    private lateinit var tvExpenses: TextView
    private lateinit var tvDifference: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_balance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvIncome = view.findViewById(R.id.tv_income_value)
        tvExpenses = view.findViewById(R.id.tv_expenses_value)
        tvDifference = view.findViewById(R.id.tv_difference_value)

        // Setup RecyclerView
        val rvTransactions: RecyclerView = view.findViewById(R.id.rv_transactions)
        transactionAdapter = TransactionAdapter(transactions)
        rvTransactions.adapter = transactionAdapter
        rvTransactions.layoutManager = LinearLayoutManager(requireContext())


        val fab: FloatingActionButton = view.findViewById(R.id.fab_add)
        fab.setOnClickListener {
            showAddTransactionDialog()
        }
        updateSummary()
    }

    private fun showAddTransactionDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_transaction, null)
        val ptName: EditText = dialogView.findViewById(R.id.et_pt_name)
        val price: EditText = dialogView.findViewById(R.id.et_price)
        val itemName: EditText = dialogView.findViewById(R.id.et_item_name)
        val transactionTypeSpinner: Spinner = dialogView.findViewById(R.id.spinner_transaction_type)
        val btnSave: Button = dialogView.findViewById(R.id.btn_save)

        // Setup Spinner
        val transactionTypes = arrayOf("Income", "Expense")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, transactionTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        transactionTypeSpinner.adapter = adapter

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)

        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        btnSave.setOnClickListener {
            val name = ptName.text.toString()
            val amountStr = price.text.toString()
            val item = itemName.text.toString()
            val type = transactionTypeSpinner.selectedItem.toString()

            if (name.isNotEmpty() && amountStr.isNotEmpty() && item.isNotEmpty()) {
                val amount = amountStr.toDoubleOrNull()
                if (amount != null) {
                    val newTransaction = Transaction(
                        ptName = name,
                        price = amount,
                        itemName = item,
                        type = type
                    )
                    transactionAdapter.addTransaction(newTransaction)
                    updateSummary()

                    Toast.makeText(context, "Saved $type: $name - $item", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                } else {
                    Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateSummary() {
        val totalIncome = transactions.filter { it.type == "Income" }.sumOf { it.price }
        val totalExpenses = transactions.filter { it.type == "Expense" }.sumOf { it.price }
        val difference = totalIncome - totalExpenses

        val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

        tvIncome.text = "Rp\n${formatter.format(totalIncome)}"
        tvExpenses.text = "Rp\n${formatter.format(totalExpenses)}"
        tvDifference.text = "Rp\n${formatter.format(difference)}"
    }
}