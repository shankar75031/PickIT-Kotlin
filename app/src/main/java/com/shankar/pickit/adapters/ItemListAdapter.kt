package com.shankar.pickit.adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.shankar.pickit.R
import com.shankar.pickit.activities.ItemDetailsActivity
import com.shankar.pickit.data.Item
import com.shankar.pickit.data.USER_VISITING_AIRPORTS
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_itemlistactivity.view.*


class ItemListAdapter(firebaseRecyclerOptions: FirebaseRecyclerOptions<Item>, var context: Context) :
        FirebaseRecyclerAdapter<Item, ItemListAdapter.ViewHolder>(firebaseRecyclerOptions) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_itemlistactivity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int, item: Item) {
        viewHolder.bindView(item)
        viewHolder.itemView.setOnClickListener {
            var itemID = getRef(position).key
            var itemIntent = Intent(context, ItemDetailsActivity::class.java)
//            itemIntent.putExtra("airportsList", USER_VISITING_AIRPORTS)
            itemIntent.putExtra("itemName", viewHolder.itemName)
            itemIntent.putExtra("itemImage", viewHolder.itemImageURL)
            itemIntent.putExtra("itemThumb", viewHolder.itemThumbURL)
            itemIntent.putExtra("itemRate", viewHolder.itemRate)
            itemIntent.putExtra("itemDescription", viewHolder.itemDesc)
            itemIntent.putExtra("selectedItemID", itemID)
            context.startActivity(itemIntent)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName : String? = null
        var itemImageURL : String? = null
        var itemThumbURL : String? = null
        var itemRate: Double? = null
        var itemDesc: String? = null
        fun bindView(item: Item) {
            itemName = item.itemName
            itemImageURL = item.itemImage
            itemThumbURL = item.itemThumb
            itemRate = item.itemPrice
            itemDesc = item.itemDescription
            itemView.itemListItemName.text = item.itemName
            itemView.itemListItemPrice.text = item.itemPrice.toString()
            Picasso.with(context)
                    .load(item.itemThumb)
                    .placeholder(R.drawable.app_logo)
                    .into(itemView.itemListItemImage)
        }
    }


}