package dev.estaki.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.estaki.data.entities.SmsEntity
import dev.estaki.domain.models.SmsModel

@Dao
interface SmsDao {

    @Insert
    fun insertAll(smsList :List<SmsEntity>):List<Long>

    @Query("SELECT * FROM tb_sms")
    fun readAll():List<SmsEntity>


    @Query("SELECT * FROM tb_sms WHERE id=:id")
    fun read(id: Long): SmsEntity

}