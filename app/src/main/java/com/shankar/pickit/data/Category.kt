package com.shankar.pickit.data

class Category() {
    var categoryName: String? = null
    var categoryImage: String? = null

    constructor(categoryName: String?, categoryImage: String?): this() {
        this.categoryName = categoryName
        this.categoryImage = categoryImage
    }
}