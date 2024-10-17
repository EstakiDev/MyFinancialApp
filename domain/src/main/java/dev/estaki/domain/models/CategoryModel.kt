package dev.estaki.domain.models

data class CategoryModel(
    val id:Long,
    val title:String,
    var isChecked:Boolean = false
)
