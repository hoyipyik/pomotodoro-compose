package com.example.pomotodoro_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pomotodoro_compose.data.viewModel.TestViewModel
import com.example.pomotodoro_compose.ui.theme.PomotodorocomposeTheme

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