package com.shankar.pickit.data

import android.media.Rating

class Item() {
    var itemName: String? = null
    var itemDescription: String? = null
    var itemImage: String? = null
    var itemThumb: String? = null
    var itemPrice: Double? = null
    var itemRating: Float? = null

    constructor(itemName: String?, itemDescription: String?, itemImage: String?, itemPrice: Double?, itemRating: Float?, itemThumb: String?): this() {
        this.itemName = itemName
        this.itemDescription = itemDescription
        this.itemImage = itemImage
        this.itemPrice = itemPrice
        this.itemRating = itemRating
        this.itemThumb = itemThumb
    }
}