package dev.estaki.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    private val id:Long,
    private val title:String,
)
