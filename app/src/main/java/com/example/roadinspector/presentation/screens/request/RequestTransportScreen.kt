package com.example.roadinspector.presentation.screens.request

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.roadinspector.presentation.Screen
import com.example.roadinspector.presentation.ui.theme.Pink40
import com.example.roadinspector.presentation.ui.theme.Purple40
import com.example.universitywork.R

@Composable
fun RequestTransportScreen(
    coordinates: String,
    navController: NavController,
    screenState: RequestTransportState,
    requestTransport: (String, String, String) -> Unit,
    exit: () -> Unit
) {

    var showError by remember { mutableStateOf(false) }
    var email by remember {
        mutableStateOf("")
    }
    var comment by remember {
        mutableStateOf("")
    }
    val isEmailNotEmpty = email.isNotEmpty()
    val isCommentNotEmpty = comment.isNotEmpty()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text("Email")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            keyboardOptions = KeyboardOptions.Default,
            onValueChange = { email = it },
            singleLine = true
        )
        Text("Comment")
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = comment,
            label = {},
            keyboardOptions = KeyboardOptions.Default,
            onValueChange = { comment = it },
        )
        if (screenState.message == "Success") {
            navController.navigate(Screen.Map.rout)
            exit()
        }
        Button(
            onClick = {
                if (isEmailNotEmpty && isCommentNotEmpty) {
                    requestTransport(
                        email,
                        comment,
                        coordinates
                    )
                } else {
                    showError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .padding(16.dp),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(Color.Magenta)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp)
                    .background(
                        brush = Brush.horizontalGradient(listOf(Purple40, Pink40)),
                        shape = RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.button_request_transport),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        if (showError) {
            Text(
                text = "Введите почту и комментарий",
                textAlign = TextAlign.Center
            )
        }
    }
}