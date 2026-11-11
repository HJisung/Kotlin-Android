package com.androidbook.bookmarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.Holder>() {
    class Holder(view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(
        LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_order_item, parent, false)
    )
    override fun getItemCount() = Cart.books.size
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val book = Cart.books[position]
        val values = listOf(book.name, book.id, book.price.toString(), book.quantity.toString(), (book.price * book.quantity).toString())
        val ids = listOf(R.id.title, R.id.id, R.id.price, R.id.quantity, R.id.sum)
        ids.zip(values).forEach { (id, value) -> holder.itemView.findViewById<TextView>(id).text = value }
    }
}
