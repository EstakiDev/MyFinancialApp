package dev.estaki.domain.base

import android.content.ClipData.Item
import kotlinx.coroutines.flow.Flow

interface BaseDataSource<Entity,AffectedRow,Id> {
    suspend fun readAll():Flow<List<Entity>>
    suspend fun read(id:Id):Flow<Entity>
    suspend fun delete(id: Id):Flow<AffectedRow>
    suspend fun deleteAll():Flow<AffectedRow>
    suspend fun update(id: Id):Flow<AffectedRow>
    suspend fun add(id: Entity)
    suspend fun addAll(item: List<Entity>):Flow<List<Id>>
    suspend fun getAllCount():Flow<Long>
}