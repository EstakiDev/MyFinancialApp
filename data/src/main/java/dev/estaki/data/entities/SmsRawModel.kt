package dev.estaki.data.entities

data class SmsRawModel(
    val _id:String,
    val senderName:String,
    val description:String,
    var receiveDate:String,
)
