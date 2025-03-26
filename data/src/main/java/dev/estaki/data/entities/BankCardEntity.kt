package dev.estaki.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tb_bank_account")
data class BankAccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val bankName: String,
    val bankAccountNumber: String,
    val bankCardNumber: String?,
    val bankCardBalance: String,
    val isItFromSms: Boolean?,
)
