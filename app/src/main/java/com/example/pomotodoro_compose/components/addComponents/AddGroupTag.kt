package com.example.pomotodoro_compose.components.addComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.pomotodoro_compose.ui.theme.*
import com.example.pomotodoro_compose.data.viewModel.GroupTagViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddGroupTag(
  type: String,
  modifier: Modifier = Modifier,
  groupTagViewModel: GroupTagViewModel,
  bottomSheetState: ModalBottomSheetState,
  scope: CoroutineScope
) {
  var colour by remember { mutableStateOf(Purple500) }
  var text by remember { mutableStateOf("") }
  val focusManager = LocalFocusManager.current
  val focusRequester = FocusRequester()
  // need optimism
//    LaunchedEffect(bottomSheetState.currentValue != ModalBottomSheetValue.Hidden) {
//        // open keyboard
//        focusRequester.requestFocus()
//        Log.i("/debug", "show")
//    }

  LaunchedEffect(bottomSheetState.currentValue == ModalBottomSheetValue.Hidden) {
    focusManager.clearFocus()
    text = ""
    colour = Purple500
//        Log.i("/debug", "hide")
  }

  Column(
    modifier = modifier.fillMaxWidth().padding(top = 15.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
    ) {
      OutlinedTextField(
        modifier = Modifier
          .fillMaxWidth(0.74f)
          .padding(end = 9.dp)
          .height(55.dp)
          .focusRequester(focusRequester),
        value = text,
        placeholder = { Text(text = "Add group tag...") },
        onValueChange = { text = it },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
          capitalization = KeyboardCapitalization.None,
          autoCorrect = true,
          keyboardType = KeyboardType.Text,
          imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
          onDone = {
            focusManager.clearFocus()
          }
        )
      )
      Button(
        onClick = {
          if (text != "") {
            groupTagViewModel.addGroupTag(name = text, colour = colour)
            scope.launch { bottomSheetState.hide() }
            text = ""
            colour = Blue500
          }
        },
        modifier = Modifier
          .width(45.dp)
          .height(45.dp)
      ) {
        Icon(Icons.Filled.Add, contentDescription = null)
      }
    }
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
      modifier = Modifier.padding(top = 1.dp, bottom = 25.dp, start = 10.dp, end = 10.dp)
//        .fillMaxWidth(0.85f),
    ) {
      for (it in listOf<Color>(Blue700, Blue900, Blue200, Teal200, Green500, Orange200, Color.Red)) {
        IconButton(
          onClick = { colour = it },
//          modifier = Modifier.width(40.dp)
        ) {
          Box(modifier = Modifier.size(30.dp).clip(CircleShape).background(it))
        }
      }

    }
  }
}