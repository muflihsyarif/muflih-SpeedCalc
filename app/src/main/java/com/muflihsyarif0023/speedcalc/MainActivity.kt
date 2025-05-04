package com.muflihsyarif0023.speedcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.muflihsyarif0023.speedcalc.ui.screen.MainScreen
import com.muflihsyarif0023.speedcalc.ui.theme.SpeedCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpeedCalcTheme {
               MainScreen()
            }
        }
    }
}



