package com.shankar.pickit.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.shankar.pickit.R
import com.shankar.pickit.activities.ItemListActivity
import com.shankar.pickit.data.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_shop.view.*

class CategoryAdapter(firebaseRecyclerOptions: FirebaseRecyclerOptions<Category>, var context: Context) :
        FirebaseRecyclerAdapter<Category, CategoryAdapter.ViewHolder>(firebaseRecyclerOptions) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_shop, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int, category: Category) {
        viewHolder.bindView(category)
        viewHolder.itemView.setOnClickListener {
            var itemIntent = Intent(context, ItemListActivity::class.java)
            itemIntent.putExtra("category", viewHolder.categoryName)
            context.startActivity(itemIntent)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryName: String? = null
        fun bindView(category: Category) {
            categoryName = category.categoryName
            itemView.storeListCategoryName.text = category.categoryName
            Picasso.with(context)
                    .load(category.categoryImage)
                    .placeholder(R.drawable.nav_header)
                    .into(itemView.storeListCategoryImage)
        }
    }


}