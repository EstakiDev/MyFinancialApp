package dev.estaki.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.estaki.data.entities.CategoryEntity

@Dao
interface CategoryDao {

    @Insert
    fun insertCategory(categoryEntity: List<CategoryEntity>):List<Long>

    @Query("SELECT COUNT(*) FROM tb_category")
    fun getCategoryCount():Long
}