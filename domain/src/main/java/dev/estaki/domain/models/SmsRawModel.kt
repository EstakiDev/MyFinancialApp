package dev.estaki.domain.models

data class SmsRawModel(
    val _id:String,
    val senderName:String,
    val description:String,
    var receiveDate:String,
    var receiveDateTime:String,
)
