package dev.estaki.myFinancialApp.presentation.states

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable

data class MyTopAppBarState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val title: String = "",
    val navigationIcon: @Composable () -> Unit = {},
    val actions: @Composable RowScope.() -> Unit = {},
    val scrollBehavior: TopAppBarScrollBehavior? = null
)
