package com.example.aplikasimanajemenkeuangankel3

data class PriorityItem(
    val id: Long,
    var imageUrl: Int,
    var name: String,
    var price: Double,
    var description: String,
    var priorityPercentage: Int
)
    