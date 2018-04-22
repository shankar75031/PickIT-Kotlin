package com.shankar.pickit.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.shankar.pickit.R
import com.shankar.pickit.data.CartItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_cart.view.*


class CartAdapter(firebaseRecyclerOptions: FirebaseRecyclerOptions<CartItem>, var context: Context) :
        FirebaseRecyclerAdapter<CartItem, CartAdapter.ViewHolder>(firebaseRecyclerOptions) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_cart, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int, cartItem: CartItem) {
        viewHolder.bindView(cartItem)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: String? = null
        var itemThumbURL: String? = null
        var itemRate: Double? = null
        var itemTotal: Double? = null
        var itemQuantity: Int? = null
        fun bindView(cartItem: CartItem) {
            itemName = cartItem.itemName
            itemThumbURL = cartItem.itemImage
            itemRate = cartItem.itemRate
            itemTotal = cartItem.totalPrice
            itemQuantity = cartItem.itemQuantity

            itemView.cartItemName.text = itemName
            itemView.cartItemRate.text = itemRate.toString()
            itemView.cartItemQuantity.text = itemQuantity.toString()
            itemView.cartItemTotal.text = itemTotal.toString()
            Picasso.with(context)
                    .load(itemThumbURL)
                    .placeholder(R.drawable.app_logo)
                    .into(itemView.cartItemImage)
        }
    }


}