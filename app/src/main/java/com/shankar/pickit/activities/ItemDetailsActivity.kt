package com.shankar.pickit.activities

import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.shankar.pickit.R
import com.shankar.pickit.data.CartItem
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_item_details.*
import kotlinx.android.synthetic.main.content_item_details.*

class ItemDetailsActivity : AppCompatActivity() {

    var mFirebaseDatabase: DatabaseReference? = null
    var mFirebaseUser: FirebaseUser? = null
    var itemName: String? = null
    var itemDesc: String? = null
    var itemImage: String? = null
    var itemRate: Double? = null
    var itemThumb: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        setSupportActionBar(toolbar)

        if (intent.extras != null) {
            itemName = intent.extras.get("itemName").toString()
            itemDesc = intent.extras.get("itemDescription").toString()
            itemImage = intent.extras.get("itemImage").toString()
            itemRate = intent.extras.getDouble("itemRate")
            itemThumb = intent.extras.get("itemThumb").toString()

            itemDetailsItemDetails.text = itemDesc
            supportActionBar!!.title = itemName
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        Picasso.with(this)
                .load(itemImage)
                .placeholder(R.drawable.nav_header)
                .into(object : Target {
                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                    }

                    override fun onBitmapFailed(errorDrawable: Drawable?) {
                    }

                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        toolbar_layout.background = BitmapDrawable(applicationContext.resources, bitmap)
                    }

                })
        fab.setOnClickListener { view ->
            if (FirebaseAuth.getInstance().currentUser != null){
                mFirebaseUser = FirebaseAuth.getInstance().currentUser
                if (mFirebaseUser == null) {
                    Toast.makeText(this, "You cannot add to cart without logging in!", Toast.LENGTH_SHORT).show()
                } else {
                    mFirebaseDatabase = FirebaseDatabase.getInstance().reference.child(mFirebaseUser!!.uid)
                    showAddToCartDialog()
                }
            }
            else{
                Toast.makeText(this, "You cannot purchase without logging in!", Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun showAddToCartDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_to_cart, null)
        dialogBuilder.setView(dialogView)

        val quantityTextView = dialogView.findViewById<View>(R.id.dialogAddToCart_quantityTextView) as TextView
        val increaseButton = dialogView.findViewById<View>(R.id.dialogAddToCart_increaseQty) as Button
        val decreaseButton = dialogView.findViewById<View>(R.id.dialogAddToCart_decreaseQty) as Button

        increaseButton.setOnClickListener {
            var x = quantityTextView.text.toString().toInt()
            if (x >= 10) {
                Toast.makeText(this, "You cannot add more than 10 items!", Toast.LENGTH_SHORT).show()
            } else {
                x++
                quantityTextView.text = x.toString()
            }
        }
        decreaseButton.setOnClickListener {
            var x = quantityTextView.text.toString().toInt()
            if (x <= 1) {
                Toast.makeText(this, "Select minimum of 1 item for purchase!", Toast.LENGTH_SHORT).show()
            } else {
                x--
                quantityTextView.text = x.toString()
            }
        }
        dialogBuilder.setTitle("Confirm")
        dialogBuilder.setMessage("Select quantity for purchase.")
        dialogBuilder.setPositiveButton("Save", DialogInterface.OnClickListener { dialog, whichButton ->
            var quantity = quantityTextView.text.toString().toInt()
            var cartItem = CartItem(itemName, itemThumb, itemRate, quantity)
            mFirebaseDatabase!!.child("Cart").push().setValue(cartItem)
            Toast.makeText(this, "Added to cart", Toast.LENGTH_LONG).show()
        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, whichButton ->
            dialog.dismiss()
        })
        val b = dialogBuilder.create()
        b.show()
    }
}
