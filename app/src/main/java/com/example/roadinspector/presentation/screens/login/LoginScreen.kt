package com.example.roadinspector.presentation.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.universitywork.R
import com.example.roadinspector.presentation.Screen
import com.example.roadinspector.presentation.screens.login.components.InputPasswordField
import com.example.roadinspector.presentation.screens.login.components.InputUserField
import com.example.roadinspector.presentation.screens.login.components.LoginTextField
import com.example.roadinspector.presentation.ui.theme.Pink40
import com.example.roadinspector.presentation.ui.theme.Purple40

@Composable
fun LoginScreen(
    navController: NavHostController,
    login: (String, String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(28.dp)
    ) {

        var password by remember {
            mutableStateOf("")
        }

        var user by remember {
            mutableStateOf("")
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.heightIn(60.dp))
            LoginTextField(value = stringResource(id = R.string.login))
            InputUserField(
                value = user,
                onValueChange = {user = it},
                labelValue = stringResource(id = R.string.user),
                painterResource = R.drawable.user)
            Spacer(modifier = Modifier.heightIn(10.dp))
            InputPasswordField(
                value = password,
                onValueChange = {password = it},
                labelValue = stringResource(id = R.string.password),
                painterResource = painterResource(id = R.drawable.password)
            )
            Spacer(modifier = Modifier.heightIn((30.dp)))
            Button(
                onClick = { navController.navigate(Screen.Map.rout)},
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
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
                        text = stringResource(id = R.string.button),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }


        }
    }
}


