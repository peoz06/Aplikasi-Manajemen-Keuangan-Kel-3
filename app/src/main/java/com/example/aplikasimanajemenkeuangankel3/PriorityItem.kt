package com.example.aplikasimanajemenkeuangankel3

data class PriorityItem(
    val id: Long,
    val name: String,
    val price: Double,
    val description: String,
    var priorityPercentage: Int
)
    