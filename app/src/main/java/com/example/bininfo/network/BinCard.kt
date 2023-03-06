package com.example.bininfo.network

import kotlinx.serialization.Serializable

@Serializable
data class BinCard(
    val number: Number? = null,
    val scheme: String? = null,
    val type: String? = null,
    val brand: String? = null,
    val prepaid: Boolean? = null,
    val country: Country? = null,
    val bank: Bank? = null,
) {
    @Serializable
    class Number(
        val length: Int? = null,
        val luhn: Boolean? = null,
    )

    @Serializable
    class Country(
        val numeric: String? = null,
        val alpha2: String? = null,
        val name: String? = null,
        val emoji: String? = null,
        val currency: String? = null,
        val latitude: Double? = null,
        val longitude: Double? = null,
    )

    @Serializable
    class Bank(
        val name: String? = null,
        val url: String? = null,
        val phone: String? = null,
        val city: String? = null,
    )
}
