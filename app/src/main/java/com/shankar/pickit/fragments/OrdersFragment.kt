package com.shankar.pickit.fragments


import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shankar.pickit.R
import com.shankar.pickit.adapters.OrdersAdapter
import com.shankar.pickit.data.CustomerOrder
import kotlinx.android.synthetic.main.fragment_orders.view.*


class OrdersFragment : Fragment() {

    var userID: String? = null
    var databaseReference: DatabaseReference? = null
    var ordersAdapter: OrdersAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        userID = FirebaseAuth.getInstance().currentUser!!.uid
        databaseReference = FirebaseDatabase.getInstance().reference.child(userID).child("Orders")
        view!!.ordersRecyclerView.setHasFixedSize(true)
        view!!.ordersRecyclerView.layoutManager = layoutManager
        var firebaseRecyclerOptions = FirebaseRecyclerOptions.Builder<CustomerOrder>()
                .setQuery(databaseReference!!, CustomerOrder::class.java)
                .build()
        ordersAdapter = OrdersAdapter(firebaseRecyclerOptions, context!!)
        view!!.ordersRecyclerView.adapter = ordersAdapter
    }
    override fun onStart() {
        super.onStart()
        ordersAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        ordersAdapter!!.stopListening()
    }
}

