package dev.estaki.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.estaki.data.entities.CategoryEntity

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(categoryEntity: List<CategoryEntity>):List<Long>

    @Query("SELECT COUNT(*) FROM tb_category")
    fun getCategoryCount():Long
}