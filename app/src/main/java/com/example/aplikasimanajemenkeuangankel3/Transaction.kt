package com.example.aplikasimanajemenkeuangankel3

data class Transaction(
    val ptName: String,
    val price: Double,
    val itemName: String,
    val type: String // "Income" or "Expense"
)