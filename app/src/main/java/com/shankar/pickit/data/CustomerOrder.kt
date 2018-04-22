package com.shankar.pickit.data

class CustomerOrder(){
    var orderID: String? = null
    var orderAirport: String? = null
    var orderStatus: Int? = null
    var items: ArrayList<CartItem>? = null
    var orderAmount: Double? = null


    constructor(orderID: String?, orderAirport: String?, orderStatus: Int?, items: ArrayList<CartItem>?): this() {
        this.orderID = orderID
        this.orderAirport = orderAirport
        this.orderStatus = orderStatus
        this.items = items
        var amount = 0.0
        for (item in this.items!!){
            amount += item.totalPrice!!
        }
        this.orderAmount = amount
    }
}