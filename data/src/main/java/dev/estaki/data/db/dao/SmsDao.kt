package dev.estaki.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import dev.estaki.data.entities.SmsEntity
import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.models.SmsModel

@Dao
interface SmsDao {

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insertAll(smsList :List<SmsEntity>):List<Long>

    @Query("SELECT * FROM tb_sms ORDER BY id DESC")
    fun readAll():List<SmsEntity>

    @Query("SELECT * FROM tb_sms WHERE id=:id")
    fun read(id: Long): SmsEntity

    @Query("SELECT * FROM tb_sms WHERE bankAccountNumber=:bankAccountNumber ORDER BY transactionDateTime DESC")
    fun readByBankAccountNumber(bankAccountNumber: String): List<SmsEntity>

    @Query("SELECT id,\n" +
            "       bankAccountNumber,\n" +
            "       bankName,\n" +
            "       bankCardBalance,\n" +
            "       max(transactionDateTime)\n" +
            "  FROM tb_sms\n" +
            " GROUP BY bankAccountNumber;")
    fun readAllBankAccountNumber(): List<BankCardModel>

    @Upsert
    fun upsert(sms: SmsEntity)

}