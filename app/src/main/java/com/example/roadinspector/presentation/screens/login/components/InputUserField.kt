package com.example.roadinspector.presentation.screens.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.universitywork.R


@Composable
fun InputUserField(readOnly: Boolean, value: String, onValueChange: (String) -> Unit, labelValue: String, painterResource: Int) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp),
        value = value,
        readOnly = readOnly,
        label = {Text(labelValue)},
        keyboardOptions = KeyboardOptions.Default,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.user), contentDescription = "")
        },
        singleLine = true
    )
}