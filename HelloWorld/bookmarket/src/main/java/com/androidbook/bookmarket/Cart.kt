package com.androidbook.bookmarket

data class CartBook(val id: String, val name: String, val price: Int, var quantity: Int = 1)

object Cart {
    val books = mutableListOf<CartBook>()
    fun add(id: String, name: String, price: Int) {
        val existing = books.find { it.id == id }
        if (existing == null) books.add(CartBook(id, name, price)) else existing.quantity++
    }
    fun total() = books.sumOf { it.price * it.quantity }
}
