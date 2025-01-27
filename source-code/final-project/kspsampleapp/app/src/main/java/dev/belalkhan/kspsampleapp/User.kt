package dev.belalkhan.kspsampleapp

import dev.belalkhan.kspcore.GenerateToJson

@GenerateToJson
data class User(
    val id: Int,
    val name: String,
    val active: Boolean,
    val book: Book,
    val friends: List<String>, // List of strings
    val attributes: Map<String, String>, // Map of key-value pairs
    val tags: Set<String>, // Set of strings
    val address: Address, // Nested object
    val phoneNumbers: List<PhoneNumber> // List of nested objects
)

@GenerateToJson
data class Book(
    val id: Int,
    val name: String,
    val price: Double, // Adding another primitive type
    val authors: List<String> // List of strings for authors
)

@GenerateToJson
data class Address(
    val street: String,
    val city: String,
    val postalCode: Int
)

@GenerateToJson
data class PhoneNumber(
    val type: String,
    val number: String
)