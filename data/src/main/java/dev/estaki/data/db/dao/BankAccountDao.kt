package dev.estaki.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import dev.estaki.data.entities.BankAccountEntity
import dev.estaki.domain.models.BankCardModel

@Dao
interface BankAccountDao {

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    fun insertAll(smsList :List<BankAccountEntity>):List<Long>

    @Query("SELECT * FROM tb_bank_account ORDER BY id DESC")
    fun readAll():List<BankAccountEntity>

    @Query("SELECT * FROM tb_bank_account WHERE id=:id")
    fun read(id: Long): BankAccountEntity

    @Query("SELECT * FROM tb_bank_account WHERE bankAccountNumber=:bankAccountNumber")
    fun readByBankAccountNumber(bankAccountNumber: String): BankAccountEntity

    @Query("SELECT * \n" +
            "  FROM tb_bank_account \n" +
            " GROUP BY bankAccountNumber;")
    fun readAllBankAccountNumber(): List<BankCardModel>

    @Upsert
    fun upsert(bankAccountEntity: BankAccountEntity)

    @Delete
    fun delete(bankAccountEntity: BankAccountEntity):Int

    @Query("DELETE FROM tb_bank_account WHERE id = :id")
    fun deleteById(id: Long):Int

    @Query("DELETE FROM tb_bank_account")
    fun deleteAll():Int

}