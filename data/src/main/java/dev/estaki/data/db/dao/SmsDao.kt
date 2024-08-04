package dev.estaki.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.estaki.data.entities.SmsEntity

@Dao
interface SmsDao {

    @Insert
    fun insertAll(smsList :List<SmsEntity>)

    @Query("SELECT * FROM tb_sms")
    fun readAll():List<SmsEntity>

}