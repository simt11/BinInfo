package com.example.bininfo

import kotlinx.serialization.Serializable

@Serializable
data class BinCard(
    val number: Number?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: Country?,
    val bank: Bank?
) {
    @Serializable
    class Number(
        val length: Int?,
        val luhn: Boolean?,
    ) {}

    @Serializable
    class Country(
        val numeric: String,
        val alpha2: String,
        val name: String,
        val emoji: String,
        val currency: String,
        val latitude: Int,
        val longitude: Int) {}

    @Serializable
    class Bank(
        val name: String,
        val url: String,
        val phone: String,
        val city: String) {}
}
