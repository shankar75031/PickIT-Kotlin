package com.shankar.pickit.data

class AirportOrder() {
    var orderID: String? = null
    var orderPNR: String? = null
    var orderStatus: Int? = null
    var items: ArrayList<CartItem>? = null
    var orderAmount: Double? = null

    constructor(orderID: String?, orderPNR: String?, orderStatus: Int?, items: ArrayList<CartItem>?): this() {
        this.orderID = orderID
        this.orderPNR = orderPNR
        this.orderStatus = orderStatus
        this.items = items
        var amount = 0.0
        for (item in items!!){
            amount += item.totalPrice!!
        }
        this.orderAmount = amount
    }
}