package com.shankar.pickit.fragments


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.shankar.pickit.R
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shankar.pickit.adapters.CategoryAdapter
import com.shankar.pickit.data.Category
import kotlinx.android.synthetic.main.fragment_store.*
import kotlinx.android.synthetic.main.fragment_store.view.*


class StoreFragment : Fragment() {
    var databaseReference: DatabaseReference? = null
    var categoryAdapter: CategoryAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_store, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var layoutManager = GridLayoutManager(context, 2)
        databaseReference = FirebaseDatabase.getInstance().reference.child("Categories")
        view!!.storeRecyclerView.setHasFixedSize(true)
        view!!.storeRecyclerView.layoutManager = layoutManager
        var firebaseRecyclerOptions = FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(databaseReference!!, Category::class.java)
                .build()
        categoryAdapter =  CategoryAdapter(firebaseRecyclerOptions, context!!)
        view!!.storeRecyclerView.adapter = categoryAdapter
    }
    override fun onStart() {
        super.onStart()
        categoryAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        categoryAdapter!!.stopListening()
    }
}
