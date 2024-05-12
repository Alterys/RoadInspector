package com.example.roadinspector.presentation.screens.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.universitywork.R


@Composable
fun InputUserField(value: String, onValueChange: (String) -> Unit, labelValue: String, painterResource: Int) {


    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        label = {Text(text = labelValue)},
        keyboardOptions = KeyboardOptions.Default,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.user), contentDescription = "")
        }
    )
}