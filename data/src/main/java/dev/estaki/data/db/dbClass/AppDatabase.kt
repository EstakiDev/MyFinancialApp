package dev.estaki.data.db.dbClass

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.estaki.data.db.dao.SmsDao
import dev.estaki.data.entities.SmsEntity

@Database(entities = [SmsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun smsDao(): SmsDao
}