package com.shankar.pickit.data

class CartItem() {
    var itemName: String? = null
    var itemImage: String? = null
    var itemRate: Double? = null
    var itemQuantity: Int? = null
    var totalPrice: Double? = null

    constructor(itemName: String?, itemImage: String?, itemRate: Double?, itemQuantity: Int?): this(){
        this.itemName = itemName
        this.itemImage = itemImage
        this.itemRate = itemRate
        this.itemQuantity = itemQuantity
        this.totalPrice = itemRate!! * itemQuantity!!
    }
}