package com.shankar.pickit.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.shankar.pickit.R
import com.shankar.pickit.activities.OrderDetailsActivity
import com.shankar.pickit.data.CustomerOrder
import com.shankar.pickit.data.ORDER_STATUS
import com.timqi.sectorprogressview.ColorfulRingProgressView
import kotlinx.android.synthetic.main.list_item_cart.view.*
import kotlinx.android.synthetic.main.list_item_orders.view.*


class OrdersAdapter(firebaseRecyclerOptions: FirebaseRecyclerOptions<CustomerOrder>, var context: Context) :
        FirebaseRecyclerAdapter<CustomerOrder, OrdersAdapter.ViewHolder>(firebaseRecyclerOptions) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_orders, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int, order: CustomerOrder) {
        viewHolder.bindView(order)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var orderID: String? = null
        var orderStatusText: String? = null
        var orderStatus: Int? = null
        var orderAmount: Double? = null

        fun bindView(order: CustomerOrder) {
            orderID = order.orderID
            orderStatus = order.orderStatus
            orderStatusText = "Status : " + ORDER_STATUS[orderStatus!!]
            orderAmount = order.orderAmount


            itemView.ordersPickItId.text = orderID
            itemView.ordersAirport.text = order.orderAirport.toString()
            itemView.ordersAmount.text = orderAmount.toString()
            itemView.ordersStatus.text = orderStatusText
            var progressView = itemView.orderCircularProgressView as ColorfulRingProgressView
            var viewOrderDetails = itemView.ordersViewDetails as TextView
            var viewTrackOrder = itemView.ordersTrackOrder as TextView
            progressView.animate()
            when(orderStatus){
                0->{
                    progressView.percent = 0F
                }
                1->{
                    progressView.percent = 33F
                }
                2-> {
                    progressView.percent = 66F
                    // ContextCompat.getColor(context,R.color.md_red_50)
                }
                3-> {
                    progressView.percent = 66F
                }
                4-> {
                    viewTrackOrder.visibility = View.GONE
                    progressView.visibility = View.GONE
                }
                5->{
                    viewTrackOrder.visibility = View.GONE
                    progressView.percent = 100F
                    progressView.visibility = View.GONE

                }
            }
            viewOrderDetails.setOnClickListener {
                var itemIntent = Intent(context, OrderDetailsActivity::class.java)
                itemIntent.putExtra("orderID", orderID)
                itemIntent.putExtra("orderStatus", orderStatus!!)
                context.startActivity(itemIntent)
            }

            //TODO: Implement details view
            itemView.ordersTrackOrder.setOnClickListener {

            }
//            Picasso.with(context)
//                    .load(itemThumbURL)
//                    .placeholder(R.drawable.app_logo)
//                    .into(itemView.cartItemImage)
        }
    }


}