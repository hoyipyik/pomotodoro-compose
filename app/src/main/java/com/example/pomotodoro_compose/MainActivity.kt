package com.example.pomotodoro_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.pomotodoro_compose.ui.theme.PomotodorocomposeTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                PomotodorocomposeTheme {
                App()
//                val testViewModel: TestViewModel = viewModel()
//                Demo()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PomotodorocomposeTheme {
        App()
    }
}