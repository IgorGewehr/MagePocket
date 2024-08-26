package com.gewehr.magepocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.gewehr.magepocket.composables.AppNavigation
import com.gewehr.magepocket.ui.theme.MagePocketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MagePocketTheme {
                AppNavigation()
            }
        }
    }
}
