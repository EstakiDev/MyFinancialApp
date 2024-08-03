package dev.estaki.domain.base

import android.content.ClipData.Item

interface BaseRepository<Entity,AffectedRow,Id> {
    suspend fun readAll():List<Entity>
    suspend fun read(id:Id):Entity
    suspend fun delete(id: Id):AffectedRow
    suspend fun deleteAll():AffectedRow
    suspend fun update(id: Id):AffectedRow
    suspend fun add(id: Entity)
    suspend fun addAll(item: List<Entity>)
}