package com.shankar.pickit.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shankar.pickit.R
import com.shankar.pickit.adapters.CartAdapter
import com.shankar.pickit.data.CartItem
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.fragment_cart.view.*
import android.R.attr.bitmap
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import net.glxn.qrgen.android.QRCode


class OrderDetailsActivity : AppCompatActivity() {

    var orderID : String? = null
    var orderStatus : Int? = null
    var databaseReference: DatabaseReference? = null
    var cartAdapter: CartAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        if (intent.extras != null) {
            orderID = intent.extras.get("orderID").toString()
            orderDetailsPickItCode.text = orderID
            val myBitmap = QRCode.from(orderID).bitmap()
            val myImage = findViewById<View>(R.id.orderDetailsQRCode) as ImageView
            myImage.setImageBitmap(myBitmap)

            orderStatus = intent.getIntExtra("orderStatus", 0)
            if (orderStatus == 4 || orderStatus == 5)
                orderDetailsDeliveryDetails.visibility = View.GONE
        }
        supportActionBar!!.title = "Order Details"
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var userID = FirebaseAuth.getInstance().currentUser!!.uid
        databaseReference = FirebaseDatabase.getInstance().reference.child(userID).child("Orders").child(orderID).child("items")
        orderDetailsRecyclerView.setHasFixedSize(true)
        orderDetailsRecyclerView.layoutManager = layoutManager
        var firebaseRecyclerOptions = FirebaseRecyclerOptions.Builder<CartItem>()
                .setQuery(databaseReference!!, CartItem::class.java)
                .build()
        cartAdapter = CartAdapter(firebaseRecyclerOptions, this!!)
        orderDetailsRecyclerView.adapter = cartAdapter
    }
    override fun onStart() {
        super.onStart()
        cartAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        cartAdapter!!.stopListening()
    }
}
