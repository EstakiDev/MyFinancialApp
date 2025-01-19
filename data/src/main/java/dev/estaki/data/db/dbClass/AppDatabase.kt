package dev.estaki.data.db.dbClass

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.estaki.data.db.dao.CategoryDao
import dev.estaki.data.db.dao.SmsDao
import dev.estaki.data.entities.CategoryEntity
import dev.estaki.data.entities.SmsEntity

@Database(
    entities = [
        SmsEntity::class,
        CategoryEntity::class
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun smsDao(): SmsDao
    abstract fun categoryDao(): CategoryDao
}

val MIGRATION_1_2 = object : Migration(1,2) {
    override fun migrate(db: SupportSQLiteDatabase) {

    }
}