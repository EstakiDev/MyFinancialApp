package dev.estaki.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.estaki.domain.models.TransactionType


@Entity(tableName = "tb_sms")
data class SmsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val bankName:String,
    val bankAccountNumber:String,
    val transactionType: TransactionType,
    val transactionAmount:String,
    val transactionDate:String,
    val transactionTime:String,
    val bankCardBalance:String,
    val categoryId:Long,
    val description:String?,
)