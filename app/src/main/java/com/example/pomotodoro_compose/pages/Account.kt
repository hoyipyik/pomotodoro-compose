package com.example.pomotodoro_compose.pages

import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Account(scope: CoroutineScope, state: ModalBottomSheetState,) {
    Text(text = "account")
    Button(onClick = { scope.launch { state.show() } }) {
        Text("Click to show sheet")
    }
}