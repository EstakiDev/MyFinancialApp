package dev.estaki.domain.base

import kotlinx.coroutines.flow.Flow

interface BaseRepository<Entity,AffectedRow,Id> {
    suspend fun readAll():Flow<List<Entity>>
    suspend fun read(id:Id):Flow<Entity>
    suspend fun delete(id: Id):AffectedRow
    suspend fun deleteAll():AffectedRow
    suspend fun upsert(entity: Entity)
    suspend fun add(entity: Entity)
    suspend fun addAll(item: List<Entity>):Flow<List<Id>>
    suspend fun getAllCount():Flow<Long>
}