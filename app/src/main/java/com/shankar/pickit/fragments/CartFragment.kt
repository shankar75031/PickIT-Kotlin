package com.shankar.pickit.fragments


import android.app.AlertDialog
import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.shankar.pickit.R
import com.shankar.pickit.adapters.CartAdapter
import com.shankar.pickit.data.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.view.*
import java.util.*
import kotlin.collections.ArrayList


class CartFragment : Fragment() {
    var databaseReference: DatabaseReference? = null
    var cartAdapter: CartAdapter? = null
    var itemsList: ArrayList<CartItem>? = null
    var userID: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        userID = FirebaseAuth.getInstance().currentUser!!.uid
        databaseReference = FirebaseDatabase.getInstance().reference.child(userID).child("Cart")
        view!!.cartRecyclerView.setHasFixedSize(true)
        view!!.cartRecyclerView.layoutManager = layoutManager
        var firebaseRecyclerOptions = FirebaseRecyclerOptions.Builder<CartItem>()
                .setQuery(databaseReference!!, CartItem::class.java)
                .build()
        cartAdapter = CartAdapter(firebaseRecyclerOptions, context!!)
        view!!.cartRecyclerView.adapter = cartAdapter

        cartFab.setOnClickListener { view ->
            itemsList = ArrayList()
            databaseReference!!.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    for (snapshot in dataSnapshot!!.children) {
                        if(snapshot.exists()){
                            val item = snapshot.getValue(CartItem::class.java)
                            itemsList!!.add(item!!)
                        }
                    }
                    if (dataSnapshot.value != null){
                        checkList()
                    }
                }

            })
        }
    }

    private fun checkList() {
        if (itemsList!!.isNotEmpty()) {
            alternateOffers()
        }
    }

    private fun alternateOffers() {
        var toOffer = randomNumber(0,1)
        if (toOffer == 1){
            var decreasedRate = randomNumber(1,18)

            //Building Dialog Box
            val alert = AlertDialog.Builder(activity)
            with(alert){
                setTitle("Items can be cheaper")
                var airportIndex = randomNumber(1,2)
                setMessage("You are about to order items from ${USER_VISITING_AIRPORTS_NAME[0]}. Items currently in your cart are $decreasedRate % cheaper if you wish purchase from ${USER_VISITING_AIRPORTS_NAME[airportIndex]}. Do you wish to purchase from there? ")
                setNegativeButton("NO"){
                    dialog, negativeButton ->
                    placeOrder()
                    dialog.dismiss()
                }
                setPositiveButton("YES"){
                    dialog, positiveButton ->
                    modifyList(decreasedRate)
                    dialog.dismiss()
                }
            }
            val dialog = alert.create()
            dialog.show()

        }
        else{
            val alert = AlertDialog.Builder(activity)
            with(alert){
                setTitle("Confirm Order")
                setMessage("Confirm your order from ${USER_VISITING_AIRPORTS_NAME[0]}.")
                setPositiveButton("CONFIRM"){
                    dialog, positiveButton ->
                    placeOrder()
                    dialog.dismiss()
                }
                setNegativeButton("CANCEL"){
                    dialog, negativeButton ->
                    dialog.dismiss()
                }
            }
                val dialog = alert.create()
            dialog.show()
        }
    }

    private fun modifyList(decreasedRate: Int) {
        var i = 0
        while (i < itemsList!!.size){
            //Decreasing item rate by said percentage
            var rate = itemsList!![i].itemRate
            rate = rate!! - (rate * decreasedRate/100.0)
            itemsList!![i].itemRate = rate
            //Decreasing item total price by said percentage
            var price = itemsList!![i].totalPrice
            price = price!! - (price * decreasedRate/100.0)
            itemsList!![i].totalPrice = price
            i++
        }
        placeOrder()
    }

    fun placeOrder(){

        val pickItId = randomNumber(1000, 9999).toString()
        val orderAirport = USER_VISITING_AIRPORTS[0]
        val orderStatus = 0
        val myOrder = CustomerOrder(pickItId,orderAirport,orderStatus,itemsList)
        val airportOrder = AirportOrder(pickItId, USER_PNR, orderStatus, itemsList)
        val userDatabaseRef = FirebaseDatabase.getInstance().reference.child(userID)
        userDatabaseRef.child("Orders").child(pickItId).setValue(myOrder)
        val airportDatabaseReference = FirebaseDatabase.getInstance().reference.child("Orders")
        airportDatabaseReference.child(orderAirport).child(pickItId).setValue(airportOrder)
        Toast.makeText(activity, "Order Placed", Toast.LENGTH_SHORT).show()
        databaseReference!!.removeValue()   //Deletes all items in cart after placing order
    }

    //Generates a random number between start and end including the end
    fun randomNumber(start: Int, end: Int) = Random().nextInt(end + 1 - start) + start


    override fun onStart() {
        super.onStart()
        cartAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        cartAdapter!!.stopListening()
    }
}



