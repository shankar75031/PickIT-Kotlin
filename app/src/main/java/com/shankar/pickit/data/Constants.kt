package com.shankar.pickit.data

val USER_PNR: String = "123456"
val USER_PNR2: String = "ABEM4Z"
val USER_FIRST_NAME: String = "Adams"
val USER_LAST_NAME: String = "Anton"
val USER_PHONE: String = "313712387"
val USER_EMAIL: String = "antonadams@hetnet.nl"
val USER_VISITING_AIRPORTS: ArrayList<String> = arrayListOf("COK", "AUH", "MAD")
val USER_VISITING_AIRPORTS_NAME: ArrayList<String> = arrayListOf("Cochin Airport", "Abu Dhabi International Airport", "Barajas International Airport, Madrid")

/*
 *Order Statuses:
 * 0 -> Pending
 * 1 -> Processing
 * 2 -> Dispatched : Delivery by PickIT
 * 3 -> Dispatched : Delivery by Airlines\
 * 4 -> Delivered
 * 5 -> Cancelled
 */

val ORDER_STATUS: ArrayList<String> = arrayListOf("Pending",
        "Processing",
        "Order will be delivered at your gate.",
        "Order will be delivered at your seat.",
        "Delivered",
        "Cancelled")