package com.example.roadinspector.presentation.screens.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.universitywork.R


@Composable
fun InputPasswordField(value: String, onValueChange: (String) -> Unit, labelValue: String, painterResource: Painter) {



    var passwordVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = labelValue) },
        value = value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(painter = painterResource(id = R.drawable.password), contentDescription = "")
        },
        trailingIcon = {

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                if (passwordVisible) {
                    Icon(painter = painterResource(id = R.drawable.open), contentDescription = "")
                } else {
                    Icon(painter = painterResource(id = R.drawable.closed), contentDescription = "")
                }

            }
        },
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}