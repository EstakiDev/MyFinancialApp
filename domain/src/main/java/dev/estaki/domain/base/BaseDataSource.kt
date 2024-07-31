package dev.estaki.domain.base

import android.content.ClipData.Item

interface BaseDataSource<Entity,AffectedRow,Id> {
    fun readAll():List<Entity>
    fun read(id:Id):Entity
    fun delete(id: Id):AffectedRow
    fun deleteAll():AffectedRow
    fun update(id: Id):AffectedRow
    fun add(id: Entity)
    fun addAll(item: List<Entity>)
}