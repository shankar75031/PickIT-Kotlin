package com.shankar.pickit.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shankar.pickit.R
import com.shankar.pickit.adapters.ItemListAdapter
import com.shankar.pickit.data.Item
import kotlinx.android.synthetic.main.activity_item_list.*

class ItemListActivity : AppCompatActivity() {
    var databaseReference: DatabaseReference? = null
    var itemListAdapter: ItemListAdapter? = null
    var category: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        if (intent.extras != null) {
            category = intent.extras.get("category").toString()
        }

        supportActionBar!!.title = category

        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        databaseReference = FirebaseDatabase.getInstance().reference.child("COK").child(category)
        itemsRecyclerView.setHasFixedSize(true)
        itemsRecyclerView.layoutManager = layoutManager
        var firebaseRecyclerOptions = FirebaseRecyclerOptions.Builder<Item>()
                .setQuery(databaseReference!!, Item::class.java)
                .build()
        itemListAdapter = ItemListAdapter(firebaseRecyclerOptions, this)
        itemsRecyclerView.adapter = itemListAdapter
    }
    override fun onStart() {
        super.onStart()
        itemListAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        itemListAdapter!!.stopListening()
    }
}
