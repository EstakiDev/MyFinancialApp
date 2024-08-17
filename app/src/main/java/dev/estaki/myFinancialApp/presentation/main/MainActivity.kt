package dev.estaki.myFinancialApp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.estaki.myFinancialApp.isPermissionsGranted
import dev.estaki.myFinancialApp.presentation.Nav
import dev.estaki.myFinancialApp.ui.theme.FinancialTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
            } else {
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isPermissionsGranted(this))
            requestPermissionLauncher.launch(android.Manifest.permission.READ_SMS)
        else {
            setContent {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    FinancialTheme {
                        val navController = rememberNavController()
                        Nav(navController = navController )
                    }
                }
            }
        }

    }

}

